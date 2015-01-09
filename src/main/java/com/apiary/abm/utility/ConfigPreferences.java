package com.apiary.abm.utility;


import com.apiary.abm.entity.TreeNodeEntity;
import com.apiary.abm.entity.config.ConfigClassInfoEntity;
import com.apiary.abm.entity.config.ConfigConfigurationEntity;
import com.apiary.abm.entity.config.ConfigRootEntity;
import com.apiary.abm.ui.ABMToolWindow;
import com.intellij.openapi.project.Project;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class ConfigPreferences
{
	private ConfigRootEntity mConfig;


	public ConfigPreferences()
	{
		try
		{
			if(!configExist())
			{
				mConfig = new ConfigRootEntity();
				saveConfig();
				if(!configExist())
				{
					mConfig = null;
					return;
				}
			}

			Project myProject = ABMToolWindow.getProject();
			File configFile = new File(myProject.getBaseDir().getPath() + "/abm_config.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(ConfigRootEntity.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			mConfig = (ConfigRootEntity) jaxbUnmarshaller.unmarshal(configFile);
		}
		catch(JAXBException e)
		{
			e.printStackTrace();
		}
	}


	private void saveConfig()
	{
		Project myProject = ABMToolWindow.getProject();
		File configFile = new File(myProject.getBaseDir().getPath() + "/abm_config.xml");

		try
		{
			JAXBContext jaxbContext = JAXBContext.newInstance(ConfigRootEntity.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(mConfig, configFile);
			jaxbMarshaller.marshal(mConfig, System.out);

		}
		catch(JAXBException e)
		{
			e.printStackTrace();
		}
	}


	public static boolean configExist()
	{
		return new File(ABMToolWindow.getProject().getBaseDir().getPath() + "/abm_config.xml").exists();
	}


	public void saveTreeNodeEntity(TreeNodeEntity entity)
	{
		ConfigClassInfoEntity classInfoEntity = new ConfigClassInfoEntity();
		classInfoEntity.setName(entity.getName());
		classInfoEntity.setMethod(entity.getMethod());
		classInfoEntity.setUri(entity.getUri());
		mConfig.addClassInfoItem(classInfoEntity);

		saveConfig();
	}


	public void saveConfigurationEntity(ConfigConfigurationEntity entity)
	{
		mConfig.setConfigurationEntity(entity);

		saveConfig();
	}


	public ConfigConfigurationEntity getConfigurationEntity()
	{
		if(mConfig.getConfigurationEntity()!=null) return mConfig.getConfigurationEntity();
		else return new ConfigConfigurationEntity();
	}


	public TreeNodeEntity tryToFillTreeNodeEntity(TreeNodeEntity entity)
	{
		for(ConfigClassInfoEntity item : mConfig.getClassInfoList())
		{
			if(item.getMethod().equals(entity.getMethod()) && item.getUri().equals(entity.getUri())) entity.setName(item.getName());
		}
		return entity;
	}
}
