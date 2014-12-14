package com.apiary.abm.utility;

import com.apiary.abm.entity.ABMEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;


public class Utils
{
	final public static int FONT_SMALL = 12;
	final public static int FONT_MEDIUM = 15;
	final public static int FONT_LARGE = 24;
	final public static int FONT_XLARGE = 30;


	public static String readFileAsString(String path, Charset encoding) throws IOException
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}


	public static String saveWebFileToTmp(String inputUrl) throws IOException
	{
		// get URL content
		URL url = new URL(inputUrl);
		URLConnection connection = url.openConnection();

		// open the stream and put it into BufferedReader
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		// save to tmp file
		File file = File.createTempFile("tmp_abm_web_file", ".tmp");

		// use FileWriter to write file
		FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

		String inputLine;
		while((inputLine = bufferedReader.readLine())!=null)
		{
			bufferedWriter.write(inputLine + "\n");
		}

		bufferedWriter.close();
		bufferedReader.close();

		return file.getAbsolutePath();
	}


	public static ABMEntity getJsonObject(String jsonBlueprint)
	{
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(jsonBlueprint, ABMEntity.class);
	}


	public static String parseJsonFromBlueprint(String blueprint)
	{
		//		try
		//		{
		//			ClientResource resource = new ClientResource("https://api.apiblueprint.org/parser");
		//
		//			Representation r = resource.post(blueprint);
		//			if(resource.getStatus().isSuccess())
		//			{
		//				if(resource.getStatus().getCode()==200)
		//				{
		//					return r.getText();
		//				}
		//			}
		//		}
		//		catch(IOException e)
		//		{
		//			e.printStackTrace();
		//		}
		return null;
	}


	// DIMENSION UTILS
	public static int fontSize(int size)
	{
		double screen = Toolkit.getDefaultToolkit().getScreenResolution();
		return (int) (size * (screen / 96f));
	}


	public static int reDimension(int dimension)
	{
		double screen = Toolkit.getDefaultToolkit().getScreenResolution();
		return (int) (dimension * (screen / 96f));
	}


	// RESOURCES UTILS
	public static File getResourceFile(String filePath)
	{
		return new File(Utils.class.getClassLoader().getResource(filePath).getFile());
	}


	public static InputStream getResourceInputStream(String filePath)
	{
		return Utils.class.getClassLoader().getResourceAsStream(filePath);
	}


	public static BufferedImage getResourceIBufferedImage(String filePath) throws IOException
	{
		if(Utils.class.getClassLoader().getResourceAsStream(filePath)!=null)
			return ImageIO.read(Utils.class.getClassLoader().getResourceAsStream(filePath));
		return null;
	}
}
