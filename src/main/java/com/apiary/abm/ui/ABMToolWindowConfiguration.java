package com.apiary.abm.ui;

import com.apiary.abm.entity.blueprint.MetadataEntity;
import com.apiary.abm.entity.config.ConfigConfigurationEntity;
import com.apiary.abm.utility.ConfigPreferences;
import com.apiary.abm.utility.Preferences;
import com.apiary.abm.utility.ProjectManager;
import com.apiary.abm.utility.Utils;
import com.apiary.abm.view.ImageButton;
import com.apiary.abm.view.JBackgroundPanel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.psi.PsiClass;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class ABMToolWindowConfiguration extends JFrame
{
	private ToolWindow mToolWindow;
	private ConfigPreferences mConfigPreferences = new ConfigPreferences();
	private ConfigConfigurationEntity mConfigurationEntity = mConfigPreferences.getConfigurationEntity();
	private List<String> mModuleList = new ArrayList<String>();
	private ProjectManager mProjectManager = new ProjectManager();


	public ABMToolWindowConfiguration(ToolWindow toolWindow)
	{
		mToolWindow = toolWindow;
		mToolWindow.getContentManager().removeAllContents(true);

		checkValues();
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
		hostTextField.setMinimumSize(new Dimension(Utils.reDimension(160), 0));
		hostTextField.setText(mConfigurationEntity.getHost());
		hostPanel.add(hostTextField, "wrap");

		// Label host example
		final JLabel hostExampleLabel = new JLabel("<html><center>" + messages.getString("configuration_message_host_example") + "</center></html>");
		hostExampleLabel.setForeground(Color.WHITE);
		hostExampleLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
		hostExampleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		hostPanel.add(hostExampleLabel, "span, grow");


		// Panel module
		final JBackgroundPanel modulePanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		modulePanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[][fill,grow]", "[]"));
		modulePanel.setOpaque(false);
		middlePanel.add(modulePanel);

		// Label module
		final JLabel moduleLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_module") + "</center></html>");
		moduleLabel.setForeground(Color.WHITE);
		moduleLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		moduleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		modulePanel.add(moduleLabel);

		// ComboBox module
		mModuleList.add(0, "");
		final JComboBox moduleComboBox = new ComboBox(mModuleList.toArray());
		moduleComboBox.setMinimumSize(new Dimension(Utils.reDimension(160), 0));
		moduleComboBox.setSelectedItem(mConfigurationEntity.getModule());
		modulePanel.add(moduleComboBox);


		// Panel API Entity package
		final JBackgroundPanel entityPackagePanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		entityPackagePanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[][fill,grow][][]", "[]"));
		entityPackagePanel.setOpaque(false);
		middlePanel.add(entityPackagePanel);

		// Label API Entity package
		final JLabel entityPackageLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_entity_package_red") + "</center></html>");
		entityPackageLabel.setForeground(Color.WHITE);
		entityPackageLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		entityPackageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		entityPackagePanel.add(entityPackageLabel);

		// TextField API Entity package
		final JTextField entityPackageTextField = new JTextField();
		entityPackageTextField.setMinimumSize(new Dimension(Utils.reDimension(160), 0));
		entityPackageTextField.setText(mConfigurationEntity.getEntityPackage());
		entityPackagePanel.add(entityPackageTextField);

		// Button entity package
		final JButton entityPackageFileButton = new JButton("<html><center>" + messages.getString("configuration_button_check") + "</center></html>");
		entityPackageFileButton.setOpaque(false);
		entityPackageFileButton.setForeground(Color.WHITE);
		entityPackageFileButton.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
		entityPackageFileButton.setHorizontalAlignment(SwingConstants.CENTER);
		if(mProjectManager.getPackage(entityPackageTextField.getText())!=null)
			entityPackageLabel.setText("<html><center>" + messages.getString("configuration_message_api_entity_package_green") + "</center></html>");
		entityPackagePanel.add(entityPackageFileButton, "wrap");

		// Label API Entity package note
		final JLabel entityPackageNoteLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_entity_package_note") + "</center></html>");
		entityPackageNoteLabel.setForeground(Color.WHITE);
		entityPackageNoteLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
		entityPackageNoteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		entityPackagePanel.add(entityPackageNoteLabel, "span, grow");


		// Panel Interface file
		final JBackgroundPanel interfaceClassPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		interfaceClassPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[][fill,grow][][]", "[]"));
		interfaceClassPanel.setOpaque(false);
		middlePanel.add(interfaceClassPanel);

		// Label Interface file
		final JLabel interfaceClassLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_interface_red") + "</center></html>");
		interfaceClassLabel.setForeground(Color.WHITE);
		interfaceClassLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		interfaceClassLabel.setHorizontalAlignment(SwingConstants.CENTER);
		List<PsiClass> psiClassInterface = mProjectManager.getClasses(mConfigurationEntity.getModule(), mConfigurationEntity.getInterfaceClass());
		if(psiClassInterface!=null && psiClassInterface.size()>0)
			interfaceClassLabel.setText("<html><center>" + messages.getString("configuration_message_api_interface_green") + "</center></html>");
		interfaceClassPanel.add(interfaceClassLabel);

		// TextField Interface file
		final JTextField interfaceClassTextField = new JTextField();
		interfaceClassTextField.setMinimumSize(new Dimension(Utils.reDimension(160), 0));
		interfaceClassTextField.setText(mConfigurationEntity.getInterfaceClass());
		interfaceClassPanel.add(interfaceClassTextField);

		// Button Interface file
		final JButton interfaceClassButton = new JButton("<html><center>" + messages.getString("configuration_button_check") + "</center></html>");
		interfaceClassButton.setOpaque(false);
		interfaceClassButton.setForeground(Color.WHITE);
		interfaceClassButton.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
		interfaceClassButton.setHorizontalAlignment(SwingConstants.CENTER);
		interfaceClassPanel.add(interfaceClassButton, "wrap");

		// Label Interface file note
		final JLabel interfaceClassNoteLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_interface_note") + "</center></html>");
		interfaceClassNoteLabel.setForeground(Color.WHITE);
		interfaceClassNoteLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
		interfaceClassNoteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		interfaceClassPanel.add(interfaceClassNoteLabel, "span, grow");


		// Panel Manager file
		final JBackgroundPanel managerClassPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		managerClassPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[][fill,grow][][]", "[]"));
		managerClassPanel.setOpaque(false);
		middlePanel.add(managerClassPanel);

		// Label Manager file
		final JLabel managerClassLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_manager_red") + "</center></html>");
		managerClassLabel.setForeground(Color.WHITE);
		managerClassLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		managerClassLabel.setHorizontalAlignment(SwingConstants.CENTER);
		List<PsiClass> psiClassManager = mProjectManager.getClasses(mConfigurationEntity.getModule(), mConfigurationEntity.getManagerClass());
		if(psiClassManager!=null && psiClassManager.size()>0)
			managerClassLabel.setText("<html><center>" + messages.getString("configuration_message_api_manager_green") + "</center></html>");
		managerClassPanel.add(managerClassLabel);

		// TextField Manager file
		final JTextField managerClassTextField = new JTextField();
		managerClassTextField.setMinimumSize(new Dimension(Utils.reDimension(160), 0));
		managerClassTextField.setText(mConfigurationEntity.getManagerClass());
		managerClassPanel.add(managerClassTextField);

		// Button Manager file
		final JButton managerClassButton = new JButton("<html><center>" + messages.getString("configuration_button_check") + "</center></html>");
		managerClassButton.setOpaque(false);
		managerClassButton.setForeground(Color.WHITE);
		managerClassButton.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
		managerClassButton.setHorizontalAlignment(SwingConstants.CENTER);
		managerClassPanel.add(managerClassButton, "wrap");

		// Label Manager file note
		final JLabel managerClassNoteLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_manager_note") + "</center></html>");
		managerClassNoteLabel.setForeground(Color.WHITE);
		managerClassNoteLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
		managerClassNoteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		managerClassPanel.add(managerClassNoteLabel, "span, grow");


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
						mConfigurationEntity.setManagerClass(managerClassTextField.getText());
						mConfigurationEntity.setModule((String) moduleComboBox.getSelectedItem());
						mConfigurationEntity.setEntityPackage(entityPackageTextField.getText());
						mConfigurationEntity.setHost(hostTextField.getText());
						mConfigurationEntity.setInterfaceClass(interfaceClassTextField.getText());
						mConfigPreferences.saveConfigurationEntity(mConfigurationEntity);

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

		entityPackageFileButton.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(mProjectManager.getPackage(entityPackageTextField.getText())!=null)
					entityPackageLabel.setText("<html><center>" + messages.getString("configuration_message_api_entity_package_green") + "</center></html>");
				else
					entityPackageLabel.setText("<html><center>" + messages.getString("configuration_message_api_entity_package_red") + "</center></html>");
			}
		});

		interfaceClassButton.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				List<PsiClass> cl = mProjectManager.getClasses((String) moduleComboBox.getSelectedItem(), interfaceClassTextField.getText());

				if(cl!=null && cl.size()>0)
					interfaceClassLabel.setText("<html><center>" + messages.getString("configuration_message_api_interface_green") + "</center></html>");
				else
					interfaceClassLabel.setText("<html><center>" + messages.getString("configuration_message_api_interface_red") + "</center></html>");
			}
		});

		managerClassButton.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				List<PsiClass> cl = mProjectManager.getClasses((String) moduleComboBox.getSelectedItem(), managerClassTextField.getText());

				if(cl!=null && cl.size()>0)

					managerClassLabel.setText("<html><center>" + messages.getString("configuration_message_api_manager_green") + "</center></html>");

				else

					managerClassLabel.setText("<html><center>" + messages.getString("configuration_message_api_manager_red") + "</center></html>");

			}
		});
	}


	private void checkValues()
	{
		ProjectManager manager = new ProjectManager();

		if(mConfigurationEntity.getHost().equals(""))
		{
			try
			{
				Preferences prefs = new Preferences();
				String json = Utils.readFileAsString(prefs.getBlueprintJsonTmpFileLocation(), Charset.forName("UTF-8"));
				List<MetadataEntity> metadataList = Utils.parseJsonBlueprint(json).getAst().getMetadata();
				for(MetadataEntity metadata : metadataList)
				{
					if(metadata.getName().equals("HOST"))
					{
						mConfigurationEntity.setHost(metadata.getValue());
						break;
					}
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}

		for(Module module : manager.getAllModules())
		{
			mModuleList.add(module.getName());
		}
	}
}
