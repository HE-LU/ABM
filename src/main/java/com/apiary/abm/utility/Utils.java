package com.apiary.abm.utility;

import com.apiary.abm.entity.DocResponseEntity;
import com.apiary.abm.entity.blueprint.ABMEntity;
import com.apiary.abm.ui.ABMToolWindow;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.intellij.openapi.project.Project;

import org.apache.commons.io.FileUtils;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;

import javax.imageio.ImageIO;


public class Utils
{
	final public static int FONT_SMALL = 12;
	final public static int FONT_MEDIUM = 15;
	final public static int FONT_LARGE = 24;
	final public static int FONT_XLARGE = 30;


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


	public static BufferedImage getResourceBufferedImage(String filePath) throws IOException
	{
		if(Utils.class.getClassLoader().getResourceAsStream(filePath)!=null)
			return ImageIO.read(Utils.class.getClassLoader().getResourceAsStream(filePath));
		return null;
	}


	// FILES
	public static String saveStringToTmpFile(String fileName, String input) throws IOException
	{
		File file = File.createTempFile(fileName, ".tmp");
		FileUtils.writeStringToFile(file, input);
		return file.getAbsolutePath();
	}


	public static String saveStringToFile(String path, String input) throws IOException
	{
		File file = new File(path);
		FileUtils.writeStringToFile(file, input);
		return file.getAbsolutePath();
	}


	public static String saveStringToFile(File file, String input) throws IOException
	{
		FileUtils.writeStringToFile(file, input);
		return file.getAbsolutePath();
	}


	public static String readFileAsString(String path, Charset encoding) throws IOException
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}


	public static String readFileAsString(File file, Charset encoding) throws IOException
	{
		byte[] encoded = Files.readAllBytes(file.toPath());
		return new String(encoded, encoding);
	}


	// JSON
	public static DocResponseEntity parseJsonDoc(String jsonDoc)
	{
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(jsonDoc, DocResponseEntity.class);
	}


	public static ABMEntity parseJsonBlueprint(String jsonBlueprint)
	{
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(jsonBlueprint, ABMEntity.class);
	}


	// GRADLE
	public static boolean isGradleWithRetrofit()
	{
		Project myProject = ABMToolWindow.getProject();
		File projectDirectory = new File(myProject.getBaseDir().getPath());

		String fileName = "build.gradle";
		try
		{
			Collection filesCollection = FileUtils.listFiles(projectDirectory, null, true);

			for(Object fileObject : filesCollection)
			{
				File file = (File) fileObject;
				if(file.getName().equals(fileName))
				{
					String gradleString = Utils.readFileAsString(file, Charset.forName("UTF-8"));

					if(gradleString.contains("com.squareup.retrofit:retrofit")) return true;
					//					else
					//					{
					//						int index = gradleString.indexOf("dependencies");
					//						index = gradleString.indexOf("{", index);
					//						index++;
					//						gradleString = gradleString.substring(0, index) + "\n\tcompile 'com.squareup.retrofit:retrofit:1.8.0'" + gradleString.substring(index, gradleString.length());
					//
					//						Utils.saveStringToFile(file, gradleString);
					//					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
