package com.apiary.abm.entity.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ConfigConfigurationEntity
{
	String mHost;
	String mModule;
	String mInterfaceClass;
	//	String mEntityPackage;
	String mManagerClass;


	public ConfigConfigurationEntity()
	{
		mHost = "";
		mModule = "";
		mInterfaceClass = "APIRequest";
		//		mEntityPackage = "com.example.app.api.entity";
		mManagerClass = "APIManager";
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


	//	public String getEntityPackage()
	//	{
	//		return mEntityPackage;
	//	}
	//
	//
	//	@XmlElement(name = "EntityPackage")
	//	public void setEntityPackage(String mEntityPackage)
	//	{
	//		this.mEntityPackage = mEntityPackage;
	//	}


	public String getManagerClass()
	{
		return mManagerClass;
	}


	@XmlElement(name = "ManagerClassName")
	public void setManagerClass(String mApiManagerFile)
	{
		this.mManagerClass = mApiManagerFile;
	}
}
