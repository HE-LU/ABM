package com.apiary.abm.ui;

import com.apiary.abm.utility.ConfigPreferences;
import com.apiary.abm.utility.Utils;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;

import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;


public class ABMToolWindow extends JFrame implements ToolWindowFactory
{
	private static Project sProject;


	public ABMToolWindow()
	{
		Utils.initAnalytics();
	}


	@Override
	public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow)
	{
		sProject = project;

		if(ConfigPreferences.configExist()) new ABMToolWindowMain(toolWindow);
		else new ABMToolWindowWelcome(toolWindow);
	}


	public static Project getProject()
	{
		return sProject;
	}
}