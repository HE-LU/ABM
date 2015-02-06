package com.apiary.abm.utility;


import com.apiary.abm.entity.BodyObjectEntity;
import com.apiary.abm.entity.BodyVariableEntity;
import com.apiary.abm.entity.ProblemEntity;
import com.apiary.abm.entity.TreeNodeEntity;
import com.apiary.abm.entity.blueprint.HeadersEntity;
import com.apiary.abm.entity.blueprint.ParametersEntity;
import com.apiary.abm.ui.ABMToolWindow;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameterList;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProjectManager
{
	public static List<Module> getAllModules()
	{
		Project myProject = ABMToolWindow.getProject();
		ModuleManager moduleManager = ModuleManager.getInstance(myProject);
		return Arrays.asList(moduleManager.getModules());
	}


	public static Module getModuleByName(String name)
	{
		Project myProject = ABMToolWindow.getProject();
		ModuleManager moduleManager = ModuleManager.getInstance(myProject);
		for(Module module : Arrays.asList(moduleManager.getModules()))
		{
			if(module.getName().equals(name)) return module;
		}
		return null;
	}


	public static List<PsiClass> getClasses(String module, String name)
	{
		return getClasses(getModuleByName(module), name);
	}


	public static List<PsiClass> getClasses(Module module, String name)
	{
		if(module==null) return null;
		return Arrays.asList(PsiShortNamesCache.getInstance(ABMToolWindow.getProject()).getClassesByName(name, GlobalSearchScope.moduleScope(module)));
	}


	public static PsiClass getInterfaceClass()
	{
		ConfigPreferences configPreferences = new ConfigPreferences();
		return getClasses(configPreferences.getConfigurationEntity().getModule(), configPreferences.getConfigurationEntity().getInterfaceClass()).get(0);
	}


	public static PsiClass getManagerClass()
	{
		ConfigPreferences configPreferences = new ConfigPreferences();
		return getClasses(configPreferences.getConfigurationEntity().getModule(), configPreferences.getConfigurationEntity().getManagerClass()).get(0);
	}


	public static List<ProblemEntity> checkMethodForProblems(TreeNodeEntity entity)
	{
		PsiClass interfaceClass = ProjectManager.getInterfaceClass();
		List<ProblemEntity> problems = new ArrayList<ProblemEntity>();
		boolean callbackFound = false;
		boolean uriFound = false;

		// 1) Check if method exists
		PsiMethod method;
		try
		{
			method = PsiShortNamesCache.getInstance(ABMToolWindow.getProject()).getMethodsByName(entity.getMethodName(), GlobalSearchScope.fileScope(interfaceClass.getContainingFile()))[0];
		}
		catch(Exception e)
		{
			problems.add(new ProblemEntity("Method missing", "Method could not be found!"));
			return problems;
		}

		// 2) Check if method Headers exists
		PsiAnnotation[] annotations = method.getModifierList().getAnnotations();
		String headersString = null;
		for(PsiAnnotation annotation : annotations)
		{
			if(annotation.getQualifiedName().equals("Headers"))
				headersString = annotation.getParameterList().getAttributes()[0].getValue().getText();
		}

		if(headersString!=null && entity.getRequestHeaders()!=null)
		{
			String checkString = "\"";
			for(HeadersEntity headersEntity : entity.getRequestHeaders())
			{
				checkString += headersEntity.getName() + ": " + headersEntity.getValue() + "\",";
			}
			checkString = checkString.substring(0, checkString.length() - 1);

			if(!checkString.equals(headersString))
				problems.add(new ProblemEntity("Method headers", "Headers in method and on API do not match."));
		}
		else if(headersString==null && entity.getRequestHeaders()!=null)
			problems.add(new ProblemEntity("Method headers", "There are headers on API that are not implemented in code."));
		else if(headersString!=null && entity.getRequestHeaders()==null)
			problems.add(new ProblemEntity("Method headers", "There are headers implemented in code that are not supported by API."));


		// 3) Check method URI
		for(PsiAnnotation annotation : annotations)
		{
			if(annotation.getQualifiedName().toUpperCase().equals(entity.getMethod().toUpperCase()))
			{
				uriFound = true;
				if(!annotation.getParameterList().getAttributes()[0].getValue().getText().equals("\"" + entity.getUri() + "\""))
					problems.add(new ProblemEntity("Method URI", "Method and API URI do not match. 1:" + annotation.getParameterList().getAttributes()[0].getValue().getText() + "\t2:" + entity.getUri()));
			}
		}


		// 4) Check method return type
		String methodReturnType = "";
		if(entity.isAsync()) methodReturnType += "void";
		else if(entity.getResponseBody()!=null) methodReturnType += entity.getResponseBody().get(0).getEntityName();
		else methodReturnType += "Response";
		if(!method.getReturnType().getPresentableText().equals(methodReturnType))
			problems.add(new ProblemEntity("Method return type", "Method and API return type do not match. 1:" + method.getReturnType().getPresentableText() + "\t2:" + methodReturnType));


		// 5) Check method parameters
		List<BodyObjectEntity> requestsList = entity.getRequestBody();
		List<ParametersEntity> paramList = entity.getParameters();

		PsiParameterList parameterList = method.getParameterList();
		for(int i = 0; i<parameterList.getParametersCount(); i++)
		{
			Log.d("Parameter " + i + " is: " + parameterList.getParameters()[i].getName() + " and parameter type: " + parameterList.getParameters()[i].getType().getPresentableText());

			// Callback for async task
			if(entity.isAsync() && parameterList.getParameters()[i].getName().equals(entity.getMethodName() + "Callback"))
			{
				callbackFound = true;
				if(entity.getResponseBody()!=null)
				{
					if(parameterList.getParameters()[i].getType().getPresentableText().equals("Callback<" + entity.getResponseBody().get(0).getEntityName() + ">"))
						continue;
					else problems.add(new ProblemEntity("Callback return type", "Callback return type mismatch."));
				}
				else
				{
					if(parameterList.getParameters()[i].getType().getPresentableText().equals("Callback<Response>")) continue;
					else problems.add(new ProblemEntity("Callback return type", "Callback return type mismatch."));
				}
			}

			// All request parameters
			for(BodyObjectEntity bodyEntity : requestsList)
			{
				if(parameterList.getParameters()[i].getName().equals("param" + Utils.firstLetterUpperCase(bodyEntity.getEntityName())))
				{
					if(!parameterList.getParameters()[i].getType().getPresentableText().equals(bodyEntity.getEntityName()))
						problems.add(new ProblemEntity("Body request entity", "Request have bad type."));
					requestsList.remove(bodyEntity);
					break;
				}
			}

			// All parameters
			for(ParametersEntity paramEntity : paramList)
			{
				if(parameterList.getParameters()[i].getName().equals("param" + Utils.firstLetterUpperCase(paramEntity.getName())))
				{
					if(entity.getUri().contains(paramEntity.getName()))
					{
						if(!parameterList.getParameters()[i].getType().getPresentableText().equals(Utils.firstLetterUpperCase(paramEntity.getType())))
							problems.add(new ProblemEntity("Parameters", "Parameter have bad type."));

						if(!parameterList.getParameters()[i].getModifierList().getAnnotations()[0].getQualifiedName().equals(paramEntity.getTypeOfParam()))
							problems.add(new ProblemEntity("Parameters", "Parameter have bad annotation"));
					}
					else problems.add(new ProblemEntity("Parameters", "URI does not contain this parameter."));

					paramList.remove(paramEntity);
					break;
				}
			}
		}

		for(BodyObjectEntity bodyEntity : requestsList)
			problems.add(new ProblemEntity("Body request entity", "This body item is not implemented. 1:" + bodyEntity.getEntityName()));

		for(ParametersEntity paramEntity : paramList)
			problems.add(new ProblemEntity("Parameters", "This parameter is not implemented. 1:" + paramEntity.getName()));

		if(entity.isAsync() && !callbackFound) problems.add(new ProblemEntity("Callback missing", "Method missing callback."));

		if(!uriFound) problems.add(new ProblemEntity("URI missing", "Method missing URI."));
		return problems;
	}


	public static List<ProblemEntity> checkEntityForProblems(BodyObjectEntity entity)
	{
		ConfigPreferences configPreferences = new ConfigPreferences();
		List<ProblemEntity> problems = new ArrayList<ProblemEntity>();

		// 1) Check if method exists
		PsiClass entityClass;
		try
		{
			entityClass = PsiShortNamesCache.getInstance(ABMToolWindow.getProject()).getClassesByName(entity.getEntityName(), GlobalSearchScope.moduleScope(getModuleByName(configPreferences.getConfigurationEntity().getModule())))[0];
		}
		catch(Exception e)
		{
			e.printStackTrace();
			problems.add(new ProblemEntity("Entity missing", "Entity could not be found: " + entity.getEntityName()));
			return problems;
		}

		List<BodyVariableEntity> variablesList = entity.getVariables();
		// check variables
		for(PsiField field : entityClass.getFields())
		{
			for(BodyVariableEntity variable : variablesList)
			{
				if(field.getNameIdentifier().getText().equals("m" + Utils.firstLetterUpperCase(Utils.cleanUpString(variable.getName()))))
				{
					// check type
					// private String mDescription;
					if(!field.getType().getPresentableText().equals(variable.getTypeName()))
						problems.add(new ProblemEntity("Entity variable bad type", "Entity: " + variable.getType().toString() + ":" + variable.getTypeName() + " variable have bad type: " + field.getType().getPresentableText()));

					// check annotation
					// @SerializedName("description")
					PsiAnnotation[] annotations = field.getModifierList().getAnnotations();
					String headersString = "";
					for(PsiAnnotation annotation : annotations)
					{
						if(annotation.getQualifiedName().equals("SerializedName"))
							headersString = annotation.getParameterList().getAttributes()[0].getValue().getText();
					}
					if(!headersString.equals("\"" + variable.getName() + "\""))
						problems.add(new ProblemEntity("Entity variable bad type", "Entity variable have bad serializable name: " + headersString));

					variablesList.remove(variable);
					break;
				}
			}
		}

		for(BodyVariableEntity variable : variablesList)
			problems.add(new ProblemEntity("Entity variable", "This variable is not implemented. 1:" + "m" + Utils.firstLetterUpperCase(Utils.cleanUpString(variable.getName()))));

		return problems;
	}
}
