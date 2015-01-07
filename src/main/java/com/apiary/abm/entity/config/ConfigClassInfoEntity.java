package com.apiary.abm.entity.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ConfigClassInfoEntity
{
	String mName;
	String mUri;
	String mMethod;


	public String getName()
	{
		return mName;
	}


	@XmlElement
	public void setName(String mName)
	{
		this.mName = mName;
	}


	public String getUri()
	{
		return mUri;
	}


	@XmlElement
	public void setUri(String mUri)
	{
		this.mUri = mUri;
	}


	public String getMethod()
	{
		return mMethod;
	}


	@XmlElement
	public void setMethod(String mMethod)
	{
		this.mMethod = mMethod;
	}

}
