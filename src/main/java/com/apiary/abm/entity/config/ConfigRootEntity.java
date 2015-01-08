package com.apiary.abm.entity.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ConfigRootEntity
{
	String mName;
	ConfigConfigurationEntity mConfigurationEntity;
	List<ConfigClassInfoEntity> mClassInfoList = new ArrayList<ConfigClassInfoEntity>();


	public String getName()
	{
		return mName;
	}


	@XmlElement
	public void setName(String mName)
	{
		this.mName = mName;
	}


	public List<ConfigClassInfoEntity> getClassInfoList()
	{
		return mClassInfoList;
	}


	@XmlElement
	public void setConfigurationEntity(ConfigConfigurationEntity mConfigurationEntity)
	{
		this.mConfigurationEntity = mConfigurationEntity;
	}


	public ConfigConfigurationEntity getConfigurationEntity()
	{
		return mConfigurationEntity;
	}


	@XmlElement
	public void setClassInfoList(List<ConfigClassInfoEntity> mClassInfoList)
	{
		this.mClassInfoList = mClassInfoList;
	}


	public void addClassInfoItem(ConfigClassInfoEntity mClassInfoItem)
	{
		this.mClassInfoList.add(mClassInfoItem);
	}
}
