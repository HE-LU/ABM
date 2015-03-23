package com.apiary.abm.ui;

import com.apiary.abm.utility.Utils;
import com.apiary.abm.view.ImageButton;
import com.apiary.abm.view.JBackgroundPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class ABMToolWindowConnectGradle extends JFrame
{
	private ToolWindow mToolWindow;
	private final ResourceBundle mMessages = ResourceBundle.getBundle("values/strings");


	public ABMToolWindowConnectGradle(ToolWindow toolWindow)
	{
		mToolWindow = toolWindow;

		Utils.trackPage("Connect gradle screen");

		initLayout();
	}


	private void initLayout()
	{
		// create UI
		final JBackgroundPanel myToolWindowContent = new JBackgroundPanel("drawable/img_background.png", JBackgroundPanel.JBackgroundPanelType.BACKGROUND_REPEAT);
		final ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
		final Content content = contentFactory.createContent(myToolWindowContent, "", false);
		mToolWindow.getContentManager().removeAllContents(true);
		mToolWindow.getContentManager().addContent(content);

		// MIGLAYOUT ( params, columns, rows)
		// insets TOP LEFT BOTTOM RIGHT
		myToolWindowContent.setLayout(new MigLayout("insets 0, flowy, fillx, filly", "[fill, grow, center]", "[fill,top][fill, grow][fill,bottom]"));

		final JBackgroundPanel topPanel = new JBackgroundPanel("drawable/img_box_top.png", JBackgroundPanel.JBackgroundPanelType.PANEL);
		final JPanel middlePanel = new JPanel();
		final JBScrollPane middleScrollPanel = new JBScrollPane(middlePanel, JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JBScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		final JBackgroundPanel bottomPanel = new JBackgroundPanel("drawable/img_box_bottom.png", JBackgroundPanel.JBackgroundPanelType.PANEL);

		topPanel.setMinimumSize(new Dimension(0, Utils.reDimension(90)));
		bottomPanel.setMinimumSize(new Dimension(0, Utils.reDimension(90)));

		topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Utils.reDimension(90)));
		bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Utils.reDimension(90)));

		// add elements
		topPanel.setLayout(new MigLayout("insets 0 " + Utils.reDimension(20) + " " + Utils.reDimension(20) + " " + Utils.reDimension(20) + ", flowy, fillx, filly", "[fill, grow]", "[fill]"));
		middlePanel.setLayout(new MigLayout("insets 0 " + Utils.reDimension(15) + " 0 " + Utils.reDimension(15) + ", flowy, fillx, filly", "[grow, center]", "[][][]"));
		bottomPanel.setLayout(new MigLayout("insets " + Utils.reDimension(18) + " 0 0 0, flowy, fillx, filly", "[grow, center]", "[center, top]"));

		topPanel.setOpaque(false);
		middlePanel.setOpaque(false);
		middleScrollPanel.setOpaque(false);
		middleScrollPanel.getViewport().setOpaque(false);
		middleScrollPanel.setBorder(BorderFactory.createEmptyBorder());
		middleScrollPanel.getVerticalScrollBar().setUnitIncrement(15);
		bottomPanel.setOpaque(false);

		myToolWindowContent.add(topPanel);
		myToolWindowContent.add(middleScrollPanel);
		myToolWindowContent.add(bottomPanel);


		// Connect label
		final JLabel nameText = new JLabel("<html><center>" + mMessages.getString("connect_gradle_header") + "</center></html>");
		nameText.setForeground(Color.WHITE);
		nameText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_LARGE)));
		nameText.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(nameText);


		// Panel gradle
		final JBackgroundPanel infoPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		infoPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowy, fillx, filly", "[fill, grow][fill, grow]", "[]"));
		infoPanel.setOpaque(false);
		infoPanel.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));
		middlePanel.add(infoPanel);

		// gradle message
		final JTextPane infoText = new JTextPane();
		StyledDocument doc = infoText.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		infoText.setText(mMessages.getString("connect_gradle_message"));
		infoText.setOpaque(false);
		infoText.setForeground(Color.WHITE);
		infoText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		infoText.setEditable(false);
		infoPanel.add(infoText);


		// Panel skip
		final JBackgroundPanel skipPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		skipPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowy, fillx, filly", "[center][center]", "[]"));
		skipPanel.setOpaque(false);
		skipPanel.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));
		middlePanel.add(skipPanel);

		// skip message
		final JLabel skipText = new JLabel("<html><center>" + mMessages.getString("connect_gradle_message_second") + "</center></html>");
		skipText.setForeground(Color.WHITE);
		skipText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		skipText.setHorizontalAlignment(SwingConstants.CENTER);
		skipPanel.add(skipText);

		// skip button
		final JButton skipButton = new JButton(mMessages.getString("connect_gradle_button_skip"));
		skipButton.setMaximumSize(new Dimension(Utils.reDimension(120), Integer.MAX_VALUE));
		skipButton.setMinimumSize(new Dimension(Utils.reDimension(120), 0));
		skipButton.setOpaque(false);
		skipPanel.add(skipButton);

		// connect button
		final ImageButton button = new ImageButton();
		button.setImage("drawable/img_button_next.png");
		button.setSize(Utils.reDimension(70), Utils.reDimension(70));
		button.addMouseListener(new MouseAdapter()
		{
			private boolean connecting;


			public void mouseClicked(MouseEvent e)
			{
				if(connecting) return;
				button.setImage("drawable/img_button_next_pressed.png");
				button.setSize(Utils.reDimension(70), Utils.reDimension(70));
				connecting = true;

				Thread t = new Thread(new Runnable()
				{
					public void run()
					{
						boolean error = false;
						String errorText = "";

						if(Utils.isGradleWithRetrofit())
						{
							SwingUtilities.invokeLater(new Runnable()
							{
								public void run()
								{
									new ABMToolWindowMain(mToolWindow);
								}
							});
						}
						else
						{
							error = true;
							errorText = mMessages.getString("connect_gradle_message_error");
						}

						if(error)
						{
							connecting = false;
							JOptionPane.showMessageDialog(null, Utils.generateMessage(errorText), mMessages.getString("global_error_title"), JOptionPane.ERROR_MESSAGE);
							SwingUtilities.invokeLater(new Runnable()
							{
								public void run()
								{
									button.setImage("drawable/img_button_next.png");
								}
							});
						}
						else SwingUtilities.invokeLater(new Runnable()
						{
							public void run()
							{
								new ABMToolWindowMain(mToolWindow);
							}
						});
					}
				});
				t.start();
			}


			public void mousePressed(MouseEvent e)
			{
				if(connecting) return;
				button.setImage("drawable/img_button_next_pressed.png");
				button.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}


			public void mouseReleased(MouseEvent e)
			{
				if(connecting) return;
				button.setImage("drawable/img_button_next.png");
				button.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}
		});

		bottomPanel.add(button);

		skipButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						new ABMToolWindowMain(mToolWindow);
					}
				});
			}
		});
	}
}
