package com.apiary.abm.ui;

import com.apiary.abm.utility.JBackgroundPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class ABMToolWindowWelcome extends JFrame
{
	private ToolWindow mToolWindow;


	public ABMToolWindowWelcome(ToolWindow toolWindow)
	{
		mToolWindow = toolWindow;
		mToolWindow.getContentManager().removeAllContents(true);
		ResourceBundle messages = ResourceBundle.getBundle("values/strings");

		// create UI
		JBackgroundPanel myToolWindowContent = new JBackgroundPanel("img_background.png", JBackgroundPanel.JBackgroundPanelType.BACKGROUND_REPEAT);
		ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
		Content content = contentFactory.createContent(myToolWindowContent, "", false);
		mToolWindow.getContentManager().addContent(content);

		// MIGLAYOUT ( params, columns, rows)
		// insets TOP LEFT BOTTOM RIGHT
		myToolWindowContent.setLayout(new MigLayout("insets 0, flowy, fillx, filly", "[fill, grow, center]", "[fill,top][fill][fill,bottom]"));

		JBackgroundPanel topPanel = new JBackgroundPanel("img_box_top.png", JBackgroundPanel.JBackgroundPanelType.PANEL);
		JPanel middlePanel = new JPanel();
		JBackgroundPanel bottomPanel = new JBackgroundPanel("img_box_bottom.png", JBackgroundPanel.JBackgroundPanelType.PANEL);

		topPanel.setMinimumSize(new Dimension(0, 130));
		bottomPanel.setMinimumSize(new Dimension(0, 130));

		topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
		bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

		// add elements
		topPanel.setLayout(new MigLayout("insets 0 55 20 55, flowy, fillx, filly", "[fill, grow]", "[fill]"));
		middlePanel.setLayout(new MigLayout("insets 0, flowy, fillx, filly", "[fill, grow]", "[fill][fill]"));
		bottomPanel.setLayout(new MigLayout("insets 30 0 0 0, flowy, fillx, filly", "[grow, center]", "[center, top]"));

		topPanel.setOpaque(false);
		middlePanel.setOpaque(false);
		bottomPanel.setOpaque(false);

		myToolWindowContent.add(topPanel);
		myToolWindowContent.add(middlePanel);
		myToolWindowContent.add(bottomPanel);

		// name
		JLabel nameText = new JLabel("<html><center>" + messages.getString("welcome_name") + "</center></html>");
		nameText.setForeground(Color.WHITE);
		nameText.setFont(new Font("Ariel", Font.BOLD, 32));
		nameText.setHorizontalAlignment(SwingConstants.CENTER);
		middlePanel.add(nameText);

		// version
		JLabel versionText = new JLabel("<html><center>" + messages.getString("welcome_version") + "</center></html>");
		versionText.setForeground(Color.WHITE);
		versionText.setFont(new Font("Ariel", Font.BOLD, 24));
		versionText.setHorizontalAlignment(SwingConstants.CENTER);
		middlePanel.add(versionText);

		// information
		JLabel infoText = new JLabel("<html><center>" + messages.getString("welcome_information") + "</center></html>");
		infoText.setForeground(Color.WHITE);
		infoText.setFont(new Font("Ariel", Font.BOLD, 18));
		infoText.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(infoText);

		// connect button
		final JLabel button = new JLabel();
		button.setOpaque(false);
		button.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_start.png") + "' width='90' height='90' /></html>");

		button.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				new ABMToolWindowConnect(mToolWindow);
			}


			public void mousePressed(MouseEvent e)
			{
				button.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_start_pressed.png") + "' width='90' height='90' /></html>");
			}


			public void mouseReleased(MouseEvent e)
			{
				button.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_start.png") + "' width='90' height='90' /></html>");
			}
		});

		bottomPanel.add(button);
	}
}
