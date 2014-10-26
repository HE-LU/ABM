package com.apiary.abm.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ResourceGroupsEntity
{

	@SerializedName("description")
	private String mDescription;

	@SerializedName("name")
	private String mName;

	@SerializedName("resources")
	private ArrayList<ResourcesEntity> mResources;


	public String getDescription()
	{
		return this.mDescription;
	}


	public String getName()
	{
		return this.mName;
	}


	public ArrayList<ResourcesEntity> getResources()
	{
		return this.mResources;
	}
}
