package com.apiary.abm.entity.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "ABMConfigRoot")
public class ConfigRootEntity
{
	String mName;
	ConfigConfigurationEntity mConfigurationEntity;
	List<ConfigClassInfoEntity> mClassInfoList = new ArrayList<ConfigClassInfoEntity>();


	public String getName()
	{
		return mName;
	}


	@XmlElement(name = "Name")
	public void setName(String mName)
	{
		this.mName = mName;
	}


	public List<ConfigClassInfoEntity> getClassInfoList()
	{
		return mClassInfoList;
	}


	@XmlElement(name = "Configuration")
	public void setConfigurationEntity(ConfigConfigurationEntity mConfigurationEntity)
	{
		this.mConfigurationEntity = mConfigurationEntity;
	}


	public ConfigConfigurationEntity getConfigurationEntity()
	{
		return mConfigurationEntity;
	}


	@XmlElementWrapper(name = "ClassInfoList")
	@XmlElement(name = "ClassInfo")
	public void setClassInfoList(List<ConfigClassInfoEntity> mClassInfoList)
	{
		this.mClassInfoList = mClassInfoList;
	}


	public void addClassInfoItem(ConfigClassInfoEntity mClassInfoItem)
	{
		this.mClassInfoList.add(mClassInfoItem);
	}
}
