package com.apiary.abm.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class AstEntity
{
	@SerializedName("_version")
	private String mVersion;

	@SerializedName("description")
	private String mDescription;

	@SerializedName("metadata")
	private ArrayList<MetadataEntity> mMetadata;

	@SerializedName("name")
	private String mName;

	@SerializedName("resourceGroups")
	private ArrayList<ResourceGroupsEntity> mResourceGroups;


	public String getVersion()
	{
		return this.mVersion;
	}


	public String getDescription()
	{
		return this.mDescription;
	}


	public ArrayList<MetadataEntity> getMetadata()
	{
		return this.mMetadata;
	}


	public String getName()
	{
		return this.mName;
	}


	public ArrayList<ResourceGroupsEntity> getResourceGroups()
	{
		return this.mResourceGroups;
	}
}
