package com.apiary.abm.ui;

import com.apiary.abm.utility.JBackgroundPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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

		topPanel.setMinimumSize(new Dimension(0, 125));
		bottomPanel.setMinimumSize(new Dimension(0, 125));

		topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 125));
		bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 125));

		// add elements
		topPanel.setLayout(new MigLayout("insets 0 55 20 55, flowy, fillx, filly", "[fill, grow]", "[fill]"));
		middlePanel.setLayout(new MigLayout("insets 0, flowy, fillx, filly", "[fill, grow]", "[fill][fill]"));
		bottomPanel.setLayout(new MigLayout("insets 25 0 0 0, flowy, fillx, filly", "[grow, center]", "[center, top]"));

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

		// informations
		JLabel infoText = new JLabel("<html><center>" + messages.getString("welcome_information") + "</center></html>");
		infoText.setForeground(Color.WHITE);
		infoText.setFont(new Font("Ariel", Font.BOLD, 18));
		infoText.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(infoText);

		// connect button
		try
		{
			final JLabel buttonConnect = new JLabel();
			BufferedImage tmpImage = ImageIO.read(JBackgroundPanel.class.getClassLoader().getResourceAsStream("drawable/img_button_welcome.png"));

			Image image = tmpImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			buttonConnect.setIcon(new ImageIcon(image));
			buttonConnect.setOpaque(false);

			buttonConnect.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					new ABMToolWindowConnect(mToolWindow);
				}


				public void mousePressed(MouseEvent e)
				{
					try
					{
						InputStream tmp = JBackgroundPanel.class.getClassLoader().getResourceAsStream("drawable/img_button_welcome_pressed.png");
						BufferedImage tmpImage = ImageIO.read(tmp);
						Image buttonConnectImage = tmpImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
						buttonConnect.setIcon(new ImageIcon(buttonConnectImage));
					}
					catch(IOException e1)
					{
						e1.printStackTrace();
					}
				}


				public void mouseReleased(MouseEvent e)
				{
					try
					{
						InputStream tmp = JBackgroundPanel.class.getClassLoader().getResourceAsStream("drawable/img_button_welcome.png");
						BufferedImage tmpImage = ImageIO.read(tmp);
						Image buttonConnectImage = tmpImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
						buttonConnect.setIcon(new ImageIcon(buttonConnectImage));
					}
					catch(IOException e1)
					{
						e1.printStackTrace();
					}
				}
			});

			bottomPanel.add(buttonConnect);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}


	}
}
