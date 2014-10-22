package com.apiary.abm.utility;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class JBackgroundPanel extends JPanel
{
	private boolean mRepeat;
	private BufferedImage mImg;


	public JBackgroundPanel(boolean repeat)
	{
		mRepeat = repeat;

		// load the background image
		try
		{
			InputStream tmp = JBackgroundPanel.class.getClassLoader().getResourceAsStream("drawable/pattern-1.png");
			mImg = ImageIO.read(tmp);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}


	public JBackgroundPanel(String name, boolean repeat)
	{
		mRepeat = repeat;

		// load the background image
		try
		{
			InputStream tmp = JBackgroundPanel.class.getClassLoader().getResourceAsStream("drawable/" + name);
			mImg = ImageIO.read(tmp);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}


	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		// paint the background image and repeat image
		if(mRepeat)
		{
			for(int i = 0; i<getWidth(); i = i + mImg.getWidth())
			{
				for(int j = 0; j<getHeight(); j = j + mImg.getHeight())
				{
					g.drawImage(mImg, i, j, mImg.getWidth(), mImg.getHeight(), this);
				}
			}
		}
		// paint the background image and stretch image
		else
		{
			g.drawImage(mImg, 0, 0, getWidth(), getHeight(), this);
		}
	}
}