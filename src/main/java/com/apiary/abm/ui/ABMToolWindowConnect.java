package com.apiary.abm.ui;

import com.apiary.abm.utility.JBackgroundPanel;
import com.apiary.abm.utility.Preferences;
import com.apiary.abm.utility.Utils;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class ABMToolWindowConnect extends JFrame
{
	private ToolWindow mToolWindow;


	public ABMToolWindowConnect(ToolWindow toolWindow)
	{
		mToolWindow = toolWindow;
		mToolWindow.getContentManager().removeAllContents(true);
		final ResourceBundle messages = ResourceBundle.getBundle("values/strings");

		// create UI
		final JBackgroundPanel myToolWindowContent = new JBackgroundPanel("img_background.png", JBackgroundPanel.JBackgroundPanelType.BACKGROUND_REPEAT);
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
		middlePanel.setLayout(new MigLayout("insets 0 15 0 15, flowy, fillx, filly", "[grow, center]", "[][]"));
		bottomPanel.setLayout(new MigLayout("insets 30 0 0 0, flowy, fillx, filly", "[grow, center]", "[center, top]"));

		topPanel.setOpaque(false);
		middlePanel.setOpaque(false);
		bottomPanel.setOpaque(false);

		myToolWindowContent.add(topPanel);
		myToolWindowContent.add(middlePanel);
		myToolWindowContent.add(bottomPanel);

		// Connect label
		JLabel infoText = new JLabel("<html><center>" + messages.getString("connect_header") + "</center></html>");
		infoText.setForeground(Color.WHITE);
		infoText.setFont(new Font("Ariel", Font.BOLD, 40));
		infoText.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(infoText);

		// Label + EditText
		JBackgroundPanel middleContentPanel = new JBackgroundPanel("img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		middleContentPanel.setLayout(new MigLayout("insets 12 12 18 19, flowy, fillx, filly", "[fill, grow]", "[][]"));
		middleContentPanel.setOpaque(false);
		middleContentPanel.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));

		JLabel labelMessage = new JLabel("<html><center>" + messages.getString("connect_message") + "</center></html>");
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(new Font("Ariel", Font.BOLD, 18));
		labelMessage.setHorizontalAlignment(SwingConstants.CENTER);
		middleContentPanel.add(labelMessage);

		final JTextField textField = new JTextField();
		textField.setText("http://127.0.0.1:8080/share/input.blueprint");
		middleContentPanel.add(textField);

		middlePanel.add(middleContentPanel);

		final JLabel labelError = new JLabel();
		labelError.setForeground(Color.RED);
		labelError.setFont(new Font("Ariel", Font.BOLD, 18));
		labelError.setHorizontalAlignment(SwingConstants.CENTER);
		middlePanel.add(labelError);

		// connect button
		final JLabel button = new JLabel();
		button.setOpaque(false);
		button.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_connect.png") + "' width='90' height='90' /></html>");


		labelError.setText("<html><center>" + messages.getString("connect_message_error") + "</html></center>");
		labelError.setVisible(false);

		button.addMouseListener(new MouseAdapter()
		{
			private boolean reloading;


			public void mouseClicked(MouseEvent e)
			{
				if(reloading) return;

				button.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/animation_connect.gif") + "' width='90' height='90' /></html>");
				reloading = true;

				Thread t = new Thread(new Runnable()
				{
					public void run()
					{
						try
						{
							String inputFilePath = Utils.saveWebFileToTmp(textField.getText());
							String tmp = Utils.readFileAsString(inputFilePath, StandardCharsets.UTF_8);

							Preferences preferences = new Preferences();
							preferences.setApiaryBlueprintUrl(textField.getText());
							preferences.setApiaryBlueprintRaw(tmp);

							SwingUtilities.invokeLater(new Runnable()
							{
								public void run()
								{
									new ABMToolWindowMain(mToolWindow);
								}
							});
						}
						catch(IOException e)
						{
							e.printStackTrace();
							reloading = false;
							SwingUtilities.invokeLater(new Runnable()
							{
								public void run()
								{
									labelError.setVisible(true);
									button.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_connect.png") + "' width='90' height='90' /></html>");

								}
							});
						}
					}
				});
				t.start();


			}


			public void mousePressed(MouseEvent e)
			{
				if(reloading) return;
				button.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_connect_pressed.png") + "' width='90' height='90' /></html>");
			}


			public void mouseReleased(MouseEvent e)
			{
				if(reloading) return;
				button.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_connect.png") + "' width='90' height='90' /></html>");
			}
		});

		bottomPanel.add(button);
	}
}
