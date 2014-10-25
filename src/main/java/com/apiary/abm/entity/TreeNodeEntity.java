package com.apiary.abm.entity;

public class TreeNodeEntity
{
	private NodeTypeEnum mNodeType;
	private String mName;
	private String mValue;


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
}
