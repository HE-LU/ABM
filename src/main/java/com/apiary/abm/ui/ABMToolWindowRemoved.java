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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class ABMToolWindowRemoved extends JFrame
{
	private ToolWindow mToolWindow;
	private TreeNodeEntity mEntity;
	private final ResourceBundle mMessages = ResourceBundle.getBundle("values/strings");
	private List<String> mMethodList = new ArrayList<String>();


	public ABMToolWindowRemoved(ToolWindow toolWindow, TreeNodeEntity entity)
	{
		mToolWindow = toolWindow;
		mEntity = new TreeNodeEntity(entity);
		mToolWindow.getContentManager().removeAllContents(true);

		Utils.trackPage("Removed screen");

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
		final JBScrollPane middleScrollPanel = new JBScrollPane(middlePanel, JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JBScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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

		// Top Panel label
		final JLabel infoText = new JLabel("<html><center>" + mMessages.getString("removed_header") + "</center></html>");
		infoText.setForeground(Color.WHITE);
		infoText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_LARGE)));
		infoText.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(infoText);


		// Panel identification
		final JBackgroundPanel identificationPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		identificationPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowy, fillx, filly", "[fill, grow][fill, grow]", "[]"));
		identificationPanel.setOpaque(false);
		identificationPanel.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));
		middlePanel.add(identificationPanel);

		// Label method name
		final JLabel methodNameText = new JLabel("<html><center>" + mMessages.getString("removed_message_method_name") + " " + mEntity.getMethodName() + "</center></html>");
		methodNameText.setForeground(Color.WHITE);
		methodNameText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		methodNameText.setHorizontalAlignment(SwingConstants.CENTER);
		identificationPanel.add(methodNameText);

		// Label method
		final JLabel methodText = new JLabel("<html><center>" + mMessages.getString("removed_message_method") + " " + mEntity.getMethod() + "</center></html>");
		methodText.setForeground(Color.WHITE);
		methodText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		methodText.setHorizontalAlignment(SwingConstants.CENTER);
		identificationPanel.add(methodText);

		// Label uri
		final JLabel uriText = new JLabel("<html><center>" + mMessages.getString("removed_message_uri") + " " + mEntity.getUri() + "</center></html>");
		uriText.setForeground(Color.WHITE);
		uriText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		uriText.setHorizontalAlignment(SwingConstants.CENTER);
		identificationPanel.add(uriText);


		// Panel info
		final JBackgroundPanel infoPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		infoPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[fill, grow]", "[]"));
		infoPanel.setOpaque(false);
		infoPanel.setMaximumSize(new Dimension(Utils.reDimension(330), Integer.MAX_VALUE));
		middlePanel.add(infoPanel);

		// Label hide note
		final JLabel infoNoteText = new JLabel("<html><center>" + mMessages.getString("removed_message_info_note") + "</center></html>");
		infoNoteText.setForeground(Color.WHITE);
		infoNoteText.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
		infoNoteText.setHorizontalAlignment(SwingConstants.CENTER);
		infoPanel.add(infoNoteText);


		// Panel hide
		final JBackgroundPanel hidePanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		hidePanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[fill, grow][fill, grow]", "[]" + Utils.reDimension(15) + "[]"));
		hidePanel.setOpaque(false);
		hidePanel.setMaximumSize(new Dimension(Utils.reDimension(330), Integer.MAX_VALUE));
		middlePanel.add(hidePanel);

		// Label hide
		final JLabel hideText = new JLabel("<html><center>" + mMessages.getString("removed_message_hide") + "</center></html>");
		hideText.setForeground(Color.WHITE);
		hideText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		hideText.setHorizontalAlignment(SwingConstants.CENTER);
		hidePanel.add(hideText);

		// Button hide
		final JButton hideButton = new JButton(mMessages.getString("removed_button_hide"));
		hideButton.setMaximumSize(new Dimension(Utils.reDimension(120), Integer.MAX_VALUE));
		hideButton.setMinimumSize(new Dimension(Utils.reDimension(120), 0));
		hideButton.setOpaque(false);
		hidePanel.add(hideButton, "wrap");

		// Label hide note
		final JLabel hideNoteText = new JLabel("<html><center>" + mMessages.getString("removed_message_hide_note") + "</center></html>");
		hideNoteText.setForeground(Color.WHITE);
		hideNoteText.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
		hideNoteText.setHorizontalAlignment(SwingConstants.CENTER);
		hidePanel.add(hideNoteText, "span 2");


		// Panel method and URI
		final JBackgroundPanel requestEditPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		requestEditPanel.setLayout(new MigLayout("insets " + Utils.reDimension(13) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[fill, grow][fill, grow]", "[][]" + Utils.reDimension(15) + "[]"));
		requestEditPanel.setOpaque(false);
		requestEditPanel.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));
		middlePanel.add(requestEditPanel);

		// Label uri
		final JLabel uriEditText = new JLabel("<html><center>" + mMessages.getString("removed_message_uri_edit") + "</center></html>");
		uriEditText.setForeground(Color.WHITE);
		uriEditText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		uriEditText.setHorizontalAlignment(SwingConstants.CENTER);
		requestEditPanel.add(uriEditText);

		// TextField uri
		final JTextField uriEditTextField = new JTextField(mEntity.getUri());
		uriEditTextField.setMaximumSize(new Dimension(Utils.reDimension(170), Integer.MAX_VALUE));
		uriEditTextField.setMinimumSize(new Dimension(Utils.reDimension(170), 0));
		uriEditTextField.setText(mEntity.getUri());
		requestEditPanel.add(uriEditTextField, "wrap");

		// Label uri
		final JLabel uriEditExampleText = new JLabel("<html><center>" + mMessages.getString("removed_message_uri_edit_example") + "</center></html>");
		uriEditExampleText.setForeground(Color.WHITE);
		uriEditExampleText.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
		uriEditExampleText.setHorizontalAlignment(SwingConstants.CENTER);
		requestEditPanel.add(uriEditExampleText, "span, wrap");

		uriEditTextField.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				char ch = e.getKeyChar();
				if(!Character.isAlphabetic(ch) && ch != '/') e.consume();
			}
		});


		// Label method
		final JLabel methodEditText = new JLabel("<html><center>" + mMessages.getString("removed_message_method_edit") + "</center></html>");
		methodEditText.setForeground(Color.WHITE);
		methodEditText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		methodEditText.setHorizontalAlignment(SwingConstants.CENTER);
		requestEditPanel.add(methodEditText);

		// TextField method
		mMethodList.add("GET");
		mMethodList.add("POST");
		mMethodList.add("PUT");
		mMethodList.add("DELETE");
		mMethodList.add("UPDATE");
		final JComboBox methodEditComboBox = new com.intellij.openapi.ui.ComboBox(mMethodList.toArray());
		methodEditComboBox.setMinimumSize(new Dimension(Utils.reDimension(160), 0));
		methodEditComboBox.setSelectedItem(mEntity.getMethod());
		requestEditPanel.add(methodEditComboBox);


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
		buttonNext.setImage("drawable/img_button_save.png");
		buttonNext.setSize(Utils.reDimension(70), Utils.reDimension(70));

		buttonNext.addMouseListener(new MouseAdapter()
		{
			private boolean progress;


			public void mouseClicked(MouseEvent e)
			{
				if(progress) return;
				buttonNext.setImage("drawable/img_button_save_pressed.png");
				buttonNext.setSize(Utils.reDimension(70), Utils.reDimension(70));
				progress = true;

				Thread t = new Thread(new Runnable()
				{
					public void run()
					{
						boolean error = false;
						String errorText = "";

						// check uri name
						if(uriEditTextField.getText().isEmpty())
						{
							error = true;
							errorText = mMessages.getString("implementation_message_error_method");
						}


						if(error)
						{
							progress = false;
							JOptionPane.showMessageDialog(null, Utils.generateMessage(errorText), mMessages.getString("global_error_title"), JOptionPane.ERROR_MESSAGE);
							SwingUtilities.invokeLater(new Runnable()
							{
								public void run()
								{
									buttonNext.setImage("drawable/img_button_save.png");
								}
							});
						}
						else
						{
							String oldUri = mEntity.getUri();
							String oldMethod = mEntity.getMethod();

							mEntity.setUri(uriEditTextField.getText());
							mEntity.setMethod(mMethodList.get(methodEditComboBox.getSelectedIndex()));

							ConfigPreferences configPreferences = new ConfigPreferences();
							configPreferences.saveTreeNodeEntity(mEntity, oldUri, oldMethod);

							SwingUtilities.invokeLater(new Runnable()
							{
								public void run()
								{
									new ABMToolWindowMain(mToolWindow);
								}
							});
						}
					}
				});
				t.start();
			}


			public void mousePressed(MouseEvent e)
			{
				if(progress) return;
				buttonNext.setImage("drawable/img_button_save_pressed.png");
				buttonNext.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}


			public void mouseReleased(MouseEvent e)
			{
				if(progress) return;
				buttonNext.setImage("drawable/img_button_save.png");
				buttonNext.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}
		});

		bottomPanel.add(buttonBack, "right");
		bottomPanel.add(buttonNext, "left");

		hideButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane pane = new JOptionPane(Utils.generateMessage(mMessages.getString("removed_dialog_message_add_hidden")));
				Object[] options = new String[]{mMessages.getString("global_yes"), mMessages.getString("global_no")};
				pane.setOptions(options);
				JDialog dialog = pane.createDialog(new JFrame(), mMessages.getString("removed_dialog_message_add_hidden_header"));
				dialog.setVisible(true);
				Object obj = pane.getValue();
				int result = -1;
				for(int k = 0; k < options.length; k++)
					if(options[k].equals(obj)) result = k;
				if(result == 0)
				{
					mEntity.setHidden(TreeNodeEntity.STATE_REMOVED);
					ConfigPreferences configPreferences = new ConfigPreferences();
					configPreferences.saveTreeNodeEntity(mEntity);
					new ABMToolWindowMain(mToolWindow);
				}
			}
		});


		// Scroll to top after layout init.
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				middleScrollPanel.getVerticalScrollBar().setValue(0);
			}
		});
	}
}
