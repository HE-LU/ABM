package com.apiary.abm.view;

import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class ImageButton extends JButton
{
	private BufferedImage mImage;


	public ImageButton()
	{
		setMargin(new Insets(0, 0, 0, 0));
		setIconTextGap(0);
		setBorderPainted(false);
		setBorder(null);
		setText(null);
		setOpaque(false);

		addComponentListener(new ComponentListener()
		{
			@Override
			public void componentShown(ComponentEvent e)
			{
			}


			@Override
			public void componentResized(ComponentEvent e)
			{
				if(getWidth()!=0 && getHeight()!=0 && mImage!=null)
				{
					ImageIcon icon = new ImageIcon(resize(mImage, getWidth(), getHeight()));
					setIcon(icon);
				}
			}


			@Override
			public void componentMoved(ComponentEvent e)
			{
			}


			@Override
			public void componentHidden(ComponentEvent e)
			{
			}
		});
	}


	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		ImageIcon icon = new ImageIcon(resize(mImage, width, height));
		setIcon(icon);
	}


	public void setImage(BufferedImage image)
	{
		mImage = image;
		ImageIcon icon = new ImageIcon(mImage);
		setIcon(icon);
	}


	public static BufferedImage resize(BufferedImage image, int width, int height)
	{
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
		Graphics2D g2d = bi.createGraphics();
		g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		g2d.drawImage(image, 0, 0, width, height, null);
		g2d.dispose();
		return bi;
	}
}