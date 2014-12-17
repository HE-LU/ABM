package com.apiary.abm.entity.blueprint;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class ResourcesEntity
{

	@SerializedName("actions")
	private ArrayList<ActionsEntity> mActions;

	@SerializedName("description")
	private String mDescription;

	@SerializedName("model")
	private ModelEntity mModel;

	@SerializedName("name")
	private String mName;

	@SerializedName("parameters")
	private List<ParametersEntity> mParameters;

	@SerializedName("uriTemplate")
	private String mUriTemplate;


	public ArrayList<ActionsEntity> getActions()
	{
		return this.mActions;
	}


	public String getDescription()
	{
		return this.mDescription;
	}


	public ModelEntity getModel()
	{
		return this.mModel;
	}


	public String getName()
	{
		return this.mName;
	}


	public List<ParametersEntity> getParameters()
	{
		return this.mParameters;
	}


	public String getUriTemplate()
	{
		return this.mUriTemplate;
	}
}
