/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.apiary.abm.utility.images;

import com.apiary.abm.utility.Utils;
import com.apiary.abm.utility.images.gif.AnimatedGifEncoder;
import com.apiary.abm.utility.images.gif.GifDecoder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class GraphicsUtilities
{
	public static BufferedImage loadCompatibleImage(URL resource) throws IOException
	{
		BufferedImage image = ImageIO.read(resource);
		return toCompatibleImage(image);
	}


	public static BufferedImage loadCompatibleImage(InputStream stream) throws IOException
	{
		BufferedImage image = ImageIO.read(stream);
		return toCompatibleImage(image);
	}


	public static BufferedImage createCompatibleImage(int width, int height)
	{
		return getGraphicsConfiguration().createCompatibleImage(width, height);
	}


	public static BufferedImage toCompatibleImage(BufferedImage image)
	{
		if(isHeadless())
		{
			return image;
		}
		if(image.getColorModel().equals(getGraphicsConfiguration().getColorModel()))
		{
			return image;
		}
		BufferedImage compatibleImage = getGraphicsConfiguration().createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());
		Graphics g = compatibleImage.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return compatibleImage;
	}


	public static BufferedImage createCompatibleImage(BufferedImage image, int width, int height)
	{
		return getGraphicsConfiguration().createCompatibleImage(width, height, image.getTransparency());
	}


	private static GraphicsConfiguration getGraphicsConfiguration()
	{
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		return environment.getDefaultScreenDevice().getDefaultConfiguration();
	}


	private static boolean isHeadless()
	{
		return GraphicsEnvironment.isHeadless();
	}


	public static BufferedImage createTranslucentCompatibleImage(int width, int height)
	{
		return getGraphicsConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
	}


	public static int[] getPixels(BufferedImage img, int x, int y, int w, int h, int[] pixels)
	{
		if(w == 0 || h == 0)
		{
			return new int[0];
		}
		if(pixels == null)
		{
			pixels = new int[w * h];
		}
		else if(pixels.length < w * h)
		{
			throw new IllegalArgumentException("Pixels array must have a length >= w * h");
		}
		int imageType = img.getType();
		if(imageType == BufferedImage.TYPE_INT_ARGB || imageType == BufferedImage.TYPE_INT_RGB)
		{
			Raster raster = img.getRaster();
			return (int[]) raster.getDataElements(x, y, w, h, pixels);
		}
		// Unmanages the image
		return img.getRGB(x, y, w, h, pixels, 0, w);
	}


	public static void convertGifFile(String source, File destination, int toWidth, int toHeight) throws FileNotFoundException, IOException
	{
		GifDecoder decoder = new GifDecoder();
		decoder.read(Utils.getResourceInputStream(source));

		//create encoder
		AnimatedGifEncoder encoder = new AnimatedGifEncoder();

		//set gif loop value
		encoder.setRepeat(0);

		//set gif quality
		encoder.setQuality(100);

		//set color for tranparence
		encoder.setTransparent(Color.BLACK);

		// create output stream for the encoder
		FileOutputStream fileOutputStream = new FileOutputStream(destination);
		encoder.start(fileOutputStream);

		//read frames with encoder and push scaled frames oin encoder
		for(int i = 0; i < decoder.getFrameCount(); i++)
		{
			BufferedImage bufferedImage = decoder.getFrame(i);

			//scale image at max dimension value MAX_IMAGE_DIMENSION both on height
			//and width, edit this part of code as you need
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			float scaleFactor = 1;
			if(width > toWidth || height > toHeight)
			{
				if(width > height)
				{
					scaleFactor = (float) toWidth / width;
				}
				else
				{
					scaleFactor = (float) toHeight / height;
				}
			}

			//final values for height and width
			width = (int) (width * scaleFactor);
			height = (int) (height * scaleFactor);
			if(scaleFactor != 1)
			{
				Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
				bufferedImage = toBufferedImage(image);
			}
			encoder.addFrame(bufferedImage);

			//change delay frame for those that have 0
			if(decoder.getDelay(i) == 0)
			{
				encoder.setDelay(100);
			}
			else
			{
				encoder.setDelay(decoder.getDelay(i));
			}
		}
		//stop and close
		encoder.finish();
		fileOutputStream.close();
	}


	public static BufferedImage toBufferedImage(Image image)
	{
		if(image instanceof BufferedImage)
		{
			return (BufferedImage) image;
		}

		// This code ensures that all the pixels in the image are loaded
		image = new ImageIcon(image).getImage();

		// Determine if the image has transparent pixels; for this method's
		// implementation, see e661 Determining If an Image Has Transparent Pixels
		boolean hasAlpha = hasAlpha(image);

		// Create a buffered image with a format that's compatible with the screen
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try
		{
			// Determine the type of transparency of the new buffered image
			int transparency = Transparency.OPAQUE;
			if(hasAlpha)
			{
				transparency = Transparency.TRANSLUCENT;
			}

			// Create the buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
		}
		catch(HeadlessException e)
		{
			// The system does not have a screen
		}

		if(bimage == null)
		{
			// Create a buffered image using the default color model
			int type = BufferedImage.TYPE_INT_RGB;
			if(hasAlpha)
			{
				type = BufferedImage.TYPE_INT_ARGB;
			}
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		}

		// Copy image to buffered image
		Graphics2D g = bimage.createGraphics();
		g.setRenderingHints(new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR));

		// Paint the image onto the buffered image
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}


	// This method returns true if the specified image has transparent pixels
	public static boolean hasAlpha(Image image)
	{
		// If buffered image, the color model is readily available
		if(image instanceof BufferedImage)
		{
			BufferedImage bimage = (BufferedImage) image;
			return bimage.getColorModel().hasAlpha();
		}

		// Use a pixel grabber to retrieve the image's color model;
		// grabbing a single pixel is usually sufficient
		PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
		try
		{
			pg.grabPixels();
		}
		catch(InterruptedException e)
		{
		}

		// Get the image's color model
		ColorModel cm = pg.getColorModel();
		return cm.hasAlpha();
	}
}