package com.apiary.abm.ui;

import com.apiary.abm.utility.Preferences;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;

import javax.swing.JFrame;


public class ABMToolWindow extends JFrame implements ToolWindowFactory
{
	public ABMToolWindow()
	{
	}


	@Override
	public void createToolWindowContent(Project project, ToolWindow toolWindow)
	{
		ToolWindow mToolWindow = toolWindow;

		Preferences preferences = new Preferences();
		if(preferences.getPluginInitialized())
		{
			ABMToolWindowMain main = new ABMToolWindowMain(mToolWindow);
		}
		else
		{
			ABMToolWindowWelcome welcome = new ABMToolWindowWelcome(mToolWindow);
		}
	}
}