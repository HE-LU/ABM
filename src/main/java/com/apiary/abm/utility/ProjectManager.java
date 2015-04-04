package com.apiary.abm.utility;


import com.apiary.abm.entity.BodyObjectEntity;
import com.apiary.abm.entity.BodyVariableEntity;
import com.apiary.abm.entity.ProblemEntity;
import com.apiary.abm.entity.TreeNodeEntity;
import com.apiary.abm.entity.blueprint.HeadersEntity;
import com.apiary.abm.entity.blueprint.ParametersEntity;
import com.apiary.abm.ui.ABMToolWindow;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.ide.util.PackageUtil;
import com.intellij.lang.ImportOptimizer;
import com.intellij.lang.LanguageImportStatements;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.PsiParameterList;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


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


	public static PsiPackage getPackages(String module, String name)
	{
		return getPackages(getModuleByName(module), name);
	}


	public static PsiDirectory getDirectory(String module, String name)
	{
		return getDirectory(getModuleByName(module), name);
	}


	public static List<PsiClass> getClasses(String module, String name)
	{
		return getClasses(getModuleByName(module), name);
	}


	public static PsiPackage getPackages(Module module, String name)
	{
		if(module == null) return null;

		PsiDirectory psiDir = PackageUtil.findPossiblePackageDirectoryInModule(module, name);
		if(JavaDirectoryService.getInstance().getPackage(psiDir).getQualifiedName() != null && JavaDirectoryService.getInstance().getPackage(psiDir).getQualifiedName().equals(name))
			return JavaDirectoryService.getInstance().getPackage(psiDir);
		else return null;
	}


	public static PsiDirectory getDirectory(Module module, String name)
	{
		if(module == null) return null;

		PsiDirectory psiDir = PackageUtil.findPossiblePackageDirectoryInModule(module, name);
		if(JavaDirectoryService.getInstance().getPackage(psiDir).getQualifiedName() != null && JavaDirectoryService.getInstance().getPackage(psiDir).getQualifiedName().equals(name))
			return psiDir;
		else return null;
	}


	public static void createFileWithContent(final PsiDirectory dir, String name, String content)
	{
		final PsiFile element = PsiFileFactory.getInstance(ABMToolWindow.getProject()).createFileFromText(name, JavaFileType.INSTANCE, content);
		ApplicationManager.getApplication().runWriteAction(new Runnable()
		{
			public void run()
			{
				// optimize imports
				ImportOptimizer importOptimizer = (ImportOptimizer) LanguageImportStatements.INSTANCE.forFile(element).toArray()[0];
				if(importOptimizer != null) importOptimizer.processFile(element).run();

				// reformat file
				CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(ABMToolWindow.getProject());
				if(codeStyleManager != null) codeStyleManager.reformat(element);

				try
				{
					dir.add(element);
				}
				catch(Exception e)
				{
					ResourceBundle messages = ResourceBundle.getBundle("values/strings");
					String path = dir.toString().substring(dir.toString().indexOf(":") + 1, dir.toString().length());
					JOptionPane pane = new JOptionPane("Ouch! File " + element.getName() + " already exist in directory" + path + "! File won't be created!");
					Object[] options = new String[]{messages.getString("global_ok")};
					pane.setOptions(options);
					JDialog dialog = pane.createDialog(new JFrame(), messages.getString("implementation_dialog_file_exist"));
					dialog.setVisible(true);
				}
			}
		});
	}


	public static List<PsiClass> getClasses(Module module, String name)
	{
		if(module == null) return null;
		return Arrays.asList(PsiShortNamesCache.getInstance(ABMToolWindow.getProject()).getClassesByName(name, GlobalSearchScope.moduleScope(module)));
	}


	public static PsiClass getInterfaceClass()
	{
		ConfigPreferences configPreferences = new ConfigPreferences();
		try
		{
			return getClasses(configPreferences.getConfigurationEntity().getModule(), configPreferences.getConfigurationEntity().getInterfaceClass()).get(0);
		}
		catch(Exception e)
		{
			return null;
		}
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

		if(headersString != null && entity.getRequestHeaders() != null)
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
		else if(headersString == null && entity.getRequestHeaders() != null)
			problems.add(new ProblemEntity("Method headers", "There are headers on API that are not implemented in code."));
		else if(headersString != null && entity.getRequestHeaders() == null)
			problems.add(new ProblemEntity("Method headers", "There are headers implemented in code that are not supported by API."));


		// 3) Check method URI
		for(PsiAnnotation annotation : annotations)
		{
			String annotationString = annotation.getQualifiedName();
			if(annotationString.lastIndexOf(".") != -1)
				annotationString = annotationString.substring(annotationString.lastIndexOf(".") + 1, annotationString.length());

			if(annotationString.toUpperCase().equals(entity.getMethod().toUpperCase()))
			{
				uriFound = true;
				if(!annotation.getParameterList().getAttributes()[0].getValue().getText().equals("\"" + entity.getUri() + "\""))
					problems.add(new ProblemEntity("Method URI", "Method and API URI do not match."));
			}
		}


		// 4) Check method return type
		String methodReturnType = "";
		if(entity.isAsync()) methodReturnType += "void";
		else if(entity.getResponseBody() != null) methodReturnType += entity.getResponseBody().get(0).getEntityName();
		else methodReturnType += "Response";
		if(!method.getReturnType().getPresentableText().equals(methodReturnType))
			problems.add(new ProblemEntity("Method return type", "Method and API return type do not match."));


		// 5) Check method parameters
		List<BodyObjectEntity> requestsList = entity.getRequestBody();
		List<ParametersEntity> paramList = entity.getParameters();

		PsiParameterList parameterList = method.getParameterList();
		for(int i = 0; i < parameterList.getParametersCount(); i++)
		{
			// Callback for async task
			if(entity.isAsync() && parameterList.getParameters()[i].getName().equals(entity.getMethodName() + "Callback"))
			{
				callbackFound = true;
				if(entity.getResponseBody() != null)
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
			if(requestsList != null) for(BodyObjectEntity bodyEntity : requestsList)
			{
				if(parameterList.getParameters()[i].getName().equals("param" + Utils.firstLetterUpperCase(bodyEntity.getEntityName())))
				{
					if(!parameterList.getParameters()[i].getType().getPresentableText().equals(bodyEntity.getEntityName()))
						problems.add(new ProblemEntity("Method body request", "Method body request: " + bodyEntity.getSerializableName() + " has bad type."));
					requestsList.remove(bodyEntity);
					break;
				}
			}

			// All parameters
			if(paramList != null) for(ParametersEntity paramEntity : paramList)
			{
				if(parameterList.getParameters()[i].getName().equals("param" + Utils.firstLetterUpperCase(paramEntity.getName())))
				{
					if(entity.getUri().contains(paramEntity.getName()))
					{
						if(!parameterList.getParameters()[i].getType().getPresentableText().equals(Utils.firstLetterUpperCase(paramEntity.getType())))
							problems.add(new ProblemEntity("Method parameters", "Method parameter: " + paramEntity.getName() + " has bad type."));

						if(!parameterList.getParameters()[i].getModifierList().getAnnotations()[0].getQualifiedName().equals(paramEntity.getTypeOfParam()))
							problems.add(new ProblemEntity("Method parameters", "Method parameter: " + paramEntity.getName() + " has bad annotation"));
					}
					else
						problems.add(new ProblemEntity("Method parameters", "URI does not contain this parameter: " + paramEntity.getName() + "."));

					paramList.remove(paramEntity);
					break;
				}
			}
		}

		if(requestsList != null) for(BodyObjectEntity bodyEntity : requestsList)
			problems.add(new ProblemEntity("Method body request", "This body item is not implemented: " + bodyEntity.getEntityName() + "."));

		if(paramList != null) for(ParametersEntity paramEntity : paramList)
			problems.add(new ProblemEntity("Method parameters", "This parameter is not implemented:" + paramEntity.getName() + "."));

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
			problems.add(new ProblemEntity("Entity missing", "Entity: " + entity.getEntityName() + " could not be found."));
			return problems;
		}

		List<BodyVariableEntity> variablesList = new ArrayList<BodyVariableEntity>(entity.getVariables());
		// check variables
		for(PsiField field : entityClass.getFields())
		{
			for(BodyVariableEntity variable : variablesList)
			{
				if(field.getNameIdentifier().getText().equals("m" + Utils.firstLetterUpperCase(Utils.cleanUpString(variable.getName()))))
				{
					// check type
					if(!field.getType().getPresentableText().equals(variable.getTypeName()))
						problems.add(new ProblemEntity("Entity variable bad type", "Variable: " + variable.getName() + " has bad type."));

					// check annotation
					PsiAnnotation[] annotations = field.getModifierList().getAnnotations();
					String headersString = "";
					for(PsiAnnotation annotation : annotations)
					{
						String annotationString = annotation.getQualifiedName();
						if(annotationString.lastIndexOf(".") != -1)
							annotationString = annotationString.substring(annotationString.lastIndexOf(".") + 1, annotationString.length());

						if(annotationString.equals("SerializedName"))
							headersString = annotation.getParameterList().getAttributes()[0].getValue().getText();
					}
					if(!headersString.equals("\"" + variable.getName() + "\""))
						problems.add(new ProblemEntity("Entity variable bad type", "Variable: " + variable.getName() + " has bad serialized name."));

					variablesList.remove(variable);
					break;
				}
			}
		}

		for(BodyVariableEntity variable : variablesList)
			problems.add(new ProblemEntity("Entity variable", "Variable: m" + Utils.firstLetterUpperCase(Utils.cleanUpString(variable.getName())) + " is not implemented."));

		return problems;
	}


	public static boolean checkMethodUsage(TreeNodeEntity entity, List<TreeNodeEntity> list)
	{
		for(TreeNodeEntity item : list)
		{
			if(entity.getMethod().equals(item.getMethod()) && entity.getUri().equals(item.getUri())) return true;
		}
		return false;
	}
}
