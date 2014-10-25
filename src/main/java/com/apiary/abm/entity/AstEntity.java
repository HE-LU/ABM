package com.apiary.abm.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class AstEntity
{
	@SerializedName("_version")
	private String mVersion;

	@SerializedName("description")
	private String mDescription;

	@SerializedName("metadata")
	private List mMetadata;

	@SerializedName("name")
	private String mName;

	@SerializedName("resourceGroups")
	private List mResourceGroups;


	public String getVersion()
	{
		return this.mVersion;
	}


	public String getDescription()
	{
		return this.mDescription;
	}


	public List getMetadata()
	{
		return this.mMetadata;
	}


	public String getName()
	{
		return this.mName;
	}


	public List getResourceGroups()
	{
		return this.mResourceGroups;
	}
}
