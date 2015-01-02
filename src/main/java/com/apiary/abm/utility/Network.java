package com.apiary.abm.utility;


import com.apiary.abm.entity.DocResponseEntity;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.nio.charset.Charset;


public class Network
{
	public static String requestBlueprintFromApiary(String location, String key)
	{
		try
		{
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
		try
		{
			HttpResponse<String> request = Unirest.get(url).asString();
			if(request.getStatus()!=200) return null;
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
		try
		{
			String url = "https://api.apiblueprint.org/parser";
			HttpResponse<String> request = Unirest.post(url).body(blueprint).asString();
			return request.getBody();
		}
		catch(UnirestException e)
		{
			e.printStackTrace();
			return null;
		}
	}


	public static boolean isBlueprintValid(String blueprint)
	{
		try
		{
			String url = "https://api.apiblueprint.org/parser";
			HttpResponse<String> request = Unirest.post(url).body(blueprint).asString();
			return Utils.parseJsonBlueprint(request.getBody()).getError()==null;
		}
		catch(UnirestException e)
		{
			e.printStackTrace();
			return false;
		}
	}


	public static String refreshBlueprint()
	{
		Preferences preferences = new Preferences();
		String tmpFilePath = "";
		switch(preferences.getBlueprintConnectionType())
		{
			case CONNECTION_TYPE_DOC:
				try
				{
					String output = Network.requestBlueprintFromApiary(preferences.getBlueprintConnectionPath(), preferences.getBlueprintConnectionDocKey());
					DocResponseEntity response = Utils.parseJsonDoc(output);

					if(response==null) throw new Exception("Gson parsing problem!");

					if(response.getError() || response.getCode()==null) throw new Exception(response.getMessage());

					if(!Network.isBlueprintValid(response.getCode())) throw new Exception("Error in parsing blueprint!");

					tmpFilePath = Utils.saveStringToTmpFile("blueprint", response.getCode());
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

					if(output==null) throw new Exception("Could not get valid web file!");

					if(!Network.isBlueprintValid(output)) throw new Exception("Error in parsing blueprint!");

					tmpFilePath = Utils.saveStringToTmpFile("blueprint", output);
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

					if(output==null) throw new Exception("Could not read the file!");

					if(!Network.isBlueprintValid(output)) throw new Exception("Error in parsing blueprint!");

					tmpFilePath = Utils.saveStringToTmpFile("blueprint", output);
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
}
