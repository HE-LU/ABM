package com.apiary.abm.ui;

import com.apiary.abm.utility.Utils;
import com.apiary.abm.view.JBackgroundPanel;
import com.intellij.openapi.wm.ToolWindow;
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
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class ABMToolWindowConnect extends JFrame
{
	final static String PANEL_DOCUMENTATION = "panelDocumentation";
	final static String PANEL_WEB_URL = "panelWebUrl";
	final static String PANEL_LOCAL_FILE = "panelLocalFile";

	private ToolWindow mToolWindow;


	public ABMToolWindowConnect(ToolWindow toolWindow)
	{
		mToolWindow = toolWindow;
		mToolWindow.getContentManager().removeAllContents(true);

		initLayout();
	}


	private void initLayout()
	{
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

		topPanel.setMinimumSize(new Dimension(0, Utils.reDimension(20)));
		bottomPanel.setMinimumSize(new Dimension(0, Utils.reDimension(20)));

		topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Utils.reDimension(20)));
		bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Utils.reDimension(20)));

		// add elements
		topPanel.setLayout(new MigLayout("insets 0 55 20 55, flowy, fillx, filly", "[fill, grow]", "[fill]"));
		middlePanel.setLayout(new MigLayout("insets 0 15 0 15, flowy, fillx, filly", "[grow, center]", "[][]"));
		bottomPanel.setLayout(new MigLayout("insets " + Utils.reDimension(20) + " 0 0 0, flowy, fillx, filly", "[grow, center]", "[center, top]"));

		topPanel.setOpaque(false);
		middlePanel.setOpaque(false);
		bottomPanel.setOpaque(false);

		myToolWindowContent.add(topPanel);
		myToolWindowContent.add(middlePanel);
		myToolWindowContent.add(bottomPanel);

		// Connect label
		JLabel infoText = new JLabel("<html><center>" + messages.getString("connect_header") + "</center></html>");
		infoText.setForeground(Color.WHITE);
		infoText.setFont(new Font("Ariel", Font.BOLD, Utils.getFontSizePanelHeader()));
		infoText.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(infoText);

		// init middle panel
		initMiddlePanel(middlePanel);

		// connect button
		final JLabel button = new JLabel();
		button.setOpaque(false);
		button.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_connect.png") + "' width='" + Utils.reDimension(20) + "' height='" + Utils.reDimension(20) + "' /></html>");

		button.addMouseListener(new MouseAdapter()
		{
			private boolean reloading;


			public void mouseClicked(MouseEvent e)
			{
				if(reloading) return;

				button.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/animation_connect.gif") + "' width='" + Utils.reDimension(20) + "' height='" + Utils.reDimension(20) + "' /></html>");
				reloading = true;

				Thread t = new Thread(new Runnable()
				{
					public void run()
					{
						//						try
						//						{
						//							String inputFilePath = Utils.saveWebFileToTmp(textField.getText());
						//							String tmp = Utils.readFileAsString(inputFilePath, StandardCharsets.UTF_8);
						//
						//							Preferences preferences = new Preferences();
						//							preferences.setApiaryBlueprintUrl(textField.getText());
						//							//							preferences.setApiaryBlueprintRaw(tmp);
						//
						//							SwingUtilities.invokeLater(new Runnable()
						//							{
						//								public void run()
						//								{
						//									new ABMToolWindowMain(mToolWindow);
						//								}
						//							});
						//						}
						//						catch(IOException e)
						//						{
						//							e.printStackTrace();
						//							reloading = false;
						//							SwingUtilities.invokeLater(new Runnable()
						//							{
						//								public void run()
						//								{
						//									labelError.setVisible(true);
						//									button.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_connect.png") + "' width='90' height='90' /></html>");
						//
						//								}
						//							});
						//						}
					}
				});
				t.start();


			}


			public void mousePressed(MouseEvent e)
			{
				if(reloading) return;
				button.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_connect_pressed.png") + "' width='" + Utils.reDimension(20) + "' height='" + Utils.reDimension(20) + "' /></html>");
			}


			public void mouseReleased(MouseEvent e)
			{
				if(reloading) return;
				button.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_connect.png") + "' width='" + Utils.reDimension(20) + "' height='" + Utils.reDimension(20) + "' /></html>");
			}
		});

		bottomPanel.add(button);
	}


	private void initMiddlePanel(JPanel rootPanel)
	{
		final ResourceBundle messages = ResourceBundle.getBundle("values/strings");

		// Chose Type
		JBackgroundPanel typePanel = new JBackgroundPanel("img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		typePanel.setLayout(new MigLayout("insets 12 12 18 19, flowy, fillx, filly", "[fill, grow]", "[][][][]"));
		typePanel.setOpaque(false);
		typePanel.setMaximumSize(new Dimension(600, Integer.MAX_VALUE));

		// Chose type label
		JLabel labelType = new JLabel("<html><center>" + messages.getString("connect_type") + "</center></html>");
		labelType.setForeground(Color.WHITE);
		labelType.setFont(new Font("Ariel", Font.BOLD, Utils.getFontSizeMedium()));
		labelType.setHorizontalAlignment(SwingConstants.CENTER);
		typePanel.add(labelType);

		// Create the radio buttons.
		JRadioButton btnApiaryDoc = new JRadioButton("Apiary documentation");
		btnApiaryDoc.setActionCommand("Apiary documentation");
		btnApiaryDoc.setMargin(new Insets(0, 0, 0, 0));
		btnApiaryDoc.setFont(new Font("Ariel", Font.BOLD, Utils.getFontSizeSmall()));
		btnApiaryDoc.setOpaque(false);
		btnApiaryDoc.setSelected(true);

		JRadioButton btnWebUrl = new JRadioButton("Web URL");
		btnWebUrl.setActionCommand("Web URL");
		btnWebUrl.setMargin(new Insets(0, 0, 0, 0));
		btnWebUrl.setFont(new Font("Ariel", Font.BOLD, Utils.getFontSizeSmall()));
		btnWebUrl.setOpaque(false);

		JRadioButton btnLocal = new JRadioButton("Local file");
		btnLocal.setActionCommand("Local file");
		btnLocal.setMargin(new Insets(0, 0, 0, 0));
		btnLocal.setFont(new Font("Ariel", Font.BOLD, Utils.getFontSizeSmall()));
		btnLocal.setOpaque(false);

		//Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(btnApiaryDoc);
		group.add(btnWebUrl);
		group.add(btnLocal);
		typePanel.add(btnApiaryDoc);
		typePanel.add(btnWebUrl);
		typePanel.add(btnLocal);
		rootPanel.add(typePanel);


		// Content
		JBackgroundPanel contentPanel = new JBackgroundPanel("img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		contentPanel.setLayout(new MigLayout("insets 0, flowy, fillx, filly", "[fill, grow]", "[]"));
		contentPanel.setOpaque(false);
		contentPanel.setMaximumSize(new Dimension(600, Integer.MAX_VALUE));

		final JPanel cards = new JPanel(new CardLayout());
		cards.setOpaque(false);


		// Content documentation
		final JPanel cardDocumentation = new JPanel();
		cardDocumentation.setLayout(new MigLayout("insets 12 12 18 19, flowy, fillx, filly", "[fill, grow]", "[][][][]"));
		cardDocumentation.setOpaque(false);
		cardDocumentation.setMaximumSize(new Dimension(600, Integer.MAX_VALUE));

		JLabel labelDocumentationUrl = new JLabel("<html><center>" + messages.getString("connect_message_documentation_url") + "</center></html>");
		labelDocumentationUrl.setForeground(Color.WHITE);
		labelDocumentationUrl.setFont(new Font("Ariel", Font.BOLD, Utils.getFontSizeMedium()));
		labelDocumentationUrl.setHorizontalAlignment(SwingConstants.CENTER);
		cardDocumentation.add(labelDocumentationUrl);

		final JTextField textFieldDocumentationUrl = new JTextField();
		textFieldDocumentationUrl.setText("http://docs.tuxilero.apiary.io/");
		cardDocumentation.add(textFieldDocumentationUrl);

		JLabel labelDocumentationToken = new JLabel("<html><center>" + messages.getString("connect_message_documentation_token") + "</center></html>");
		labelDocumentationToken.setForeground(Color.WHITE);
		labelDocumentationToken.setFont(new Font("Ariel", Font.BOLD, Utils.getFontSizeMedium()));
		labelDocumentationToken.setHorizontalAlignment(SwingConstants.CENTER);
		cardDocumentation.add(labelDocumentationToken, "gap 0 0 10 0");

		final JTextField textFieldDocumentationToken = new JTextField();
		textFieldDocumentationToken.setText("824b074bb727d3242fd960f8c5c4cfa9");
		cardDocumentation.add(textFieldDocumentationToken);

		cards.add(cardDocumentation, PANEL_DOCUMENTATION);


		// Content web url
		final JPanel cardWebUrl = new JPanel();
		cardWebUrl.setLayout(new MigLayout("insets 12 12 18 19, flowy, fillx, filly", "[fill, grow]", "[][]"));
		cardWebUrl.setOpaque(false);
		cardWebUrl.setMaximumSize(new Dimension(600, Integer.MAX_VALUE));

		JLabel labelWebUrlMessage = new JLabel("<html><center>" + messages.getString("connect_message_web_url") + "</center></html>");
		labelWebUrlMessage.setForeground(Color.WHITE);
		labelWebUrlMessage.setFont(new Font("Ariel", Font.BOLD, Utils.getFontSizeMedium()));
		labelWebUrlMessage.setHorizontalAlignment(SwingConstants.CENTER);
		cardWebUrl.add(labelWebUrlMessage);

		final JTextField textFieldWebUrl = new JTextField();
		textFieldWebUrl.setText("http://127.0.0.1:8080/share/my.blueprint");
		cardWebUrl.add(textFieldWebUrl);

		cards.add(cardWebUrl, PANEL_WEB_URL);


		// Content local file
		final JPanel cardLocalFile = new JPanel();
		cardLocalFile.setLayout(new MigLayout("insets 12 12 18 19, flowy, fillx, filly", "[fill, grow]", "[]"));
		cardLocalFile.setOpaque(false);
		cardLocalFile.setMaximumSize(new Dimension(600, Integer.MAX_VALUE));

		JLabel labelLocalMessage = new JLabel("<html><center>" + messages.getString("connect_message_local_file") + "</center></html>");
		labelLocalMessage.setForeground(Color.WHITE);
		labelLocalMessage.setFont(new Font("Ariel", Font.BOLD, Utils.getFontSizeMedium()));
		labelLocalMessage.setHorizontalAlignment(SwingConstants.CENTER);
		cardLocalFile.add(labelLocalMessage);

		cards.add(cardLocalFile, PANEL_LOCAL_FILE);


		contentPanel.add(cards);
		rootPanel.add(contentPanel);


		// error label
		final JLabel labelError = new JLabel();
		labelError.setForeground(Color.RED);
		labelError.setFont(new Font("Ariel", Font.BOLD, Utils.getFontSizeMedium()));
		labelError.setHorizontalAlignment(SwingConstants.CENTER);
		labelError.setText("<html><center>" + messages.getString("connect_message_error") + "</html></center>");
		labelError.setVisible(false);
		rootPanel.add(labelError);


		//Register a listener for the radio buttons.
		btnApiaryDoc.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				CardLayout layout = (CardLayout) (cards.getLayout());
				layout.show(cards, PANEL_DOCUMENTATION);
				pack();
			}
		});
		btnWebUrl.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				CardLayout layout = (CardLayout) (cards.getLayout());
				layout.show(cards, PANEL_WEB_URL);
				pack();
			}
		});
		btnLocal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				CardLayout layout = (CardLayout) (cards.getLayout());
				layout.show(cards, PANEL_LOCAL_FILE);
				pack();
			}
		});
	}
}
