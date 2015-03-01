package com.apiary.abm.entity.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ConfigConfigurationEntity
{
	String mHost;
	String mModule;
	String mInterfaceClass;


	public ConfigConfigurationEntity()
	{
		mHost = "";
		mModule = "";
		mInterfaceClass = "APIRequest";
	}


	public String getHost()
	{
		return mHost;
	}


	@XmlElement(name = "Host")
	public void setHost(String mHost)
	{
		this.mHost = mHost;
	}


	public String getModule()
	{
		return mModule;
	}


	@XmlElement(name = "ProjectModule")
	public void setModule(String mApiModule)
	{
		this.mModule = mApiModule;
	}


	public String getInterfaceClass()
	{
		return mInterfaceClass;
	}


	@XmlElement(name = "InterfaceClassName")
	public void setInterfaceClass(String mInterfaceFile)
	{
		this.mInterfaceClass = mInterfaceFile;
	}
}
