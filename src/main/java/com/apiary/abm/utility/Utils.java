package com.apiary.abm.utility;

import com.apiary.abm.ABMConfig;
import com.apiary.abm.entity.BodyObjectEntity;
import com.apiary.abm.entity.DocResponseEntity;
import com.apiary.abm.entity.blueprint.ABMEntity;
import com.apiary.abm.ui.ABMToolWindow;
import com.brsanthu.googleanalytics.AppViewHit;
import com.brsanthu.googleanalytics.GoogleAnalytics;
import com.brsanthu.googleanalytics.GoogleAnalyticsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.intellij.openapi.project.Project;

import org.apache.commons.io.FileUtils;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


public class Utils
{
	final public static int FONT_SMALL = 12;
	final public static int FONT_MEDIUM = 15;
	final public static int FONT_MEDIUM_LARGE = 20;
	final public static int FONT_LARGE = 24;
	final public static int FONT_XLARGE = 30;

	private static GoogleAnalytics sGoogleAnalytics = null;


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
		if(Utils.class.getClassLoader().getResourceAsStream(filePath) != null)
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


	public static String firstLetterUpperCase(String string)
	{
		if(string == null || string.length() == 0) return string;
		if(string.length() == 1) return string.toUpperCase();
		return string.substring(0, 1).toUpperCase() + string.substring(1, string.length());
	}


	public static String cleanUpString(String string)
	{
		return string.replaceAll("[^a-zA-Z]", "");
	}


	public static String findEntityNameInBodyObjectList(List<BodyObjectEntity> list, String serializableName)
	{
		for(BodyObjectEntity ent : list)
			if(ent.getSerializableName().equals(serializableName)) return ent.getEntityName();
		return null;
	}


	public static void initAnalytics()
	{
		if(sGoogleAnalytics == null) sGoogleAnalytics = new GoogleAnalytics(ABMConfig.GA_ID, ABMConfig.GA_APP_NAME, ABMConfig.VERSION);
	}


	public static void trackPage(String page)
	{
		if(sGoogleAnalytics == null || ABMConfig.DEBUG) return;

		GoogleAnalyticsResponse result = sGoogleAnalytics.post(new AppViewHit(ABMConfig.GA_ID, ABMConfig.VERSION, page));
		Log.d("GA Tracking page: " + page + " Response: " + result.getStatusCode() + " Data: " + result.getPostedParms());
	}


	public static JEditorPane generateMessage(String message)
	{
		final JEditorPane editorPane = new JEditorPane("text/html", "<html><body style=\"font-family: Ariel; font-weight: bold; color: white; font-size:" + Utils.fontSize(Utils.FONT_SMALL) + "pt; \"><center>" + message + "</center></body></html>");
		editorPane.setOpaque(false);
		editorPane.setHighlighter(null);
		editorPane.setEditable(false);
		editorPane.addHyperlinkListener(new HyperlinkListener()
		{
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e)
			{
				if(e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) try
				{
					Desktop.getDesktop().browse(e.getURL().toURI());
				}
				catch(IOException e1)
				{
					e1.printStackTrace();
				}
				catch(URISyntaxException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		return editorPane;
	}
}
