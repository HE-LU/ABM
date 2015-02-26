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


	public boolean setClassInfoItem(ConfigClassInfoEntity mClassInfoItem, String oldUri, String oldMethod)
	{
		if(oldUri != null && oldMethod != null) for(ConfigClassInfoEntity item : this.mClassInfoList)
		{
			if(item.getUri().equals(oldUri) && item.getMethod().equals(oldMethod))
			{
				this.mClassInfoList.remove(item);
				this.mClassInfoList.add(mClassInfoItem);
				return true;
			}
		}
		else for(ConfigClassInfoEntity item : this.mClassInfoList)
		{
			if(item.getUri().equals(mClassInfoItem.getUri()) && item.getMethod().equals(mClassInfoItem.getMethod()))
			{
				this.mClassInfoList.remove(item);
				this.mClassInfoList.add(mClassInfoItem);
				return true;
			}
		}
		return false;
	}
}
