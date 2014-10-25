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
import javax.swing.JTextField;
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

		topPanel.setMinimumSize(new Dimension(0, 125));
		bottomPanel.setMinimumSize(new Dimension(0, 125));

		topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 125));
		bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 125));

		// add elements
		topPanel.setLayout(new MigLayout("insets 0 55 20 55, flowy, fillx, filly", "[fill, grow]", "[fill]"));
		middlePanel.setLayout(new MigLayout("insets 0, flowy, fillx, filly", "[grow, center]", "[]"));
		bottomPanel.setLayout(new MigLayout("insets 25 0 0 0, flowy, fillx, filly", "[grow, center]", "[center, top]"));

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

		JLabel text = new JLabel("<html><center>" + messages.getString("connect_message") + "</center></html>");
		text.setForeground(Color.WHITE);
		text.setFont(new Font("Ariel", Font.BOLD, 18));
		text.setHorizontalAlignment(SwingConstants.CENTER);
		middleContentPanel.add(text);

		final JTextField textField = new JTextField();
		textField.setText("http://127.0.0.1:8080/share/input.blueprint");
		middleContentPanel.add(textField);

		middlePanel.add(middleContentPanel);


		// connect button
		try
		{
			final JLabel buttonConnect = new JLabel();
			BufferedImage tmpImage = ImageIO.read(JBackgroundPanel.class.getClassLoader().getResourceAsStream("drawable/img_button_connect.png"));

			Image image = tmpImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			buttonConnect.setIcon(new ImageIcon(image));
			buttonConnect.setOpaque(false);

			buttonConnect.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					try
					{
						String inputFilePath = Utils.saveWebFileToTmp(textField.getText());

						Preferences preferences = new Preferences();
						preferences.setApiaryBlueprintRaw(Utils.readFileAsString(inputFilePath, StandardCharsets.UTF_8));

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
						InputStream tmp = JBackgroundPanel.class.getClassLoader().getResourceAsStream("drawable/img_button_connect_pressed.png");
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
						InputStream tmp = JBackgroundPanel.class.getClassLoader().getResourceAsStream("drawable/img_button_connect.png");
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
