package com.apiary.abm.entity.blueprint;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ExamplesEntity
{
	@SerializedName("description")
	private String mDescription;

	@SerializedName("name")
	private String mName;

	@SerializedName("requests")
	private List mRequests;

	@SerializedName("responses")
	private List mResponses;


	public String getDescription()
	{
		return this.mDescription;
	}


	public String getName()
	{
		return this.mName;
	}


	public List getRequests()
	{
		return this.mRequests;
	}


	public List getResponses()
	{
		return this.mResponses;
	}
}
