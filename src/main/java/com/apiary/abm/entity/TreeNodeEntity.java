package com.apiary.abm.entity;

public class TreeNodeEntity
{
	private NodeTypeEnum mNodeType;
	private String mName;
	private String mUri;
	private String mMethod;
	private String mValue;


	public TreeNodeEntity()
	{
	}


	public TreeNodeEntity(NodeTypeEnum mNodeType, String mName, String mValue)
	{
		this.mNodeType = mNodeType;
		this.mName = mName;
		this.mValue = mValue;
	}


	public NodeTypeEnum getNodeType()
	{
		return mNodeType;
	}


	public void setNodeType(NodeTypeEnum mNodeType)
	{
		this.mNodeType = mNodeType;
	}


	public String getName()
	{
		return mName;
	}


	public void setName(String mName)
	{
		this.mName = mName;
	}


	public String getValue()
	{
		return mValue;
	}


	public void setValue(String mValue)
	{
		this.mValue = mValue;
	}


	public String getUri()
	{
		return mUri;
	}


	public void setUri(String mUri)
	{
		this.mUri = mUri;
	}


	public String getMethod()
	{
		return mMethod;
	}


	public void setMethod(String mMethod)
	{
		this.mMethod = mMethod;
	}
}
