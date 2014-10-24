package com.apiary.abm.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Utils
{
	public static String getStringFromFile(String path, Charset encoding) throws IOException
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}


	public static String getWebFile(String inputUrl) throws IOException
	{
		// get URL content
		URL url = new URL(inputUrl);
		URLConnection connection = url.openConnection();

		// open the stream and put it into BufferedReader
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		// save to tmp file
		File file = File.createTempFile("tmp_input", ".tmp");

		// use FileWriter to write file
		FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

		String inputLine;
		while((inputLine = bufferedReader.readLine())!=null)
		{
			bufferedWriter.write(inputLine);
		}

		bufferedWriter.close();
		bufferedReader.close();

		return file.getAbsolutePath();
	}
}
