package com.apiary.abm.ui;

import com.apiary.abm.entity.BodyObjectEntity;
import com.apiary.abm.entity.BodyVariableEntity;
import com.apiary.abm.entity.TreeNodeEntity;
import com.apiary.abm.entity.blueprint.HeadersEntity;
import com.apiary.abm.entity.blueprint.ParametersEntity;
import com.apiary.abm.enums.VariableEnum;
import com.apiary.abm.utility.ConfigPreferences;
import com.apiary.abm.utility.Utils;
import com.apiary.abm.view.ImageButton;
import com.apiary.abm.view.JBackgroundPanel;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.table.JBTable;

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
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class ABMToolWindowImplementationFirst extends JFrame
{
	private ToolWindow mToolWindow;
	private TreeNodeEntity mEntity;
	private final ResourceBundle mMessages = ResourceBundle.getBundle("values/strings");
	private List<Pair<String, JTextField>> mRequestTextFields = new ArrayList<Pair<String, JTextField>>();
	private List<Pair<String, JTextField>> mResponseTextFields = new ArrayList<Pair<String, JTextField>>();


	public ABMToolWindowImplementationFirst(ToolWindow toolWindow, TreeNodeEntity entity)
	{
		mToolWindow = toolWindow;
		mEntity = new TreeNodeEntity(entity);
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
		final JLabel infoText = new JLabel("<html><center>" + mMessages.getString("implementation_header_new") + "</center></html>");
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

		// Label method
		final JLabel methodText = new JLabel("<html><center>" + mMessages.getString("implementation_message_method") + " " + mEntity.getMethod() + "</center></html>");
		methodText.setForeground(Color.WHITE);
		methodText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		methodText.setHorizontalAlignment(SwingConstants.CENTER);
		identificationPanel.add(methodText);

		// Label uri
		final JLabel uriText = new JLabel("<html><center>" + mMessages.getString("implementation_message_uri") + " " + mEntity.getUri() + "</center></html>");
		uriText.setForeground(Color.WHITE);
		uriText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		uriText.setHorizontalAlignment(SwingConstants.CENTER);
		identificationPanel.add(uriText);


		// Panel hide
		final JBackgroundPanel hidePanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		hidePanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[fill, grow][fill, grow]", "[]"));
		hidePanel.setOpaque(false);
		hidePanel.setMaximumSize(new Dimension(Utils.reDimension(330), Integer.MAX_VALUE));
		middlePanel.add(hidePanel);

		// Label hide
		final JLabel hideText = new JLabel("<html><center>" + mMessages.getString("implementation_message_hide") + "</center></html>");
		hideText.setForeground(Color.WHITE);
		hideText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		hideText.setHorizontalAlignment(SwingConstants.CENTER);
		hidePanel.add(hideText);

		// Button hide
		final JButton hideButton = new JButton(mMessages.getString("implementation_button_hide"));
		hideButton.setMaximumSize(new Dimension(Utils.reDimension(120), Integer.MAX_VALUE));
		hideButton.setMinimumSize(new Dimension(Utils.reDimension(120), 0));
		hideButton.setOpaque(false);
		hidePanel.add(hideButton, "wrap");

		// Label hide note
		final JLabel hideNoteText = new JLabel("<html><center>" + mMessages.getString("implementation_message_hide_note") + "</center></html>");
		hideNoteText.setForeground(Color.WHITE);
		hideNoteText.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
		hideNoteText.setHorizontalAlignment(SwingConstants.CENTER);
		hidePanel.add(hideNoteText, "span 2");


		// Panel method name
		final JBackgroundPanel methodNamePanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		methodNamePanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[fill, grow][fill, grow]", "[]"));
		methodNamePanel.setOpaque(false);
		methodNamePanel.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));
		middlePanel.add(methodNamePanel);

		// Label method name
		final JLabel methodNameText = new JLabel("<html><center>" + mMessages.getString("implementation_message_method_name") + "</center></html>");
		methodNameText.setForeground(Color.WHITE);
		methodNameText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		methodNameText.setHorizontalAlignment(SwingConstants.CENTER);
		methodNamePanel.add(methodNameText);

		// TextField method name
		final JTextField methodNameTextField = new JTextField(mEntity.getMethodName());
		methodNameTextField.setMaximumSize(new Dimension(Utils.reDimension(170), Integer.MAX_VALUE));
		methodNameTextField.setMinimumSize(new Dimension(Utils.reDimension(170), 0));
		methodNameTextField.setText(mEntity.getMethodName());
		methodNamePanel.add(methodNameTextField, "wrap");

		// Label method name
		final JLabel methodNameExampleText = new JLabel("<html><center>" + mMessages.getString("implementation_message_method_name_example") + "</center></html>");
		methodNameExampleText.setForeground(Color.WHITE);
		methodNameExampleText.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
		methodNameExampleText.setHorizontalAlignment(SwingConstants.CENTER);
		methodNamePanel.add(methodNameExampleText, "span");

		methodNameTextField.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				char ch = e.getKeyChar();
				if(!Character.isAlphabetic(ch)) e.consume();
			}
		});


		// Panel method async
		final JBackgroundPanel methodAsyncPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		methodAsyncPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[fill, grow][fill, grow]", "[]"));
		methodAsyncPanel.setOpaque(false);
		methodAsyncPanel.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));
		middlePanel.add(methodAsyncPanel);

		// CheckBox method async
		final JCheckBox methodAsyncCheckBox = new JCheckBox("<html><center>" + mMessages.getString("implementation_message_checkbox_async") + "</center></html>");
		methodAsyncCheckBox.setForeground(Color.WHITE);
		methodAsyncCheckBox.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
		methodAsyncCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		methodAsyncCheckBox.setOpaque(false);
		methodAsyncCheckBox.setSelected(mEntity.isAsync());
		methodAsyncPanel.add(methodAsyncCheckBox, "wrap");


		if(mEntity.getParameters()!=null && mEntity.getParameters().size()>0)
		{
			// Panel parameters
			final JBackgroundPanel parametersPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
			parametersPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowy, fillx, filly", "[fill, grow]", "[grow][grow][grow]"));
			parametersPanel.setOpaque(false);
			parametersPanel.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));
			middlePanel.add(parametersPanel, "grow");

			// Label parameters header
			final JLabel parametersHeaderText = new JLabel("<html><center>" + mMessages.getString("implementation_message_parameters") + "</center></html>");
			parametersHeaderText.setForeground(Color.WHITE);
			parametersHeaderText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
			parametersHeaderText.setHorizontalAlignment(SwingConstants.CENTER);
			parametersPanel.add(parametersHeaderText);


			Object columnNames[] = {"Name", "Type", "Required"};

			DefaultTableModel model = new DefaultTableModel();
			model.setColumnIdentifiers(columnNames);

			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);

			JTable table = new JBTable(model);
			table.setForeground(Color.WHITE);
			table.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
			table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
			table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
			table.getColumnModel().getColumn(0).setPreferredWidth(Utils.reDimension(115));
			table.getColumnModel().getColumn(1).setPreferredWidth(Utils.reDimension(80));
			table.getColumnModel().getColumn(1).setMinWidth(Utils.reDimension(80));
			table.getColumnModel().getColumn(2).setMinWidth(Utils.reDimension(65));
			table.getColumnModel().getColumn(2).setMaxWidth(Utils.reDimension(65));
			table.setEnabled(false);
			parametersPanel.add(table.getTableHeader());
			parametersPanel.add(table);

			for(ParametersEntity entity : mEntity.getParameters())
			{
				Object[] o = new Object[3];
				o[0] = entity.getName();
				o[1] = Utils.firstLetterUpperCase(entity.getType());
				if(entity.isRequired()) o[2] = "Yes";
				else o[2] = "No";
				model.addRow(o);
			}
		}


		if(mEntity.getRequestHeaders()!=null)
		{
			// Panel request
			final JBackgroundPanel requestHeadersPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
			requestHeadersPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowy, fillx, filly", "[fill, grow]", "[grow][grow][grow]"));
			requestHeadersPanel.setOpaque(false);
			requestHeadersPanel.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));
			middlePanel.add(requestHeadersPanel, "grow");

			// Label request headers header
			final JLabel requestHeaderText = new JLabel("<html><center>" + mMessages.getString("implementation_message_request_headers") + "</center></html>");
			requestHeaderText.setForeground(Color.WHITE);
			requestHeaderText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
			requestHeaderText.setHorizontalAlignment(SwingConstants.CENTER);
			requestHeadersPanel.add(requestHeaderText);

			Object columnNames[] = {"Name", "Value"};

			DefaultTableModel model = new DefaultTableModel();
			model.setColumnIdentifiers(columnNames);

			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);

			JTable table = new JBTable(model);
			table.setForeground(Color.WHITE);
			table.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
			table.setEnabled(false);
			requestHeadersPanel.add(table.getTableHeader());
			requestHeadersPanel.add(table);

			for(HeadersEntity entity : mEntity.getRequestHeaders())
			{
				Object[] o = new Object[2];
				o[0] = entity.getName();
				o[1] = entity.getValue();
				model.addRow(o);
			}
		}


		if(!mEntity.getRequestBodyJson().equals(""))
		{
			// Panel request
			final JBackgroundPanel requestPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
			requestPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[center][center]", "[][center]"));
			requestPanel.setOpaque(false);
			requestPanel.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));
			middlePanel.add(requestPanel);

			// Label request header
			final JLabel requestHeaderText = new JLabel("<html><center>" + mMessages.getString("implementation_message_request") + "</center></html>");
			requestHeaderText.setForeground(Color.WHITE);
			requestHeaderText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
			requestHeaderText.setHorizontalAlignment(SwingConstants.CENTER);
			requestPanel.add(requestHeaderText, "span 2, wrap");

			String tmpText = mEntity.getRequestBodyJson();
			tmpText = tmpText.substring(0, tmpText.length() - 1);

			// TextArea response
			final JTextArea requestBodyText = new JTextArea(tmpText);
			requestBodyText.setForeground(Color.WHITE);
			requestBodyText.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
			requestBodyText.setOpaque(false);
			requestBodyText.setEditable(false);
			requestPanel.add(requestBodyText, "span 2, center, wrap");


			for(BodyObjectEntity entity : mEntity.getRequestBody())
			{
				// Label method name
				final JLabel rootEntityText = new JLabel();
				rootEntityText.setForeground(Color.WHITE);
				rootEntityText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
				rootEntityText.setHorizontalAlignment(SwingConstants.CENTER);
				rootEntityText.setText("<html><center><a style=\"color: red\">Name of</a> " + entity.getSerializableName() + " <a style=\"color: red\">entity:</a></center></html>");
				requestPanel.add(rootEntityText);

				// TextField method name
				final JTextField rootEntityTextField = new JTextField(entity.getEntityName());
				rootEntityTextField.setMaximumSize(new Dimension(Utils.reDimension(170), Integer.MAX_VALUE));
				rootEntityTextField.setMinimumSize(new Dimension(Utils.reDimension(170), 0));
				requestPanel.add(rootEntityTextField, "wrap");

				rootEntityTextField.addKeyListener(new KeyAdapter()
				{
					public void keyTyped(KeyEvent e)
					{
						char ch = e.getKeyChar();
						if(!Character.isAlphabetic(ch)) e.consume();
					}
				});

				mRequestTextFields.add(Pair.create(entity.getSerializableName(), rootEntityTextField));
			}
		}

		if(mEntity.getResponseHeaders()!=null)
		{
			// Panel response
			final JBackgroundPanel responseHeadersPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
			responseHeadersPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowy, fillx, filly", "[fill, grow]", "[grow][grow][grow]"));
			responseHeadersPanel.setOpaque(false);
			responseHeadersPanel.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));
			middlePanel.add(responseHeadersPanel, "grow");

			// Label response headers header
			final JLabel responseHeaderText = new JLabel("<html><center>" + mMessages.getString("implementation_message_response_headers") + "</center></html>");
			responseHeaderText.setForeground(Color.WHITE);
			responseHeaderText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
			responseHeaderText.setHorizontalAlignment(SwingConstants.CENTER);
			responseHeadersPanel.add(responseHeaderText);

			Object columnNames[] = {"Name", "Value"};

			DefaultTableModel model = new DefaultTableModel();
			model.setColumnIdentifiers(columnNames);

			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);

			JTable table = new JBTable(model);
			table.setForeground(Color.WHITE);
			table.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
			table.setEnabled(false);
			responseHeadersPanel.add(table.getTableHeader());
			responseHeadersPanel.add(table);

			for(HeadersEntity entity : mEntity.getResponseHeaders())
			{
				Object[] o = new Object[2];
				o[0] = entity.getName();
				o[1] = entity.getValue();
				model.addRow(o);
			}
		}

		if(!mEntity.getResponseBodyJson().equals(""))
		{
			// Panel response
			final JBackgroundPanel responsePanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
			responsePanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[center][center]", "[][center]"));
			responsePanel.setOpaque(false);
			responsePanel.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));
			middlePanel.add(responsePanel);

			// Label response header
			final JLabel responseHeaderText = new JLabel("<html><center>" + mMessages.getString("implementation_message_response") + "</center></html>");
			responseHeaderText.setForeground(Color.WHITE);
			responseHeaderText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
			responseHeaderText.setHorizontalAlignment(SwingConstants.CENTER);
			responsePanel.add(responseHeaderText, "span 2, wrap");

			String tmpText = mEntity.getResponseBodyJson();
			tmpText = tmpText.substring(0, tmpText.length() - 1);

			// TextArea response
			final JTextArea responseBodyText = new JTextArea(tmpText);
			responseBodyText.setForeground(Color.WHITE);
			responseBodyText.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
			responseBodyText.setOpaque(false);
			responseBodyText.setEditable(false);
			responsePanel.add(responseBodyText, "span 2, center, wrap");

			for(BodyObjectEntity entity : mEntity.getResponseBody())
			{
				// Label method name
				final JLabel rootEntityText = new JLabel();
				rootEntityText.setForeground(Color.WHITE);
				rootEntityText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
				rootEntityText.setHorizontalAlignment(SwingConstants.CENTER);
				rootEntityText.setText("<html><center><a style=\"color: red\">Name of</a> " + entity.getSerializableName() + " <a style=\"color: red\">entity:</a></center></html>");
				responsePanel.add(rootEntityText);

				// TextField method name
				final JTextField rootEntityTextField = new JTextField(entity.getEntityName());
				rootEntityTextField.setMaximumSize(new Dimension(Utils.reDimension(170), Integer.MAX_VALUE));
				rootEntityTextField.setMinimumSize(new Dimension(Utils.reDimension(170), 0));
				responsePanel.add(rootEntityTextField, "wrap");

				rootEntityTextField.addKeyListener(new KeyAdapter()
				{
					public void keyTyped(KeyEvent e)
					{
						char ch = e.getKeyChar();
						if(!Character.isAlphabetic(ch)) e.consume();
					}
				});

				mResponseTextFields.add(Pair.create(entity.getSerializableName(), rootEntityTextField));
			}
		}


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
						String errorText = "";

						// check method name
						if(methodNameTextField.getText().equals(""))
						{
							error = true;
							errorText = mMessages.getString("implementation_message_error_method");
						}

						// check all request entity names
						if(!error) for(Pair<String, JTextField> entity : mRequestTextFields)
						{
							if(entity.getSecond().getText().equals(""))
							{
								error = true;
								errorText = mMessages.getString("implementation_message_error_request");
							}
						}

						// check all response entity names
						if(!error) for(Pair<String, JTextField> entity : mResponseTextFields)
						{
							if(entity.getSecond().getText().equals(""))
							{
								error = true;
								errorText = mMessages.getString("implementation_message_error_response");
							}
						}


						if(error)
						{
							progress = false;
							JOptionPane.showMessageDialog(null, errorText, mMessages.getString("global_error_title"), JOptionPane.ERROR_MESSAGE);
							SwingUtilities.invokeLater(new Runnable()
							{
								public void run()
								{
									buttonNext.setImage("drawable/img_button_next.png");
								}
							});
						}
						else
						{
							mEntity.setMethodName(methodNameTextField.getText());
							mEntity.setAsync(methodAsyncCheckBox.isSelected());

							for(Pair<String, JTextField> entity : mRequestTextFields)
							{
								for(BodyObjectEntity bodyEntity : mEntity.getRequestBody())
									if(entity.getFirst().equals(bodyEntity.getSerializableName()))
									{
										bodyEntity.setEntityName(entity.getSecond().getText());
										break;
									}
							}

							for(Pair<String, JTextField> entity : mResponseTextFields)
							{
								for(BodyObjectEntity bodyEntity : mEntity.getResponseBody())
								{
									if(entity.getFirst().equals(bodyEntity.getSerializableName()))
									{
										bodyEntity.setEntityName(entity.getSecond().getText());
										break;
									}
								}
							}

							if(mEntity.getRequestBody()!=null) for(BodyObjectEntity ent : mEntity.getRequestBody())
							{
								for(BodyVariableEntity entVar : ent.getVariables())
									if(entVar.getType()==VariableEnum.STRING) entVar.setTypeName("String");
									else if(entVar.getType()==VariableEnum.INTEGER) entVar.setTypeName("Integer");
									else if(entVar.getType()==VariableEnum.DOUBLE) entVar.setTypeName("Double");
									else if(entVar.getType()==VariableEnum.BOOLEAN) entVar.setTypeName("Boolean");
									else if(entVar.getType()==VariableEnum.ENUM) entVar.setTypeName("ENUM");
									else if(entVar.getType()==VariableEnum.COLLECTION)
									{
										if(!entVar.getTypeName().equals("")) entVar.setTypeName(entVar.getTypeName());
										else
											entVar.setTypeName("List<" + Utils.findEntityNameInBodyObjectList(mEntity.getResponseBody(), entVar.getName()) + ">");
									}
									else if(entVar.getType()==VariableEnum.MAP)
										entVar.setTypeName(Utils.findEntityNameInBodyObjectList(mEntity.getRequestBody(), entVar.getName()));
									else if(entVar.getType()==VariableEnum.NONE) entVar.setTypeName("NONE");
							}

							if(mEntity.getResponseBody()!=null) for(BodyObjectEntity ent : mEntity.getResponseBody())
							{
								for(BodyVariableEntity entVar : ent.getVariables())
									if(entVar.getType()==VariableEnum.STRING) entVar.setTypeName("String");
									else if(entVar.getType()==VariableEnum.INTEGER) entVar.setTypeName("Integer");
									else if(entVar.getType()==VariableEnum.DOUBLE) entVar.setTypeName("Double");
									else if(entVar.getType()==VariableEnum.BOOLEAN) entVar.setTypeName("Boolean");
									else if(entVar.getType()==VariableEnum.ENUM) entVar.setTypeName("ENUM");
									else if(entVar.getType()==VariableEnum.COLLECTION)
									{
										if(!entVar.getTypeName().equals("")) entVar.setTypeName(entVar.getTypeName());
										else
											entVar.setTypeName("List<" + Utils.findEntityNameInBodyObjectList(mEntity.getResponseBody(), entVar.getName()) + ">");
									}
									else if(entVar.getType()==VariableEnum.MAP)
										entVar.setTypeName(Utils.findEntityNameInBodyObjectList(mEntity.getResponseBody(), entVar.getName()));
									else if(entVar.getType()==VariableEnum.NONE) entVar.setTypeName("NONE");
							}

							SwingUtilities.invokeLater(new Runnable()
							{
								public void run()
								{
									new ABMToolWindowImplementationSecond(mToolWindow, mEntity);
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

		hideButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane pane = new JOptionPane(mMessages.getString("implementation_dialog_message_add_hidden"));
				Object[] options = new String[]{mMessages.getString("global_yes"), mMessages.getString("global_no")};
				pane.setOptions(options);
				JDialog dialog = pane.createDialog(new JFrame(), mMessages.getString("implementation_dialog_message_add_hidden_header"));
				dialog.setVisible(true);
				Object obj = pane.getValue();
				int result = -1;
				for(int k = 0; k<options.length; k++)
					if(options[k].equals(obj)) result = k;
				if(result==0)
				{
					mEntity.setHidden(true);
					ConfigPreferences configPreferences = new ConfigPreferences();
					configPreferences.saveTreeNodeEntity(mEntity);
					new ABMToolWindowMain(mToolWindow);
				}
			}
		});


		// Scroll to top after layout init.
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				middleScrollPanel.getVerticalScrollBar().setValue(0);
			}
		});
	}
}
