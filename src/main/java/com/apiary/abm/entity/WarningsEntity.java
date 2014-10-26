package com.apiary.abm.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class WarningsEntity
{
	@SerializedName("code")
	private Number mCode;

	@SerializedName("location")
	private List mLocation;

	@SerializedName("message")
	private String mMessage;


	public Number getCode()
	{
		return this.mCode;
	}


	public List getLocation()
	{
		return this.mLocation;
	}


	public String getMessage()
	{
		return this.mMessage;
	}
}
