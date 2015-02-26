package com.apiary.abm.ui;

import com.apiary.abm.ABMConfig;
import com.apiary.abm.entity.DocResponseEntity;
import com.apiary.abm.enums.ConnectionTypeEnum;
import com.apiary.abm.utility.Log;
import com.apiary.abm.utility.Network;
import com.apiary.abm.utility.Preferences;
import com.apiary.abm.utility.Utils;
import com.apiary.abm.view.ImageButton;
import com.apiary.abm.view.JBackgroundPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import net.miginfocom.swing.MigLayout;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class ABMToolWindowConnect extends JFrame
{
	final static String CARD_DOCUMENTATION = "panelDocumentation";
	final static String CARD_WEB_URL = "panelWebUrl";
	final static String CARD_LOCAL_FILE = "panelLocalFile";

	private ToolWindow mToolWindow;
	private final ResourceBundle mMessages = ResourceBundle.getBundle("values/strings");


	public ABMToolWindowConnect(ToolWindow toolWindow)
	{
		mToolWindow = toolWindow;
		mToolWindow.getContentManager().removeAllContents(true);

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
		middlePanel.setLayout(new MigLayout("insets 0 " + Utils.reDimension(15) + " 0 " + Utils.reDimension(15) + ", flowy, fillx, filly", "[grow, center]", "[][]"));
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
		final JLabel infoText = new JLabel("<html><center>" + mMessages.getString("connect_header") + "</center></html>");
		infoText.setForeground(Color.WHITE);
		infoText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_LARGE)));
		infoText.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(infoText);

		// init middle panel

		// Chose Type
		final JBackgroundPanel typePanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		typePanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowy, fillx, filly", "[fill, grow]", "[][][][]"));
		typePanel.setOpaque(false);
		typePanel.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));

		// Chose type label
		final JLabel labelType = new JLabel("<html><center>" + mMessages.getString("connect_type") + "</center></html>");
		labelType.setForeground(Color.WHITE);
		labelType.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		labelType.setHorizontalAlignment(SwingConstants.CENTER);

		// Create the radio buttons.
		final JRadioButton radBtnApiaryDoc = new JRadioButton(mMessages.getString("connect_radio_documentation"));
		radBtnApiaryDoc.setActionCommand(mMessages.getString("connect_radio_documentation"));
		radBtnApiaryDoc.setMargin(new Insets(0, 0, 0, 0));
		radBtnApiaryDoc.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
		radBtnApiaryDoc.setOpaque(false);
		radBtnApiaryDoc.setSelected(true);

		final JRadioButton radBtnWebUrl = new JRadioButton(mMessages.getString("connect_radio_web_url"));
		radBtnWebUrl.setActionCommand(mMessages.getString("connect_radio_web_url"));
		radBtnWebUrl.setMargin(new Insets(0, 0, 0, 0));
		radBtnWebUrl.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
		radBtnWebUrl.setOpaque(false);

		final JRadioButton radBtnLocal = new JRadioButton(mMessages.getString("connect_radio_local_file"));
		radBtnLocal.setActionCommand(mMessages.getString("connect_radio_local_file"));
		radBtnLocal.setMargin(new Insets(0, 0, 0, 0));
		radBtnLocal.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
		radBtnLocal.setOpaque(false);

		// Group the radio buttons.
		final ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(radBtnApiaryDoc);
		buttonGroup.add(radBtnWebUrl);
		buttonGroup.add(radBtnLocal);
		typePanel.add(labelType);
		typePanel.add(radBtnApiaryDoc);
		typePanel.add(radBtnWebUrl);
		typePanel.add(radBtnLocal);
		middlePanel.add(typePanel);


		// Content
		final JBackgroundPanel contentPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		contentPanel.setLayout(new MigLayout("insets 0, flowy, fillx, filly", "[fill, grow]", "[]"));
		contentPanel.setOpaque(false);
		contentPanel.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));

		// Cards layout
		final JPanel cards = new JPanel(new CardLayout());
		cards.setOpaque(false);

		// Card content documentation
		final JPanel cardDocumentation = new JPanel();
		cardDocumentation.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowy, fillx, filly", "[fill, grow]", "[][][][]"));
		cardDocumentation.setOpaque(false);
		cardDocumentation.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));

		final JLabel labelDocumentationUrl = new JLabel("<html><center>" + mMessages.getString("connect_message_documentation_url") + "</center></html>");
		labelDocumentationUrl.setForeground(Color.WHITE);
		labelDocumentationUrl.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		labelDocumentationUrl.setHorizontalAlignment(SwingConstants.CENTER);

		final JTextField textFieldDocumentationUrl = new JTextField();
		if(ABMConfig.DEBUG) textFieldDocumentationUrl.setText("tuxilero");

		final JLabel labelDocumentationToken = new JLabel("<html><center>" + mMessages.getString("connect_message_documentation_token") + "</center></html>");
		labelDocumentationToken.setForeground(Color.WHITE);
		labelDocumentationToken.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		labelDocumentationToken.setHorizontalAlignment(SwingConstants.CENTER);

		final JTextField textFieldDocumentationToken = new JTextField();
		if(ABMConfig.DEBUG) textFieldDocumentationToken.setText("824b074bb727d3242fd960f8c5c4cfa9");

		cardDocumentation.add(labelDocumentationUrl);
		cardDocumentation.add(textFieldDocumentationUrl);
		cardDocumentation.add(labelDocumentationToken, "gap 0 0 " + Utils.reDimension(5) + " 0");
		cardDocumentation.add(textFieldDocumentationToken);
		cards.add(cardDocumentation, CARD_DOCUMENTATION);


		// Card content web url
		final JPanel cardWebUrl = new JPanel();
		cardWebUrl.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowy, fillx, filly", "[fill, grow]", "[][]"));
		cardWebUrl.setOpaque(false);
		cardWebUrl.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));

		final JLabel labelWebUrlMessage = new JLabel("<html><center>" + mMessages.getString("connect_message_web_url") + "</center></html>");
		labelWebUrlMessage.setForeground(Color.WHITE);
		labelWebUrlMessage.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		labelWebUrlMessage.setHorizontalAlignment(SwingConstants.CENTER);

		final JTextField textFieldWebUrl = new JTextField();
		textFieldWebUrl.setText("http://127.0.0.1:8080/share/my.blueprint"); // FIXME

		cardWebUrl.add(labelWebUrlMessage);
		cardWebUrl.add(textFieldWebUrl);
		cards.add(cardWebUrl, CARD_WEB_URL);


		// Card content local file
		final JPanel cardLocalFile = new JPanel();
		cardLocalFile.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowy, fillx, filly", "[fill, grow][]", "[][]"));
		cardLocalFile.setOpaque(false);
		cardLocalFile.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));

		final JLabel labelLocalMessage = new JLabel("<html><center>" + mMessages.getString("connect_message_local_file") + "</center></html>");
		labelLocalMessage.setForeground(Color.WHITE);
		labelLocalMessage.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		labelLocalMessage.setHorizontalAlignment(SwingConstants.CENTER);

		final JTextField textFieldLocalPath = new JTextField();
		textFieldLocalPath.setText("/home/tuxilero/input.abm");

		final JButton buttonLocalBrowse = new JButton("Browse...");
		buttonLocalBrowse.setOpaque(false);

		cardLocalFile.add(labelLocalMessage, "span 2");
		cardLocalFile.add(textFieldLocalPath, "cell 0 1");
		cardLocalFile.add(buttonLocalBrowse, "cell 1 1");
		cards.add(cardLocalFile, CARD_LOCAL_FILE);
		contentPanel.add(cards);
		middlePanel.add(contentPanel);


		//Register a listener for the radio buttons.
		radBtnApiaryDoc.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				CardLayout layout = (CardLayout) (cards.getLayout());
				layout.show(cards, CARD_DOCUMENTATION);
				pack();
			}
		});
		radBtnWebUrl.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				CardLayout layout = (CardLayout) (cards.getLayout());
				layout.show(cards, CARD_WEB_URL);
				pack();
			}
		});
		radBtnLocal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				CardLayout layout = (CardLayout) (cards.getLayout());
				layout.show(cards, CARD_LOCAL_FILE);
				pack();
			}
		});
		buttonLocalBrowse.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				JFileChooser fileChooser = new JFileChooser();
				File lastFile = new File(textFieldLocalPath.getText());
				if(lastFile != null && lastFile.exists()) fileChooser.setCurrentDirectory(lastFile);
				else fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(ABMToolWindowConnect.this);
				if(result == JFileChooser.APPROVE_OPTION)
				{
					File selectedFile = fileChooser.getSelectedFile();
					Log.d("Selected file: " + selectedFile.getAbsolutePath());
					textFieldLocalPath.setText(selectedFile.getAbsolutePath());
				}
			}
		});

		// connect button
		final ImageButton button = new ImageButton();
		button.setImage("drawable/img_button_connect.png");
		button.setSize(Utils.reDimension(70), Utils.reDimension(70));

		button.addMouseListener(new MouseAdapter()
		{
			private boolean connecting;


			public void mouseClicked(MouseEvent e)
			{
				if(connecting) return;
				button.setImage("drawable/animation_connect.gif");
				button.setSize(Utils.reDimension(70), Utils.reDimension(70));
				connecting = true;

				Thread t = new Thread(new Runnable()
				{
					public void run()
					{
						boolean error = false;
						String errorText = "";
						if(radBtnApiaryDoc.isSelected())
						{
							try
							{
								String output = Network.requestBlueprintFromApiary(textFieldDocumentationUrl.getText(), textFieldDocumentationToken.getText());
								DocResponseEntity response = Utils.parseJsonDoc(output);

								if(response == null) throw new Exception(mMessages.getString("connect_message_error_gson"));

								if(response.getError() || response.getCode() == null) throw new Exception(response.getMessage());

								if(!Network.isBlueprintValid(response.getCode()))
									throw new Exception(mMessages.getString("connect_message_error_parsing"));

								String tmpFilePath = Utils.saveStringToTmpFile(ABMConfig.FILE_BLUEPRINT, response.getCode());
								Preferences preferences = new Preferences();
								preferences.setBlueprintConnectionType(ConnectionTypeEnum.CONNECTION_TYPE_DOC);
								preferences.setBlueprintConnectionPath(textFieldDocumentationUrl.getText());
								preferences.setBlueprintConnectionDocKey(textFieldDocumentationToken.getText());
								preferences.setBlueprintTmpFileLocation(tmpFilePath);
							}
							catch(Exception e1)
							{
								errorText = e1.getMessage();
								Log.e("Exception message: " + e1.getMessage());
								e1.printStackTrace();
								error = true;
							}
						}
						else if(radBtnWebUrl.isSelected())
						{
							try
							{
								String output = Network.requestBlueprintFromURL(textFieldWebUrl.getText());

								if(output == null) throw new Exception(mMessages.getString("connect_message_error_web"));

								if(!Network.isBlueprintValid(output))
									throw new Exception(mMessages.getString("connect_message_error_parsing"));

								String tmpFilePath = Utils.saveStringToTmpFile(ABMConfig.FILE_BLUEPRINT, output);
								Preferences preferences = new Preferences();
								preferences.setBlueprintConnectionType(ConnectionTypeEnum.CONNECTION_TYPE_WEB_URL);
								preferences.setBlueprintConnectionPath(textFieldWebUrl.getText());
								preferences.setBlueprintTmpFileLocation(tmpFilePath);
							}
							catch(Exception e1)
							{
								errorText = e1.getMessage();
								Log.e("Exception message: " + e1.getMessage());
								e1.printStackTrace();
								error = true;
							}
						}
						else if(radBtnLocal.isSelected())
						{
							try
							{
								String output = Utils.readFileAsString(textFieldLocalPath.getText(), Charset.forName("UTF-8"));

								if(output == null) throw new Exception(mMessages.getString("connect_message_error_file"));

								if(!Network.isBlueprintValid(output))
									throw new Exception(mMessages.getString("connect_message_error_parsing"));

								String tmpFilePath = Utils.saveStringToTmpFile(ABMConfig.FILE_BLUEPRINT, output);
								Preferences preferences = new Preferences();
								preferences.setBlueprintConnectionType(ConnectionTypeEnum.CONNECTION_TYPE_FILE);
								preferences.setBlueprintConnectionPath(textFieldLocalPath.getText());
								preferences.setBlueprintTmpFileLocation(tmpFilePath);
							}
							catch(Exception e1)
							{
								errorText = e1.getMessage();
								Log.e("Exception message: " + e1.getMessage());
								e1.printStackTrace();
								error = true;
							}
						}

						if(error)
						{
							connecting = false;
							JOptionPane.showMessageDialog(null, errorText, mMessages.getString("global_error_title"), JOptionPane.ERROR_MESSAGE);
							SwingUtilities.invokeLater(new Runnable()
							{
								public void run()
								{
									button.setImage("drawable/img_button_connect.png");
								}
							});
						}
						else SwingUtilities.invokeLater(new Runnable()
						{
							public void run()
							{
								new ABMToolWindowConnectGradle(mToolWindow);
							}
						});
					}
				});
				t.start();
			}


			public void mousePressed(MouseEvent e)
			{
				if(connecting) return;
				button.setImage("drawable/img_button_connect_pressed.png");
				button.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}


			public void mouseReleased(MouseEvent e)
			{
				if(connecting) return;
				button.setImage("drawable/img_button_connect.png");
				button.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}
		});

		bottomPanel.add(button);
	}
}
