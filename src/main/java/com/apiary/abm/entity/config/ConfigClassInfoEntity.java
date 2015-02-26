package com.apiary.abm.entity.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ConfigClassInfoEntity
{
	String mUri;
	String mMethod;
	String mMethodName;
	Integer mHidden;
	Boolean mAsync;
	List<ConfigEntityNameEntity> mRequests = new ArrayList<ConfigEntityNameEntity>();
	List<ConfigEntityNameEntity> mResponses = new ArrayList<ConfigEntityNameEntity>();


	public String getUri()
	{
		return mUri;
	}


	@XmlElement(name = "URI")
	public void setUri(String mUri)
	{
		this.mUri = mUri;
	}


	public String getMethod()
	{
		return mMethod;
	}


	@XmlElement(name = "Method")
	public void setMethod(String mMethod)
	{
		this.mMethod = mMethod;
	}


	public String getMethodName()
	{
		return mMethodName;
	}


	@XmlElement(name = "MethodName")
	public void setMethodName(String mMethodName)
	{
		this.mMethodName = mMethodName;
	}


	public Boolean isAsync()
	{
		return mAsync;
	}


	@XmlElement(name = "Asynchronous")
	public void setAsync(Boolean mAsync)
	{
		this.mAsync = mAsync;
	}


	public Integer getHidden()
	{
		return mHidden;
	}


	@XmlElement(name = "Hidden")
	public void setHidden(Integer mHidden)
	{
		this.mHidden = mHidden;
	}


	public List<ConfigEntityNameEntity> getRequests()
	{
		return mRequests;
	}


	@XmlElementWrapper(name = "RequestsList")
	@XmlElement(name = "Request")
	public void setRequests(List<ConfigEntityNameEntity> mRequests)
	{
		this.mRequests = mRequests;
	}


	public List<ConfigEntityNameEntity> getResponses()
	{
		return mResponses;
	}


	@XmlElementWrapper(name = "ResponsesList")
	@XmlElement(name = "Response")
	public void setResponses(List<ConfigEntityNameEntity> mResponses)
	{
		this.mResponses = mResponses;
	}
}
