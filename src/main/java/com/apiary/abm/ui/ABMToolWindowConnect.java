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
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class ABMToolWindowConnect extends JFrame
{
	private ToolWindow mToolWindow;


	public ABMToolWindowConnect(ToolWindow toolWindow)
	{
		mToolWindow = toolWindow;
		mToolWindow.getContentManager().removeAllContents(true);
		ResourceBundle messages = ResourceBundle.getBundle("values/strings");

		// create UI
		final JBackgroundPanel myToolWindowContent = new JBackgroundPanel(true);
		ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
		Content content = contentFactory.createContent(myToolWindowContent, "", false);
		mToolWindow.getContentManager().addContent(content);

		// MIGLAYOUT ( params, columns, rows)
		// insets TOP LEFT BOTTOM RIGHT
		myToolWindowContent.setLayout(new MigLayout("insets 0, flowy, fillx, filly", "[fill, grow, center]", "[fill,top][fill][fill,bottom]"));

		JBackgroundPanel topPanel = new JBackgroundPanel("box_top.png", false);
		JPanel middlePanel = new JPanel();
		JBackgroundPanel bottomPanel = new JBackgroundPanel("box_bottom.png", false);

		topPanel.setMinimumSize(new Dimension(0, 125));
		bottomPanel.setMinimumSize(new Dimension(0, 125));

		topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 125));
		bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 125));

		topPanel.setOpaque(false);
		middlePanel.setOpaque(false);
		bottomPanel.setOpaque(false);

		myToolWindowContent.add(topPanel);
		myToolWindowContent.add(middlePanel);
		myToolWindowContent.add(bottomPanel);


		// add elements
		topPanel.setLayout(new MigLayout("insets 10 0 0 0, flowy, fillx, filly", "[grow, center]", "[center, top]"));
		middlePanel.setLayout(new MigLayout("insets 0, flowy, fillx, filly", "[fill, grow]", "[fill][fill]"));
		bottomPanel.setLayout(new MigLayout("insets 25 50 0 50, flowy, fillx, filly", "[fill, grow]", "[fill]"));


		// name
		final JLabel nameText = new JLabel("<html><center>" + messages.getString("wellcome_name") + "</center></html>");
		nameText.setForeground(Color.WHITE);
		nameText.setFont(new Font("Serif", Font.BOLD, 16));
		nameText.setHorizontalAlignment(SwingConstants.CENTER);
		middlePanel.add(nameText);


		// connect button
		try
		{
			final JLabel buttonConnect = new JLabel();

			InputStream tmp = JBackgroundPanel.class.getClassLoader().getResourceAsStream("drawable/button_connect.png");
			BufferedImage tmpImage = ImageIO.read(tmp);
			Image buttonConnectImage = tmpImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			buttonConnect.setIcon(new ImageIcon(buttonConnectImage));
			buttonConnect.setOpaque(false);

			buttonConnect.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					try
					{
						String inputFilePath = Utils.getWebFile("http://127.0.0.1:8080/share/input.blueprint");

						Preferences preferences = new Preferences();
						preferences.setApiaryBlueprintRaw(Utils.getStringFromFile(inputFilePath, StandardCharsets.UTF_8));

						new ABMToolWindowMain(mToolWindow);
					}
					catch(IOException e1)
					{
						e1.printStackTrace();
					}
				}


				public void mousePressed(MouseEvent e)
				{
					try
					{
						InputStream tmp = JBackgroundPanel.class.getClassLoader().getResourceAsStream("drawable/button_connect_pressed.png");
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
						InputStream tmp = JBackgroundPanel.class.getClassLoader().getResourceAsStream("drawable/button_connect.png");
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

			topPanel.add(buttonConnect);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}


}
