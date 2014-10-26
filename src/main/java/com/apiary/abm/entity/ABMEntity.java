package com.apiary.abm.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ABMEntity
{
	@SerializedName("_version")
	private String mVersion;

	@SerializedName("ast")
	private AstEntity mAst;

	@SerializedName("error")
	private String mError;

	@SerializedName("warnings")
	private ArrayList<WarningsEntity> mWarnings;


	public String getVersion()
	{
		return this.mVersion;
	}


	public AstEntity getAst()
	{
		return this.mAst;
	}


	public String getError()
	{
		return this.mError;
	}


	public ArrayList<WarningsEntity> getWarnings()
	{
		return this.mWarnings;
	}

}
