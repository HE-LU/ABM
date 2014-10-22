package com.apiary.abm.ui;

import com.apiary.abm.utility.JBackgroundPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import java.util.ResourceBundle;

import javax.swing.JFrame;


public class ABMToolWindowMain extends JFrame
{
	private ToolWindow mToolWindow;


	public ABMToolWindowMain(ToolWindow toolWindow)
	{
		mToolWindow = toolWindow;
		mToolWindow.getContentManager().removeAllContents(true);
		ResourceBundle messages = ResourceBundle.getBundle("values/strings");

		// create UI
		JBackgroundPanel myToolWindowContent = new JBackgroundPanel(true);
		ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
		Content content = contentFactory.createContent(myToolWindowContent, "", false);
		mToolWindow.getContentManager().addContent(content);


	}
}
