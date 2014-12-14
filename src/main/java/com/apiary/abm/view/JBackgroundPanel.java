package com.apiary.abm.view;

import com.apiary.abm.utility.images.NinePatch;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class JBackgroundPanel extends JPanel
{
	private JBackgroundPanelType mType;
	private BufferedImage mImg;


	public enum JBackgroundPanelType
	{
		BACKGROUND, BACKGROUND_REPEAT, PANEL, NINE_PATCH
	}


	public JBackgroundPanel(String name, JBackgroundPanelType type)
	{
		mType = type;

		try
		{
			mImg = ImageIO.read(JBackgroundPanel.class.getClassLoader().getResourceAsStream("drawable/" + name));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}


	@Override
	protected void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);

		switch(mType)
		{
			case BACKGROUND:
				graphics.drawImage(mImg, 0, 0, getWidth(), getHeight(), this);
				break;
			case BACKGROUND_REPEAT:
				for(int i = 0; i<getWidth(); i = i + mImg.getWidth())
				{
					for(int j = 0; j<getHeight(); j = j + mImg.getHeight())
					{
						graphics.drawImage(mImg, i, j, mImg.getWidth(), mImg.getHeight(), this);
					}
				}
				break;
			case PANEL:
				graphics.drawImage(mImg, 0, 0, getWidth(), getHeight(), this);
				break;
			case NINE_PATCH:
				NinePatch patch = NinePatch.load(mImg, true, false);
				Graphics2D graphics2D = (Graphics2D) graphics;
				patch.draw(graphics2D, 0, 0, getWidth(), getHeight());
				break;
		}
	}
}