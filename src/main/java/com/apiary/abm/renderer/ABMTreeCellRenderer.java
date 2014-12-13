package com.apiary.abm.renderer;

import com.apiary.abm.entity.TreeNodeEntity;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;


public class ABMTreeCellRenderer extends JLabel implements TreeCellRenderer
{
	public ABMTreeCellRenderer()
	{
	}


	@SuppressWarnings("unchecked")
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		// Find out which node we are rendering and get its text
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		TreeNodeEntity entity = (TreeNodeEntity) node.getUserObject();

		// Format cell and add custom text to the cell
		setFont(new Font("Arial", Font.BOLD, 14));
		setForeground(Color.WHITE);

		switch(entity.getNodeType())
		{
			case ERROR_ROOT:
				setFont(new Font("Arial", Font.BOLD, 18));
				setForeground(Color.RED);
				setText(entity.getName() + " (" + entity.getValue() + ")");
				break;
			case WARNING_ROOT:
				setFont(new Font("Arial", Font.BOLD, 18));
				setForeground(Color.ORANGE);
				setText(entity.getName() + " (" + entity.getValue() + ")");
				break;
			case MISSING_ROOT:
				setFont(new Font("Arial", Font.BOLD, 18));
				setForeground(Color.GREEN);
				setText(entity.getName() + " (" + entity.getValue() + ")");
				break;
			case ERROR:
				setForeground(new Color(255, 200, 200));
				setText("File: " + entity.getName() + ".java, Method: " + entity.getMethod() + ", URI: " + entity.getUri());
				break;
			case WARNING:
				setForeground(new Color(255, 245, 200));
				setText("File: " + entity.getName() + ".java, Method: " + entity.getMethod() + ", URI: " + entity.getUri());
				break;
			case MISSING:
				setForeground(new Color(200, 255, 200));
				setText("File: " + entity.getName() + ".java, Method: " + entity.getMethod() + ", URI: " + entity.getUri());
				break;
		}

		return this;
	}


	// This is a hack to paint the background.  Normally a JLabel can
	// paint its own background, but due to an apparent bug or
	// limitation in the TreeCellRenderer, the paint method is
	// required to handle this.
	public void paint(Graphics g)
	{
		//		Color bColor;

		// Set the correct background color
		//		bColor = mSelected ? SystemColor.textHighlight : Color.white;
		//		g.setColor(bColor);

		// Draw a rectangle in the background of the cell
		//		g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);

		super.paint(g);
	}

}