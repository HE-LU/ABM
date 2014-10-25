package com.apiary.abm.ui;

import com.apiary.abm.entity.ABMEntity;
import com.apiary.abm.entity.NodeTypeEnum;
import com.apiary.abm.entity.TreeNodeEntity;
import com.apiary.abm.renderer.ABMTreeCellRenderer;
import com.apiary.abm.utility.JBackgroundPanel;
import com.apiary.abm.utility.Utils;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.treeStructure.Tree;

import net.miginfocom.swing.MigLayout;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;


public class ABMToolWindowMain extends JFrame
{
	public ABMToolWindowMain(ToolWindow toolWindow)
	{
		toolWindow.getContentManager().removeAllContents(true);

		// create UI
		JBackgroundPanel myToolWindowContent = new JBackgroundPanel("img_background.png", JBackgroundPanel.JBackgroundPanelType.BACKGROUND_REPEAT);
		ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
		Content content = contentFactory.createContent(myToolWindowContent, "", false);
		toolWindow.getContentManager().addContent(content);

		// MIGLAYOUT ( params, columns, rows)
		// insets TOP LEFT BOTTOM RIGHT
		myToolWindowContent.setLayout(new MigLayout("insets 0, flowy, fillx, filly", "[fill, grow, center]", "[fill,top][fill][fill,bottom]"));

		JBackgroundPanel topPanel = new JBackgroundPanel("img_box_top.png", JBackgroundPanel.JBackgroundPanelType.PANEL);
		//		JBackgroundPanel middlePanel = new JBackgroundPanel("panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		JPanel middlePanel = new JPanel();
		JBackgroundPanel bottomPanel = new JBackgroundPanel("img_box_bottom.png", JBackgroundPanel.JBackgroundPanelType.PANEL);

		topPanel.setMinimumSize(new Dimension(0, 130));
		middlePanel.setMinimumSize(new Dimension(0, 100));
		bottomPanel.setMinimumSize(new Dimension(0, 130));

		topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
		middlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

		// add elements
		topPanel.setLayout(new MigLayout("insets 5 0 0 0, flowy, fillx, filly", "[grow, center]", "[center, top]"));
		middlePanel.setLayout(new MigLayout("insets 0 20 0 20, flowy, fillx, filly", "[fill, grow]", "[fill]"));
		bottomPanel.setLayout(new MigLayout("insets 30 0 0 0, flowy, fillx, filly", "[grow, center]", "[center, top]"));

		topPanel.setOpaque(false);
		middlePanel.setOpaque(false);
		bottomPanel.setOpaque(false);

		myToolWindowContent.add(topPanel);
		myToolWindowContent.add(middlePanel);
		myToolWindowContent.add(bottomPanel);

		// information icon
		try
		{
			final JLabel informationIcon = new JLabel();
			BufferedImage tmpImage = ImageIO.read(JBackgroundPanel.class.getClassLoader().getResourceAsStream("drawable/img_cross.png"));

			Image image = tmpImage.getScaledInstance(95, 95, Image.SCALE_SMOOTH);
			informationIcon.setIcon(new ImageIcon(image));
			informationIcon.setOpaque(false);
			topPanel.add(informationIcon);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		// parse json from raw blueprint
		Utils.parseJsonFromBlueprint();

		// convert json string into object
		ABMEntity object = Utils.getJsonObject();

		// tree structure
		final JBackgroundPanel middleTreePanel = new JBackgroundPanel("img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		middleTreePanel.setLayout(new MigLayout("insets 12 12 18 19, flowy, fillx, filly", "[fill, grow]", "[fill]"));
		middleTreePanel.setOpaque(false);


		Tree tree = new Tree(initExampleTreeStructure(object));
		tree.setRootVisible(false);
		tree.setOpaque(false);
		tree.setCellRenderer(new ABMTreeCellRenderer());

		JBScrollPane pane = new JBScrollPane(tree);
		pane.setBorder(null);
		pane.setOpaque(false);

		pane.getViewport().setBorder(null);
		pane.getViewport().setOpaque(false);

		middleTreePanel.add(pane);

		middlePanel.add(middleTreePanel);

		// Button refresh
		final JLabel buttonRefresh = new JLabel();
		buttonRefresh.setOpaque(false);
		buttonRefresh.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_reload.png") + "' width='90' height='90' /></html>");

		buttonRefresh.addMouseListener(new MouseAdapter()
		{
			private boolean reloading;


			public void mouseClicked(MouseEvent e)
			{
				if(reloading) return;

				buttonRefresh.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/animation_reload.gif") + "' width='90' height='90' /></html>");
				reloading = true;

				Thread t = new Thread(new Runnable()
				{
					public void run()
					{
						// parse json from raw blueprint
						Utils.parseJsonFromBlueprint();

						// convert json string into object
						ABMEntity object = Utils.getJsonObject();

						Tree tree = new Tree(initExampleTreeStructure(object));
						tree.setRootVisible(false);
						tree.setOpaque(false);
						tree.setCellRenderer(new ABMTreeCellRenderer());

						final JBScrollPane pane = new JBScrollPane(tree);
						pane.setBorder(null);
						pane.setOpaque(false);

						pane.getViewport().setBorder(null);
						pane.getViewport().setOpaque(false);

						reloading = false;

						SwingUtilities.invokeLater(new Runnable()
						{
							public void run()
							{
								middleTreePanel.removeAll();
								middleTreePanel.add(pane);

								buttonRefresh.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_reload.png") + "' width='90' height='90' /></html>");
							}
						});
					}
				});
				t.start();
			}


			public void mousePressed(MouseEvent e)
			{
				if(reloading) return;

				buttonRefresh.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_reload_pressed.png") + "' width='90' height='90' /></html>");
			}


			public void mouseReleased(MouseEvent e)
			{
				if(reloading) return;

				buttonRefresh.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_reload.png") + "' width='90' height='90' /></html>");
			}

		});
		bottomPanel.add(buttonRefresh);
	}


	private TreeNodeEntity createTreeNodeItem(NodeTypeEnum type, String name, String value)
	{
		return new TreeNodeEntity(type, name, value);
	}


	private DefaultMutableTreeNode initTreeStructure(ABMEntity object)
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.ROOT, "Root object", ""));

