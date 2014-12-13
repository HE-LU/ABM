package com.apiary.abm.ui;

import com.apiary.abm.utility.Utils;
import com.apiary.abm.view.ImageButton;
import com.apiary.abm.view.JBackgroundPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
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

		initLayout();
	}


	private void initLayout()
	{
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

		topPanel.setMinimumSize(new Dimension(0, Utils.reDimension(90)));
		bottomPanel.setMinimumSize(new Dimension(0, Utils.reDimension(90)));

		topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Utils.reDimension(90)));
		bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Utils.reDimension(90)));

		// add elements
		topPanel.setLayout(new MigLayout("insets 0 " + Utils.reDimension(20) + " " + Utils.reDimension(20) + " " + Utils.reDimension(20) + ", flowy, fillx, filly", "[fill, grow]", "[fill]"));
		middlePanel.setLayout(new MigLayout("insets 0, flowy, fillx, filly", "[fill, grow]", "[fill][fill]"));
		bottomPanel.setLayout(new MigLayout("insets " + Utils.reDimension(18) + " 0 0 0, flowy, fillx, filly", "[grow, center]", "[center, top]"));

		topPanel.setOpaque(false);
		middlePanel.setOpaque(false);
		bottomPanel.setOpaque(false);

		myToolWindowContent.add(topPanel);
		myToolWindowContent.add(middlePanel);
		myToolWindowContent.add(bottomPanel);

		// name
		JLabel infoText = new JLabel("<html><center>" + messages.getString("welcome_name") + "</center></html>");
		infoText.setForeground(Color.WHITE);
		infoText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_LARGE)));
		infoText.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(infoText);

		// name
		JLabel nameText = new JLabel("<html><center>" + messages.getString("welcome_information") + "</center></html>");
		nameText.setForeground(Color.WHITE);
		nameText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		nameText.setHorizontalAlignment(SwingConstants.CENTER);
		middlePanel.add(nameText);

		// version
		JLabel versionText = new JLabel("<html><center>" + messages.getString("welcome_version") + "</center></html>");
		versionText.setForeground(Color.WHITE);
		versionText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
		versionText.setHorizontalAlignment(SwingConstants.CENTER);
		middlePanel.add(versionText);

		// connect button
		final ImageButton button = new ImageButton();

		try
		{
			BufferedImage img = ImageIO.read(JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_start.png"));
			button.setImage(img);
			button.setSize(Utils.reDimension(70), Utils.reDimension(70));

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		button.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				new ABMToolWindowConnect(mToolWindow);
			}


			public void mousePressed(MouseEvent e)
			{
				try
				{
					BufferedImage img = ImageIO.read(JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_start_pressed.png"));
					button.setImage(img);
					button.setSize(Utils.reDimension(70), Utils.reDimension(70));
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
					BufferedImage img = ImageIO.read(JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_start.png"));
					button.setImage(img);
					button.setSize(Utils.reDimension(70), Utils.reDimension(70));
				}
				catch(IOException e2)
				{
					e2.printStackTrace();
				}
			}
		});

		bottomPanel.add(button);
	}
}
