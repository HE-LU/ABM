package com.apiary.abm.utility;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


public class Network
{
	public static String requestBlueprintFromApiary(String location, String key)
	{
		try
		{
			Log.d("Request url: " + "https://api.apiary.io/blueprint/get/" + location);
			Log.d("Request token: " + "Token " + key);
			HttpResponse<String> request = Unirest.get("https://api.apiary.io/blueprint/get/" + location).header("authentication", "Token " + key).asString();
			Log.d("Response:");
			Log.d(request.getBody());
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
			Log.d("Request url: " + url);
			HttpResponse<String> request = Unirest.get(url).asString();
			Log.d("Response:");
			Log.d(request.getBody());
			if(request.getStatus()!=200) return null;
			return request.getBody();
		}
		catch(UnirestException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	public static boolean isBlueprintValid(String blueprint)
	{
		try
		{
			String url = "https://api.apiblueprint.org/parser";
			Log.d("Request url: " + url);
			HttpResponse<String> request = Unirest.post(url).body(blueprint).asString();
			Log.d("Response:");
			Log.d(request.getBody());
			return Utils.parseJsonBlueprint(request.getBody()).getError()==null;
		}
		catch(UnirestException e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
