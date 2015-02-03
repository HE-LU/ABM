package com.apiary.abm.entity;

import com.apiary.abm.entity.blueprint.HeadersEntity;
import com.apiary.abm.entity.blueprint.ParametersEntity;
import com.apiary.abm.enums.TreeNodeTypeEnum;

import java.util.List;


public class TreeNodeEntity
{
	private TreeNodeTypeEnum mTreeNodeType;
	private String mText; // some text
	private Integer mValue; // like number for tree (5)
	private boolean mHidden;
	private boolean mAsync;

	private String mUri; // /ping
	private String mMethod; // GET, POST, DELETE ...

	private String mMethodName; // getPing
	private String mResponseCode; // 200
	private List<ParametersEntity> mParameters;

	private List<HeadersEntity> mRequestHeaders;
	private List<HeadersEntity> mResponseHeaders;

	private String mRequestBodyJson;
	private String mResponseBodyJson;

	private List<BodyObjectEntity> mRequestBody;
	private List<BodyObjectEntity> mResponseBody;


	public TreeNodeEntity()
	{
		this.mValue = 0;
		this.mMethodName = "";
	}


	public TreeNodeEntity(TreeNodeTypeEnum mNodeType, String mText)
	{
		this.mTreeNodeType = mNodeType;
		this.mText = mText;
		this.mValue = 0;
	}


	public TreeNodeEntity(TreeNodeEntity entity)
	{
		this.mTreeNodeType = entity.getTreeNodeType();
		this.mText = entity.getText();
		this.mValue = entity.getValue();
		this.mHidden = entity.isHidden();
		this.mAsync = entity.isAsync();

		this.mUri = entity.getUri();
		this.mMethod = entity.getMethod();

		this.mMethodName = entity.getMethodName();
		this.mResponseCode = entity.getResponseCode();
		this.mParameters = entity.getParameters();
		this.mRequestHeaders = entity.getRequestHeaders();
		this.mResponseHeaders = entity.getResponseHeaders();

		this.mRequestBodyJson = entity.getRequestBodyJson();
		this.mResponseBodyJson = entity.getResponseBodyJson();
		this.mRequestBody = entity.getRequestBody();
		this.mResponseBody = entity.getResponseBody();
	}


	public TreeNodeTypeEnum getTreeNodeType()
	{
		return mTreeNodeType;
	}


	public void setTreeNodeType(TreeNodeTypeEnum mTreeNodeType)
	{
		this.mTreeNodeType = mTreeNodeType;
	}


	public String getText()
	{
		return mText;
	}


	public void setText(String mText)
	{
		this.mText = mText;
	}


	public Integer getValue()
	{
		return mValue;
	}


	public void setValue(Integer mValue)
	{
		this.mValue = mValue;
	}


	public boolean isHidden()
	{
		return mHidden;
	}


	public void setHidden(boolean mHidden)
	{
		this.mHidden = mHidden;
	}


	public boolean isAsync()
	{
		return mAsync;
	}


	public void setAsync(boolean mAsync)
	{
		this.mAsync = mAsync;
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


	public String getMethodName()
	{
		return mMethodName;
	}


	public void setMethodName(String mMethodName)
	{
		this.mMethodName = mMethodName;
	}


	public String getResponseCode()
	{
		return mResponseCode;
	}


	public void setResponseCode(String mResponseCode)
	{
		this.mResponseCode = mResponseCode;
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


	public List<HeadersEntity> getResponseHeaders()
	{
		return mResponseHeaders;
	}


	public void setResponseHeaders(List<HeadersEntity> mResponseHeaders)
	{
		this.mResponseHeaders = mResponseHeaders;
	}


	public String getRequestBodyJson()
	{
		return mRequestBodyJson;
	}


	public void setRequestBodyJson(String mRequestBodyJson)
	{
		this.mRequestBodyJson = mRequestBodyJson;
	}


	public String getResponseBodyJson()
	{
		return mResponseBodyJson;
	}


	public void setResponseBodyJson(String mResponseBodyJson)
	{
		this.mResponseBodyJson = mResponseBodyJson;
	}


	public List<BodyObjectEntity> getRequestBody()
	{
		return mRequestBody;
	}


	public void setRequestBody(List<BodyObjectEntity> mRequestBody)
	{
		this.mRequestBody = mRequestBody;
	}


	public List<BodyObjectEntity> getResponseBody()
	{
		return mResponseBody;
	}


	public void setResponseBody(List<BodyObjectEntity> mResponseBody)
	{
		this.mResponseBody = mResponseBody;
	}
}
