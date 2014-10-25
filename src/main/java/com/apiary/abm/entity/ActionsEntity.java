package com.apiary.abm.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ActionsEntity
{
	@SerializedName("description")
	private String mDescription;

	@SerializedName("examples")
	private List mExamples;

	@SerializedName("method")
	private String mMethod;

	@SerializedName("name")
	private String mName;

	@SerializedName("parameters")
	private List mParameters;


	public String getDescription()
	{
		return this.mDescription;
	}


	public List getExamples()
	{
		return this.mExamples;
	}


	public String getMethod()
	{
		return this.mMethod;
	}


	public String getName()
	{
		return this.mName;
	}


	public List getParameters()
	{
		return this.mParameters;
	}
}
