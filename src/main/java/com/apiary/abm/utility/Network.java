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
			Log.d("Response: " + request.getBody());
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
			Log.d("Response: " + request.getBody());
			return request.getBody();
		}
		catch(UnirestException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