		DefaultMutableTreeNode category = null;
		DefaultMutableTreeNode item = null;

		category = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.ERROR_ROOT, "Errors", "3"));
		root.add(category);

		category = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.WARNING_ROOT, "Warnings", "2"));
		root.add(category);

		category = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.MISSING_ROOT, "Not implemented", "2"));
		root.add(category);


		return root;
	}


	private DefaultMutableTreeNode initExampleTreeStructure(ABMEntity object)
	{
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.ROOT, "Root", "1"));

		DefaultMutableTreeNode category = null;
		DefaultMutableTreeNode book = null;

		category = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.ERROR_ROOT, "Errors", "3"));
		top.add(category);

		//original Tutorial
		book = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.ERROR, "File: test.java", "Missing argument test"));
		category.add(book);

		//Tutorial Continued
		book = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.ERROR, "File: date.java", "Missing argument day"));
		category.add(book);

		//Swing Tutorial
		book = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.ERROR, "File: date.java", "Missing argument month"));
		category.add(book);

		//...add more books for programmers...

		category = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.WARNING_ROOT, "Warnings", "2"));
		top.add(category);

		//VM
		book = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.WARNING, "File: test.java", "Bad argument type String"));
		category.add(book);

		//Language Spec
		book = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.WARNING, "File: date.java", "Bad argument type Date"));
		category.add(book);

		category = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.MISSING_ROOT, "Not implemented", "2"));
		top.add(category);

		//VM
		book = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.MISSING, "File: test.java", "Missing argument test2"));
		category.add(book);

		//Language Spec
		book = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.MISSING, "File: date.java", "Missing argument year"));
		category.add(book);

		return top;
	}
}
