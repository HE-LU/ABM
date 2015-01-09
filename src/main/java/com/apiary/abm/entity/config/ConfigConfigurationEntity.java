package com.apiary.abm.entity.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ConfigConfigurationEntity
{
	String mHost;
	String mModule;
	String mInterfaceClass;
	String mEntityPackage;
	String mManagerClass;


	public ConfigConfigurationEntity()
	{
		mHost = "";
		mModule = "";
		mInterfaceClass = "APIRequest";
		mEntityPackage = "com.example.app.api.entity";
		mManagerClass = "APIManager";
	}


	public String getHost()
	{
		return mHost;
	}


	@XmlElement
	public void setHost(String mHost)
	{
		this.mHost = mHost;
	}


	public String getModule()
	{
		return mModule;
	}


	@XmlElement
	public void setModule(String mApiModule)
	{
		this.mModule = mApiModule;
	}


	public String getInterfaceClass()
	{
		return mInterfaceClass;
	}


	@XmlElement
	public void setInterfaceClass(String mInterfaceFile)
	{
		this.mInterfaceClass = mInterfaceFile;
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


	public String getManagerClass()
	{
		return mManagerClass;
	}


	@XmlElement
	public void setManagerClass(String mApiManagerFile)
	{
		this.mManagerClass = mApiManagerFile;
	}
}
