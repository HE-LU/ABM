package com.apiary.abm.entity;


public class ProblemEntity
{
	private String mName;
	private String mText;


	public ProblemEntity(String mName, String mText)
	{
		this.mName = mName;
		this.mText = mText;
	}


	public String getName()
	{
		return mName;
	}


	public void setName(String mName)
	{
		this.mName = mName;
	}


	public String getText()
	{
		return mText;
	}


	public void setText(String mText)
	{
		this.mText = mText;
	}
}