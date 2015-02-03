package com.apiary.abm.entity.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ConfigEntityNameEntity
{
	String mSerializableName;
	String mEntityName;


	public ConfigEntityNameEntity()
	{
	}


	public ConfigEntityNameEntity(String mSerializableName, String mEntityName)
	{
		this.mSerializableName = mSerializableName;
		this.mEntityName = mEntityName;
	}


	public String getSerializableName()
	{
		return mSerializableName;
	}


	@XmlElement(name = "SerializableName")
	public void setSerializableName(String mSerializableName)
	{
		this.mSerializableName = mSerializableName;
	}


	public String getEntityName()
	{
		return mEntityName;
	}


	@XmlElement(name = "UserEntityName")
	public void setEntityName(String mEntityName)
	{
		this.mEntityName = mEntityName;
	}
}
