package com.apiary.abm.entity.blueprint;

import com.google.gson.annotations.SerializedName;


public class MetadataEntity
{

	@SerializedName("name")
	private String mName;

	@SerializedName("value")
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
