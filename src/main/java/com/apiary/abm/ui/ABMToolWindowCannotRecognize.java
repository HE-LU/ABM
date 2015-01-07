package com.apiary.abm.ui;

import com.apiary.abm.entity.TreeNodeEntity;
import com.apiary.abm.utility.ConfigPreferences;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class ABMToolWindowCannotRecognize extends JFrame
{
	private ToolWindow mToolWindow;
	private TreeNodeEntity mEntity;


	public ABMToolWindowCannotRecognize(ToolWindow toolWindow, TreeNodeEntity entity)
	{
		mToolWindow = toolWindow;
		mEntity = entity;
		mToolWindow.getContentManager().removeAllContents(true);

		initLayout();
	}


	private void initLayout()
	{
		final ResourceBundle messages = ResourceBundle.getBundle("values/strings");

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
		bottomPanel.setLayout(new MigLayout("insets " + Utils.reDimension(18) + " 0 0 0, flowx, fillx, filly", "[grow][grow]", "[center, top]"));

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
		final JLabel infoText = new JLabel("<html><center>" + messages.getString("cannot_recognize_header") + "</center></html>");
		infoText.setForeground(Color.WHITE);
		infoText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_LARGE)));
		infoText.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(infoText);


		// spacer
		final JLabel spacerText = new JLabel("<html><center></center></html>");
		middlePanel.add(spacerText);


		// Panel identification
		final JBackgroundPanel identificationPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		identificationPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[fill, grow]" + Utils.reDimension(50) + "[fill, grow]", "[]"));
		identificationPanel.setOpaque(false);
		identificationPanel.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));
		middlePanel.add(identificationPanel);

		// Label uri
		final JLabel uriText = new JLabel("<html><center>" + messages.getString("cannot_recognize_message_uri") + " " + mEntity.getUri() + "</center></html>");
		uriText.setForeground(Color.WHITE);
		uriText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		uriText.setHorizontalAlignment(SwingConstants.CENTER);
		identificationPanel.add(uriText);

		// Label method
		final JLabel methodText = new JLabel("<html><center>" + messages.getString("cannot_recognize_message_method") + " " + mEntity.getMethod() + "</center></html>");
		methodText.setForeground(Color.WHITE);
		methodText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		methodText.setHorizontalAlignment(SwingConstants.CENTER);
		identificationPanel.add(methodText);


		// Panel fill info
		final JBackgroundPanel fillInfoPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		fillInfoPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[fill, grow][fill, grow]", "[]"));
		fillInfoPanel.setOpaque(false);
		fillInfoPanel.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));
		middlePanel.add(fillInfoPanel);

		// Label fill name
		final JLabel fillNameText = new JLabel("<html><center>" + messages.getString("cannot_recognize_message_fill_name") + "</center></html>");
		fillNameText.setForeground(Color.WHITE);
		fillNameText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		fillNameText.setHorizontalAlignment(SwingConstants.CENTER);
		fillInfoPanel.add(fillNameText);

		// TextField name
		final JTextField textFieldFillName = new JTextField();
		textFieldFillName.setMaximumSize(new Dimension(Utils.reDimension(170), Integer.MAX_VALUE));
		textFieldFillName.setMinimumSize(new Dimension(Utils.reDimension(170), 0));
		fillInfoPanel.add(textFieldFillName, "wrap");

		// Label example name
		final JLabel exampleNameText = new JLabel("<html><center>" + textFieldFillName.getText() + "<a style=\"color: red\">Request.java</a><br />" + textFieldFillName.getText() + "<a style=\"color: red\">Parser.java</a><br />" + textFieldFillName.getText() + "<a style=\"color: red\">Entity.java</a></center></html>");
		exampleNameText.setForeground(Color.WHITE);
		exampleNameText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		exampleNameText.setHorizontalAlignment(SwingConstants.CENTER);
		fillInfoPanel.add(exampleNameText, "span");


		// Label error
		final JLabel labelError = new JLabel();
		labelError.setForeground(Color.RED);
		labelError.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		labelError.setHorizontalAlignment(SwingConstants.CENTER);
		labelError.setText("<html><center>" + messages.getString("cannot_recognize_message_error") + "</html></center>");
		labelError.setVisible(false);
		middlePanel.add(labelError);

		// Back button
		final ImageButton buttonBack = new ImageButton();
		buttonBack.setImage("drawable/img_button_back.png");
		buttonBack.setSize(Utils.reDimension(70), Utils.reDimension(70));

		buttonBack.addMouseListener(new MouseAdapter()
		{
			private boolean progress;


			public void mouseClicked(MouseEvent e)
			{
				if(progress) return;
				buttonBack.setImage("drawable/img_button_back_pressed.png");
				buttonBack.setSize(Utils.reDimension(70), Utils.reDimension(70));
				progress = true;

				new ABMToolWindowMain(mToolWindow);
			}


			public void mousePressed(MouseEvent e)
			{
				if(progress) return;
				buttonBack.setImage("drawable/img_button_back_pressed.png");
				buttonBack.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}


			public void mouseReleased(MouseEvent e)
			{
				if(progress) return;
				buttonBack.setImage("drawable/img_button_back.png");
				buttonBack.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}
		});

		// Next button
		final ImageButton buttonNext = new ImageButton();
		buttonNext.setImage("drawable/img_button_next.png");
		buttonNext.setSize(Utils.reDimension(70), Utils.reDimension(70));

		buttonNext.addMouseListener(new MouseAdapter()
		{
			private boolean progress;


			public void mouseClicked(MouseEvent e)
			{
				if(progress) return;
				buttonNext.setImage("drawable/img_button_next_pressed.png");
				buttonNext.setSize(Utils.reDimension(70), Utils.reDimension(70));
				progress = true;

				Thread t = new Thread(new Runnable()
				{
					public void run()
					{
						boolean error = false;

						String text = textFieldFillName.getText();
						if(text.length()!=text.replaceAll("[^A-Za-z]", "").length()) error = true;
						else
						{
							mEntity.setName(text);
							ConfigPreferences cp = new ConfigPreferences();
							cp.saveTreeNodeEntity(mEntity);
						}

						if(error)
						{
							progress = false;
							SwingUtilities.invokeLater(new Runnable()
							{
								public void run()
								{
									labelError.setVisible(true);
									buttonNext.setImage("drawable/img_button_next.png");
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
				if(progress) return;
				buttonNext.setImage("drawable/img_button_next_pressed.png");
				buttonNext.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}


			public void mouseReleased(MouseEvent e)
			{
				if(progress) return;
				buttonNext.setImage("drawable/img_button_next.png");
				buttonNext.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}
		});

		bottomPanel.add(buttonBack, "right");
		bottomPanel.add(buttonNext, "left");

		textFieldFillName.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				char ch = e.getKeyChar();
				if(!Character.isAlphabetic(ch))
				{
					e.consume();
				}
			}
		});


		textFieldFillName.getDocument().addDocumentListener(new DocumentListener()
		{
			public void changedUpdate(DocumentEvent e)
			{
				update();
			}


			public void removeUpdate(DocumentEvent e)
			{
				update();
			}


			public void insertUpdate(DocumentEvent e)
			{
				update();
			}


			public void update()
			{
				exampleNameText.setText("<html><center>" + textFieldFillName.getText() + "<a style=\"color: red\">Request.java</a><br />" + textFieldFillName.getText() + "<a style=\"color: red\">Parser.java</a><br />" + textFieldFillName.getText() + "<a style=\"color: red\">Entity.java</a></center></html>");
			}
		});
	}
}
