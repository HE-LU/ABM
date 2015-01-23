package com.apiary.abm.entity.blueprint;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class RequestEntity
{
	@SerializedName("body")
	private String mBody;

	@SerializedName("description")
	private String mDescription;

	@SerializedName("headers")
	private List<HeadersEntity> mHeaders;

	@SerializedName("name")
	private String mName;

	@SerializedName("schema")
	private String mSchema;


	public String getBody()
	{
		return this.mBody;
	}


	public String getDescription()
	{
		return this.mDescription;
	}


	public List<HeadersEntity> getHeaders()
	{
		return this.mHeaders;
	}


	public String getName()
	{
		return this.mName;
	}


	public String getSchema()
	{
		return this.mSchema;
	}
}
