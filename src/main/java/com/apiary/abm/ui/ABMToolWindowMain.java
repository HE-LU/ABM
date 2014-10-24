package com.apiary.abm.ui;

import com.apiary.abm.entity.ABMEntity;
import com.apiary.abm.utility.JBackgroundPanel;
import com.apiary.abm.utility.Utils;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.treeStructure.Tree;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;


public class ABMToolWindowMain extends JFrame
{
	private ToolWindow mToolWindow;


	public ABMToolWindowMain(ToolWindow toolWindow)
	{
		mToolWindow = toolWindow;
		mToolWindow.getContentManager().removeAllContents(true);
		ResourceBundle messages = ResourceBundle.getBundle("values/strings");

		// create UI
		JBackgroundPanel myToolWindowContent = new JBackgroundPanel(true);
		ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
		Content content = contentFactory.createContent(myToolWindowContent, "", false);
		mToolWindow.getContentManager().addContent(content);

		// MIGLAYOUT ( params, columns, rows)
		// insets TOP LEFT BOTTOM RIGHT
		myToolWindowContent.setLayout(new MigLayout("insets 0, flowy, fillx, filly", "[fill, grow, center]", "[fill,top][fill][fill,bottom]"));

		JBackgroundPanel topPanel = new JBackgroundPanel("box_top.png", false);
		JPanel middlePanel = new JPanel();
		JBackgroundPanel bottomPanel = new JBackgroundPanel("box_bottom.png", false);

		topPanel.setMinimumSize(new Dimension(0, 125));
		bottomPanel.setMinimumSize(new Dimension(0, 125));

		topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 125));
		bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 125));

		topPanel.setOpaque(false);
		middlePanel.setOpaque(false);
		bottomPanel.setOpaque(false);

		myToolWindowContent.add(topPanel);
		myToolWindowContent.add(middlePanel);
		myToolWindowContent.add(bottomPanel);

		// add elements
		topPanel.setLayout(new MigLayout("insets 10 0 0 0, flowy, fillx, filly", "[grow, center]", "[center, top]"));
		middlePanel.setLayout(new MigLayout("insets 0 10 0 10, flowy, fillx, filly", "[fill, grow]", "[fill][fill]"));
		bottomPanel.setLayout(new MigLayout("insets 25 50 0 50, flowy, fillx, filly", "[fill, grow]", "[fill]"));

		// informations
		JLabel infoText = new JLabel("<html><center>" + messages.getString("wellcome_information") + "</center></html>");
		infoText.setForeground(Color.WHITE);
		infoText.setFont(new Font("Serif", Font.BOLD, 18));
		infoText.setHorizontalAlignment(SwingConstants.CENTER);
		bottomPanel.add(infoText);

		// parse json from raw blueprint
		Utils.parseJsonFromBlueprint();

		// convert json string into object
		ABMEntity object = Utils.getJsonObject();

		// tree structure
		Tree tree = new Tree(initTreeStructure());
		tree.setOpaque(false);

		JBScrollPane pane = new JBScrollPane(tree);
		pane.setBorder(null);
		pane.setOpaque(false);

		pane.getViewport().setBorder(null);
		pane.getViewport().setOpaque(false);

		middlePanel.add(pane);
	}



	private DefaultMutableTreeNode initTreeStructure()
	{
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("The Java Series");

		DefaultMutableTreeNode category = null;
		DefaultMutableTreeNode book = null;

		category = new DefaultMutableTreeNode("Books for Java Programmers");
		top.add(category);

		//original Tutorial
		book = new DefaultMutableTreeNode("The Java Series 2");
		category.add(book);

		//Tutorial Continued
		book = new DefaultMutableTreeNode("The Java Series 3");
		category.add(book);

		//Swing Tutorial
		book = new DefaultMutableTreeNode("The Java Series 4");
		category.add(book);

		//...add more books for programmers...

		category = new DefaultMutableTreeNode("Books for Java Implementers");
		top.add(category);

		//VM
		book = new DefaultMutableTreeNode("The Java Series 22");
		category.add(book);

		//Language Spec
		book = new DefaultMutableTreeNode("The Java Series 33");
		category.add(book);

		return top;
	}
}
