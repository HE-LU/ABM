package com.apiary.abm.utility;


import com.apiary.abm.ui.ABMToolWindow;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;

import java.util.Arrays;
import java.util.List;


public class ProjectManager
{

	public ProjectManager()
	{
	}


	public List<Module> getAllModules()
	{
		Project myProject = ABMToolWindow.getProject();
		ModuleManager moduleManager = ModuleManager.getInstance(myProject);
		return Arrays.asList(moduleManager.getModules());
	}


	public Module getModuleByName(String name)
	{
		Project myProject = ABMToolWindow.getProject();
		ModuleManager moduleManager = ModuleManager.getInstance(myProject);
		for(Module module : Arrays.asList(moduleManager.getModules()))
		{
			if(module.getName().equals(name)) return module;
		}
		return null;
	}


	public PsiPackage getPackage(String name)
	{
		return JavaPsiFacade.getInstance(ABMToolWindow.getProject()).findPackage(name);
	}


	public List<PsiClass> getClasses(String module, String name)
	{
		return getClasses(getModuleByName(module), name);
	}


	public List<PsiClass> getClasses(Module module, String name)
	{
		if(module==null) return null;
		return Arrays.asList(PsiShortNamesCache.getInstance(ABMToolWindow.getProject()).getClassesByName(name, GlobalSearchScope.moduleScope(module)));
	}
}
