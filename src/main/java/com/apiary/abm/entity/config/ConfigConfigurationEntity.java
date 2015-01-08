package com.apiary.abm.entity.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ConfigConfigurationEntity
{
	String mHost;
	String mApiPackage;
	String mInterfaceFile;
	String mEntityPackage;
	String mApiManagerFile;


	public String getHost()
	{
		return mHost;
	}


	@XmlElement
	public void setHost(String mHost)
	{
		this.mHost = mHost;
	}


	public String getApiPackage()
	{
		return mApiPackage;
	}


	@XmlElement
	public void setApiPackage(String mApiPackage)
	{
		this.mApiPackage = mApiPackage;
	}


	public String getInterfaceFile()
	{
		return mInterfaceFile;
	}


	@XmlElement
	public void setInterfaceFile(String mInterfaceFile)
	{
		this.mInterfaceFile = mInterfaceFile;
	}


	public String getEntityPackage()
	{
		return mEntityPackage;
	}


	@XmlElement
	public void setEntityPackage(String mEntityPackage)
	{
		this.mEntityPackage = mEntityPackage;
	}


	public String getApiManagerFile()
	{
		return mApiManagerFile;
	}


	@XmlElement
	public void setApiManagerFile(String mApiManagerFile)
	{
		this.mApiManagerFile = mApiManagerFile;
	}
}
