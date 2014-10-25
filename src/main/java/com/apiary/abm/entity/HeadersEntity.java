package com.apiary.abm.entity;

import com.google.gson.annotations.SerializedName;


public class HeadersEntity
{

	@SerializedName("description")
	private String mName;

	@SerializedName("description")
	private String mValue;


	public String getName()
	{
		return this.mName;
	}


	public String getValue()
	{
		return this.mValue;
	}
}
