package com.apiary.abm.view;

import com.apiary.abm.utility.Utils;
import com.apiary.abm.utility.images.GraphicsUtilities;

import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class ImageButton extends JButton
{
	private String mImage;
	private boolean mGif = false;


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
					ImageIcon icon = resizeImage(getWidth(), getHeight());
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
		ImageIcon icon = resizeImage(width, height);
		setIcon(icon);
	}


	public void setImage(String image)
	{
		try
		{
			mImage = image;
			mGif = Files.probeContentType(Paths.get(mImage)).equals("image/gif");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}


	private ImageIcon resizeImage(int width, int height)
	{
		try
		{
			if(mGif) return resizeGifImage(width, height);

			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
			Graphics2D g2d = bi.createGraphics();
			g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
			g2d.drawImage(Utils.getResourceIBufferedImage(mImage), 0, 0, width, height, null);
			g2d.dispose();
			return new ImageIcon(bi);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	public ImageIcon resizeGifImage(int width, int height)
	{
		try
		{
			File temp = File.createTempFile("temp_file", "tmp_abm_image.tmp");
			GraphicsUtilities.convertGifFile(mImage, temp, width, height);
			return new ImageIcon(temp.getAbsolutePath());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}