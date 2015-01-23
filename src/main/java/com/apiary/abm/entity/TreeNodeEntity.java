package com.apiary.abm.entity;

import com.apiary.abm.entity.blueprint.HeadersEntity;
import com.apiary.abm.entity.blueprint.ParametersEntity;
import com.apiary.abm.enums.NodeTypeEnum;
import com.apiary.abm.enums.TreeNodeTypeEnum;

import java.util.List;


public class TreeNodeEntity
{
	private TreeNodeTypeEnum mTreeNodeType;
	private NodeTypeEnum mNodeType;
	private String mName; // pingCall
	private String mDescription; // api call for requesting ping
	private String mUri; // /ping
	private String mMethod; // GET, POST, DELETE ...
	private Integer mValue; //
	private List<ParametersEntity> mParameters;
	private List<HeadersEntity> mRequestHeaders;
	private String mRequestBody;
	private String mResponseCode;
	private List<HeadersEntity> mResponseHeaders;
	private String mResponseBody;


	public TreeNodeEntity()
	{
		this.mValue = 0;
	}


	public TreeNodeEntity(TreeNodeTypeEnum mNodeType, String mName)
	{
		this.mTreeNodeType = mNodeType;
		this.mName = mName;
		this.mValue = 0;
	}


	public TreeNodeEntity(TreeNodeEntity entity)
	{
		this.mTreeNodeType = entity.getTreeNodeType();
		this.mNodeType = entity.getNodeType();
		this.mName = entity.getName();
		this.mDescription = entity.getDescription();
		this.mUri = entity.getUri();
		this.mMethod = entity.getMethod();
		this.mValue = entity.getValue();
		this.mParameters = entity.getParameters();
		this.mRequestHeaders = entity.getRequestHeaders();
		this.mRequestBody = entity.getRequestBody();
		this.mResponseCode = entity.getResponseCode();
		this.mResponseHeaders = entity.getResponseHeaders();
		this.mResponseBody = entity.getResponseBody();
	}


	public TreeNodeTypeEnum getTreeNodeType()
	{
		return mTreeNodeType;
	}


	public void setTreeNodeType(TreeNodeTypeEnum mNodeType)
	{
		this.mTreeNodeType = mNodeType;
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


	public Integer getValue()
	{
		return mValue;
	}


	public void setValue(Integer mValue)
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


	public List<HeadersEntity> getRequestHeaders()
	{
		return mRequestHeaders;
	}


	public void setRequestHeaders(List<HeadersEntity> mRequestHeaders)
	{
		this.mRequestHeaders = mRequestHeaders;
	}


	public String getRequestBody()
	{
		return mRequestBody;
	}


	public void setRequestBody(String mRequestBody)
	{
		this.mRequestBody = mRequestBody;
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
