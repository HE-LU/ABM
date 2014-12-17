package com.apiary.abm.entity;

import com.google.gson.annotations.SerializedName;


public class DocResponseEntity
{
	@SerializedName("error")
	private boolean mError;

	@SerializedName("message")
	private String mMessage;

	@SerializedName("code")
	private String mCode;


	public boolean getError()
	{
		return this.mError;
	}


	public String getMessage()
	{
		return this.mMessage;
	}


	public String getCode()
	{
		return this.mCode;
	}
}
