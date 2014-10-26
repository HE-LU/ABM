package com.apiary.abm.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ActionsEntity
{
	@SerializedName("description")
	private String mDescription;

	@SerializedName("examples")
	private List<ExamplesEntity> mExamples;

	@SerializedName("method")
	private String mMethod;

	@SerializedName("name")
	private String mName;

	@SerializedName("parameters")
	private List<ParametersEntity> mParameters;


	public String getDescription()
	{
		return this.mDescription;
	}


	public List<ExamplesEntity> getExamples()
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


	public List<ParametersEntity> getParameters()
	{
		return this.mParameters;
	}
}
