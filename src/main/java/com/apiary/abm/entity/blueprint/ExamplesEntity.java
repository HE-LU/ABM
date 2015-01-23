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
	private List<RequestEntity> mRequests;

	@SerializedName("responses")
	private List<ResponsesEntity> mResponses;


	public String getDescription()
	{
		return this.mDescription;
	}


	public String getName()
	{
		return this.mName;
	}


	public List<RequestEntity> getRequests()
	{
		return this.mRequests;
	}


	public List<ResponsesEntity> getResponses()
	{
		return this.mResponses;
	}
}
