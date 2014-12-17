package com.apiary.abm.entity.blueprint;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ParametersEntity
{
	@SerializedName("name")
	private String mName;

	@SerializedName("description")
	private String mDescription;

	@SerializedName("type")
	private String mType;

	@SerializedName("required")
	private boolean mRequired;

	@SerializedName("default")
	private String mDefault;

	@SerializedName("example")
	private String mExample;

	@SerializedName("values")
	private List mValues;


	public String getName()
	{
		return mName;
	}


	public String getDescription()
	{
		return mDescription;
	}


	public String getType()
	{
		return mType;
	}


	public boolean isRequired()
	{
		return mRequired;
	}


	public String getDefault()
	{
		return mDefault;
	}


	public String getExample()
	{
		return mExample;
	}


	public List getValues()
	{
		return mValues;
	}
}
