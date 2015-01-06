package com.apiary.abm.entity;

import com.apiary.abm.entity.blueprint.HeadersEntity;
import com.apiary.abm.entity.blueprint.ParametersEntity;
import com.apiary.abm.enums.NodeTypeEnum;

import java.util.List;


public class TreeNodeEntity
{
	private NodeTypeEnum mNodeType;
	private String mName; // pingCall
	private String mDescription; // api call for requesting ping
	private String mUri; // /ping
	private String mMethod; // GET, POST, DELETE ...
	private String mValue; //
	private List<ParametersEntity> mParameters;
	private String mResponseCode;
	private List<HeadersEntity> mResponseHeaders;
	private String mResponseBody;


	public TreeNodeEntity()
	{
	}


	public TreeNodeEntity(NodeTypeEnum mNodeType, String mName)
	{
		this.mNodeType = mNodeType;
		this.mName = mName;
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


	public String getDescription()
	{
		return mDescription;
	}


	public void setDescription(String mDescription)
	{
		this.mDescription = mDescription;
	}


	public List<ParametersEntity> getParameters()
	{
		return mParameters;
	}


	public void setParameters(List<ParametersEntity> mParameters)
	{
		this.mParameters = mParameters;
	}


	public String getResponseCode()
	{
		return mResponseCode;
	}


	public void setResponseCode(String mResponseCode)
	{
		this.mResponseCode = mResponseCode;
	}


	public List<HeadersEntity> getResponseHeaders()
	{
		return mResponseHeaders;
	}


	public void setResponseHeaders(List<HeadersEntity> mResponseHeaders)
	{
		this.mResponseHeaders = mResponseHeaders;
	}


	public String getResponseBody()
	{
		return mResponseBody;
	}


	public void setResponseBody(String mResponseBody)
	{
		this.mResponseBody = mResponseBody;
	}
}
