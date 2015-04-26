package com.apiary.abm.utility;


import com.apiary.abm.ABMConfig;
import com.apiary.abm.entity.DocResponseEntity;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;


public class Network
{
	public static String requestBlueprintFromApiary(String location, String key)
	{
		if(!isInternetReachable()) return null;

		try
		{
			Unirest.setHttpClient(makeClient());
			HttpResponse<String> request = Unirest.get("https://api.apiary.io/blueprint/get/" + location).header("authentication", "Token " + key).asString();
			return request.getBody();
		}
		catch(UnirestException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	public static String requestBlueprintFromURL(String url)
	{
		if(!isInternetReachable()) return null;

		try
		{
			HttpResponse<String> request = Unirest.get(url).asString();
			if(request.getStatus() != 200) return null;
			return request.getBody();
		}
		catch(UnirestException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	public static String requestJSONFromBlueprint(String blueprint)
	{
		if(!isInternetReachable()) return null;

		Preferences prefs = new Preferences();
		String json = "";

		try
		{
			String url = "http://api.apiblueprint.org/parser";
			Unirest.setHttpClient(makeClient());
			HttpResponse<String> request = Unirest.post(url).body(blueprint).asString();
			json = request.getBody();

			try
			{
				prefs.setBlueprintJsonTmpFileLocation(Utils.saveStringToTmpFile(ABMConfig.FILE_BLUEPRINT_JSON, json));
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		catch(UnirestException e)
		{
			e.printStackTrace();

			try
			{
				json = Utils.readFileAsString(prefs.getBlueprintJsonTmpFileLocation(), Charset.forName("UTF-8"));
			}
			catch(IOException e1)
			{
				e1.printStackTrace();
			}
		}

		Log.d("JSON DATA: " + json);
		return json;
	}


	public static Boolean isBlueprintValid(String blueprint)
	{
		if(!isInternetReachable()) return null;

		try
		{
			String url = "http://api.apiblueprint.org/parser";
			Unirest.setHttpClient(makeClient());
			HttpResponse<String> request = Unirest.post(url).body(blueprint).asString();
			Preferences prefs = new Preferences();
			prefs.setBlueprintJsonTmpFileLocation(Utils.saveStringToTmpFile(ABMConfig.FILE_BLUEPRINT_JSON, request.getBody()));
			return Utils.parseJsonBlueprint(request.getBody()).getError() == null;
		}
		catch(UnirestException e)
		{
			e.printStackTrace();
			return true;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}


	public static String refreshBlueprint()
	{
		if(!isInternetReachable()) return null;

		Preferences preferences = new Preferences();
		String tmpFilePath = preferences.getBlueprintTmpFileLocation();
		switch(preferences.getBlueprintConnectionType())
		{
			case CONNECTION_TYPE_DOC:
				try
				{
					String output = Network.requestBlueprintFromApiary(preferences.getBlueprintConnectionPath(), preferences.getBlueprintConnectionDocKey());
					Log.d("RequestBlueprintFromApiary: " + output);
					DocResponseEntity response = Utils.parseJsonDoc(output);

					if(response == null) throw new Exception("Gson parsing problem!");

					if(response.getError() || response.getCode() == null) throw new Exception(response.getMessage());

					if(!Network.isBlueprintValid(response.getCode())) throw new Exception("Error in parsing blueprint!");

					tmpFilePath = Utils.saveStringToTmpFile(ABMConfig.FILE_BLUEPRINT, response.getCode());
					preferences.setBlueprintTmpFileLocation(tmpFilePath);
				}
				catch(Exception e1)
				{
					Log.e("Exception message: " + e1.getMessage());
					e1.printStackTrace();
				}
				break;
			case CONNECTION_TYPE_WEB_URL:
				try
				{
					String output = Network.requestBlueprintFromURL(preferences.getBlueprintConnectionPath());

					if(output == null) throw new Exception("Could not get valid web file!");

					if(!Network.isBlueprintValid(output)) throw new Exception("Error in parsing blueprint!");

					tmpFilePath = Utils.saveStringToTmpFile(ABMConfig.FILE_BLUEPRINT, output);
					preferences.setBlueprintTmpFileLocation(tmpFilePath);
				}
				catch(Exception e1)
				{
					Log.e("Exception message: " + e1.getMessage());
					e1.printStackTrace();
				}
				break;
			case CONNECTION_TYPE_FILE:
				try
				{
					String output = Utils.readFileAsString(preferences.getBlueprintConnectionPath(), Charset.forName("UTF-8"));

					if(output == null) throw new Exception("Could not read the file!");

					if(!Network.isBlueprintValid(output)) throw new Exception("Error in parsing blueprint!");

					tmpFilePath = Utils.saveStringToTmpFile(ABMConfig.FILE_BLUEPRINT, output);
					preferences.setBlueprintTmpFileLocation(tmpFilePath);
				}
				catch(Exception e1)
				{
					Log.e("Exception message: " + e1.getMessage());
					e1.printStackTrace();
				}
				break;
			default:
				break;
		}

		return tmpFilePath;
	}


	public static CloseableHttpClient makeClient()
	{
		SSLContextBuilder builder = new SSLContextBuilder();
		CloseableHttpClient httpclient = null;
		try
		{
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(), SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		}
		catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch(KeyStoreException e)
		{
			e.printStackTrace();
		}
		catch(KeyManagementException e)
		{
			e.printStackTrace();
		}
		return httpclient;
	}


	public static boolean isInternetReachable()
	{
		try
		{
			URL url = new URL("http://www.google.com");

			HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
			urlConnect.getContent();
		}
		catch(UnknownHostException e)
		{
			return false;
		}
		catch(IOException e)
		{
			return false;
		}
		return true;
	}
}