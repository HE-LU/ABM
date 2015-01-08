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


public class ABMToolWindowConfiguration extends JFrame
{
	private ToolWindow mToolWindow;


	public ABMToolWindowConfiguration(ToolWindow toolWindow)
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
		middlePanel.setLayout(new MigLayout("insets 0 " + Utils.reDimension(15) + " 0 " + Utils.reDimension(15) + ", flowy, fillx, filly", "[fill, grow]", "[]" + Utils.reDimension(20) + "[]" + Utils.reDimension(20) + "[]" + Utils.reDimension(20) + "[]" + Utils.reDimension(20) + "[]"));
		bottomPanel.setLayout(new MigLayout("insets " + Utils.reDimension(18) + " 0 0 0, flowx, fillx, filly", "[grow, center]", "[center, top]"));

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
		final JLabel infoText = new JLabel("<html><center>" + messages.getString("configuration_header") + "</center></html>");
		infoText.setForeground(Color.WHITE);
		infoText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_LARGE)));
		infoText.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(infoText);


		// Panel host
		final JBackgroundPanel hostPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		hostPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[][fill,grow][]", "[]"));
		hostPanel.setOpaque(false);
		middlePanel.add(hostPanel);

		// Label host
		final JLabel hostLabel = new JLabel("<html><center>" + messages.getString("configuration_message_host") + "</center></html>");
		hostLabel.setForeground(Color.WHITE);
		hostLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		hostLabel.setHorizontalAlignment(SwingConstants.CENTER);
		hostPanel.add(hostLabel);

		// TextField host
		final JTextField hostTextField = new JTextField();
		hostTextField.setMinimumSize(new Dimension(Utils.reDimension(170), 0));
		hostPanel.add(hostTextField, "wrap");

		// Label host example
		final JLabel hostExampleLabel = new JLabel("<html><center>" + messages.getString("configuration_message_host_example") + "</center></html>");
		hostExampleLabel.setForeground(Color.WHITE);
		hostExampleLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
		hostExampleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		hostPanel.add(hostExampleLabel, "span, grow");


		// Panel Package
		final JBackgroundPanel packagePanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		packagePanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[][fill,grow][]", "[]"));
		packagePanel.setOpaque(false);
		middlePanel.add(packagePanel);

		// Label package
		final JLabel packageLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_package") + "</center></html>");
		packageLabel.setForeground(Color.WHITE);
		packageLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		packageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		packagePanel.add(packageLabel);

		// TextField package
		final JTextField packageTextField = new JTextField();
		packageTextField.setMinimumSize(new Dimension(Utils.reDimension(170), 0));
		packagePanel.add(packageTextField, "wrap");

		// Label package note
		final JLabel packageNoteLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_package_note") + "</center></html>");
		packageNoteLabel.setForeground(Color.WHITE);
		packageNoteLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
		packageNoteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		packagePanel.add(packageNoteLabel, "span, grow");


		// Panel API Entity package
		final JBackgroundPanel apiEntityPackagePanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		apiEntityPackagePanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[][fill,grow][]", "[]"));
		apiEntityPackagePanel.setOpaque(false);
		middlePanel.add(apiEntityPackagePanel);

		// Label API Entity package
		final JLabel apiEntityPackageLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_entity_package") + "</center></html>");
		apiEntityPackageLabel.setForeground(Color.WHITE);
		apiEntityPackageLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		apiEntityPackageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		apiEntityPackagePanel.add(apiEntityPackageLabel);

		// TextField API Entity package
		final JTextField apiEntityPackageTextField = new JTextField();
		apiEntityPackageTextField.setMinimumSize(new Dimension(Utils.reDimension(170), 0));
		apiEntityPackagePanel.add(apiEntityPackageTextField, "wrap");

		// Label API Entity package note
		final JLabel apiEntityPackageNoteLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_entity_package_note") + "</center></html>");
		apiEntityPackageNoteLabel.setForeground(Color.WHITE);
		apiEntityPackageNoteLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
		apiEntityPackageNoteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		apiEntityPackagePanel.add(apiEntityPackageNoteLabel, "span, grow");


		// Panel Interface file
		final JBackgroundPanel apiInterfaceFilePanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		apiInterfaceFilePanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[][fill,grow][]", "[]"));
		apiInterfaceFilePanel.setOpaque(false);
		middlePanel.add(apiInterfaceFilePanel);

		// Label Interface file
		final JLabel apiInterfaceFileLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_interface") + "</center></html>");
		apiInterfaceFileLabel.setForeground(Color.WHITE);
		apiInterfaceFileLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		apiInterfaceFileLabel.setHorizontalAlignment(SwingConstants.CENTER);
		apiInterfaceFilePanel.add(apiInterfaceFileLabel);

		// TextField Interface file
		final JTextField apiInterfaceFileTextField = new JTextField();
		apiInterfaceFileTextField.setMinimumSize(new Dimension(Utils.reDimension(170), 0));
		apiInterfaceFilePanel.add(apiInterfaceFileTextField, "wrap");

		// Label Interface file note
		final JLabel apiInterfaceFileNoteLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_interface_note") + "</center></html>");
		apiInterfaceFileNoteLabel.setForeground(Color.WHITE);
		apiInterfaceFileNoteLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
		apiInterfaceFileNoteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		apiInterfaceFilePanel.add(apiInterfaceFileNoteLabel, "span, grow");


		// Panel Manager file
		final JBackgroundPanel apiManagerFilePanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		apiManagerFilePanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[][fill,grow][]", "[]"));
		apiManagerFilePanel.setOpaque(false);
		middlePanel.add(apiManagerFilePanel);

		// Label Manager file
		final JLabel apiManagerFileLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_manager") + "</center></html>");
		apiManagerFileLabel.setForeground(Color.WHITE);
		apiManagerFileLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		apiManagerFileLabel.setHorizontalAlignment(SwingConstants.CENTER);
		apiManagerFilePanel.add(apiManagerFileLabel);

		// TextField Manager file
		final JTextField apiManagerFileTextField = new JTextField();
		apiManagerFileTextField.setMinimumSize(new Dimension(Utils.reDimension(170), 0));
		apiManagerFilePanel.add(apiManagerFileTextField, "wrap");

		// Label Manager file note
		final JLabel apiManagerFileNoteLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_manager_note") + "</center></html>");
		apiManagerFileNoteLabel.setForeground(Color.WHITE);
		apiManagerFileNoteLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
		apiManagerFileNoteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		apiManagerFilePanel.add(apiManagerFileNoteLabel, "span, grow");


		// Save button
		final ImageButton buttonSave = new ImageButton();
		buttonSave.setImage("drawable/img_button_save.png");
		buttonSave.setSize(Utils.reDimension(70), Utils.reDimension(70));

		buttonSave.addMouseListener(new MouseAdapter()
		{
			private boolean progress;


			public void mouseClicked(MouseEvent e)
			{
				if(progress) return;
				buttonSave.setImage("drawable/img_button_save_pressed.png");
				buttonSave.setSize(Utils.reDimension(70), Utils.reDimension(70));
				progress = true;

				Thread t = new Thread(new Runnable()
				{
					public void run()
					{
						// TODO: SAVE
						SwingUtilities.invokeLater(new Runnable()
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
				buttonSave.setImage("drawable/img_button_save_pressed.png");
				buttonSave.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}


			public void mouseReleased(MouseEvent e)
			{
				if(progress) return;
				buttonSave.setImage("drawable/img_button_save.png");
				buttonSave.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}
		});
		bottomPanel.add(buttonSave);
	}
}
