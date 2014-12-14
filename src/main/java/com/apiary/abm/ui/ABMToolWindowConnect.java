package com.apiary.abm.ui;

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
import java.awt.image.BufferedImage;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class ABMToolWindowConnect extends JFrame
{
	final static String CARD_DOCUMENTATION = "panelDocumentation";
	final static String CARD_WEB_URL = "panelWebUrl";
	final static String CARD_LOCAL_FILE = "panelLocalFile";

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
		final JBackgroundPanel myToolWindowContent = new JBackgroundPanel("drawable/img_background.png", JBackgroundPanel.JBackgroundPanelType.BACKGROUND_REPEAT);
		final ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
		final Content content = contentFactory.createContent(myToolWindowContent, "", false);
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
		final JLabel infoText = new JLabel("<html><center>" + messages.getString("connect_header") + "</center></html>");
		infoText.setForeground(Color.WHITE);
		infoText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_LARGE)));
		infoText.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(infoText);

		// init middle panel
		initMiddlePanel(middlePanel);

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

				// TODO
				//				Thread t = new Thread(new Runnable()
				//				{
				//					public void run()
				//					{
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
				//					}
				//				});
				//				t.start();
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


	private void initMiddlePanel(JPanel rootPanel)
	{
		final ResourceBundle messages = ResourceBundle.getBundle("values/strings");

		// Chose Type
		final JBackgroundPanel typePanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		typePanel.setLayout(new MigLayout("insets "+ Utils.reDimension(12) +" "+ Utils.reDimension(12) +" "+ Utils.reDimension(18) +" "+ Utils.reDimension(19) +", flowy, fillx, filly", "[fill, grow]", "[][][][]"));
		typePanel.setOpaque(false);
		typePanel.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));

		// Chose type label
		final JLabel labelType = new JLabel("<html><center>" + messages.getString("connect_type") + "</center></html>");
		labelType.setForeground(Color.WHITE);
		labelType.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		labelType.setHorizontalAlignment(SwingConstants.CENTER);
		typePanel.add(labelType);

		// Create the radio buttons.
		final JRadioButton btnApiaryDoc = new JRadioButton(messages.getString("connect_radio_documentation"));
		btnApiaryDoc.setActionCommand(messages.getString("connect_radio_documentation"));
		btnApiaryDoc.setMargin(new Insets(0, 0, 0, 0));
		btnApiaryDoc.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
		btnApiaryDoc.setOpaque(false);
		btnApiaryDoc.setSelected(true);

		final JRadioButton btnWebUrl = new JRadioButton(messages.getString("connect_radio_web_url"));
		btnWebUrl.setActionCommand(messages.getString("connect_radio_web_url"));
		btnWebUrl.setMargin(new Insets(0, 0, 0, 0));
		btnWebUrl.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
		btnWebUrl.setOpaque(false);

		final JRadioButton btnLocal = new JRadioButton(messages.getString("connect_radio_local_file"));
		btnLocal.setActionCommand(messages.getString("connect_radio_local_file"));
		btnLocal.setMargin(new Insets(0, 0, 0, 0));
		btnLocal.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
		btnLocal.setOpaque(false);

		// Group the radio buttons.
		final ButtonGroup group = new ButtonGroup();
		group.add(btnApiaryDoc);
		group.add(btnWebUrl);
		group.add(btnLocal);
		typePanel.add(btnApiaryDoc);
		typePanel.add(btnWebUrl);
		typePanel.add(btnLocal);
		rootPanel.add(typePanel);


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
		cardDocumentation.setLayout(new MigLayout("insets "+ Utils.reDimension(12) +" "+ Utils.reDimension(12) +" "+ Utils.reDimension(18) +" "+ Utils.reDimension(19) +", flowy, fillx, filly", "[fill, grow]", "[][][][]"));
		cardDocumentation.setOpaque(false);
		cardDocumentation.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));

		final JLabel labelDocumentationUrl = new JLabel("<html><center>" + messages.getString("connect_message_documentation_url") + "</center></html>");
		labelDocumentationUrl.setForeground(Color.WHITE);
		labelDocumentationUrl.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		labelDocumentationUrl.setHorizontalAlignment(SwingConstants.CENTER);
		cardDocumentation.add(labelDocumentationUrl);

		final JTextField textFieldDocumentationUrl = new JTextField();
		textFieldDocumentationUrl.setText("http://docs.tuxilero.apiary.io/"); // FIXME
		cardDocumentation.add(textFieldDocumentationUrl);

		final JLabel labelDocumentationToken = new JLabel("<html><center>" + messages.getString("connect_message_documentation_token") + "</center></html>");
		labelDocumentationToken.setForeground(Color.WHITE);
		labelDocumentationToken.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		labelDocumentationToken.setHorizontalAlignment(SwingConstants.CENTER);
		cardDocumentation.add(labelDocumentationToken, "gap 0 0 "+Utils.reDimension(5)+" 0");

		final JTextField textFieldDocumentationToken = new JTextField();
		textFieldDocumentationToken.setText("824b074bb727d3242fd960f8c5c4cfa9"); // FIXME
		cardDocumentation.add(textFieldDocumentationToken);

		cards.add(cardDocumentation, CARD_DOCUMENTATION);


		// Card content web url
		final JPanel cardWebUrl = new JPanel();
		cardWebUrl.setLayout(new MigLayout("insets "+ Utils.reDimension(12) +" "+ Utils.reDimension(12) +" "+ Utils.reDimension(18) +" "+ Utils.reDimension(19) +", flowy, fillx, filly", "[fill, grow]", "[][]"));
		cardWebUrl.setOpaque(false);
		cardWebUrl.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));

		final JLabel labelWebUrlMessage = new JLabel("<html><center>" + messages.getString("connect_message_web_url") + "</center></html>");
		labelWebUrlMessage.setForeground(Color.WHITE);
		labelWebUrlMessage.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		labelWebUrlMessage.setHorizontalAlignment(SwingConstants.CENTER);
		cardWebUrl.add(labelWebUrlMessage);

		final JTextField textFieldWebUrl = new JTextField();
		textFieldWebUrl.setText("http://127.0.0.1:8080/share/my.blueprint"); // FIXME
		cardWebUrl.add(textFieldWebUrl);

		cards.add(cardWebUrl, CARD_WEB_URL);


		// Card content local file
		final JPanel cardLocalFile = new JPanel();
		cardLocalFile.setLayout(new MigLayout("insets "+ Utils.reDimension(12) +" "+ Utils.reDimension(12) +" "+ Utils.reDimension(18) +" "+ Utils.reDimension(19) +", flowy, fillx, filly", "[fill, grow]", "[]"));
		cardLocalFile.setOpaque(false);
		cardLocalFile.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));

		final JLabel labelLocalMessage = new JLabel("<html><center>" + messages.getString("connect_message_local_file") + "</center></html>");
		labelLocalMessage.setForeground(Color.WHITE);
		labelLocalMessage.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		labelLocalMessage.setHorizontalAlignment(SwingConstants.CENTER);
		cardLocalFile.add(labelLocalMessage);

		// TODO local file



		cards.add(cardLocalFile, CARD_LOCAL_FILE);

		contentPanel.add(cards);
		rootPanel.add(contentPanel);


		// error label
		final JLabel labelError = new JLabel();
		labelError.setForeground(Color.RED);
		labelError.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
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
				layout.show(cards, CARD_DOCUMENTATION);
				pack();
			}
		});
		btnWebUrl.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				CardLayout layout = (CardLayout) (cards.getLayout());
				layout.show(cards, CARD_WEB_URL);
				pack();
			}
		});
		btnLocal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				CardLayout layout = (CardLayout) (cards.getLayout());
				layout.show(cards, CARD_LOCAL_FILE);
				pack();
			}
		});
	}
}