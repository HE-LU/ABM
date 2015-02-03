package com.apiary.abm.utility;


import com.apiary.abm.entity.BodyObjectEntity;
import com.apiary.abm.entity.TreeNodeEntity;
import com.apiary.abm.entity.config.ConfigClassInfoEntity;
import com.apiary.abm.entity.config.ConfigConfigurationEntity;
import com.apiary.abm.entity.config.ConfigEntityNameEntity;
import com.apiary.abm.entity.config.ConfigRootEntity;
import com.apiary.abm.ui.ABMToolWindow;
import com.intellij.openapi.project.Project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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


	public void saveTreeNodeEntity(TreeNodeEntity entity)
	{
		ConfigClassInfoEntity classInfoEntity = new ConfigClassInfoEntity();
		List<ConfigEntityNameEntity> requests = new ArrayList<ConfigEntityNameEntity>();
		List<ConfigEntityNameEntity> responses = new ArrayList<ConfigEntityNameEntity>();

		if(entity.getRequestBody()!=null) for(BodyObjectEntity body : entity.getRequestBody())
			requests.add(new ConfigEntityNameEntity(body.getSerializableName(), body.getEntityName()));

		if(entity.getResponseBody()!=null) for(BodyObjectEntity body : entity.getResponseBody())
			responses.add(new ConfigEntityNameEntity(body.getSerializableName(), body.getEntityName()));

		classInfoEntity.setUri(entity.getUri());
		classInfoEntity.setMethod(entity.getMethod());
		classInfoEntity.setMethodName(entity.getMethodName());
		classInfoEntity.setHidden(entity.isHidden());
		classInfoEntity.setAsync(entity.isAsync());
		classInfoEntity.setRequests(requests);
		classInfoEntity.setResponses(responses);

		for(ConfigClassInfoEntity item : mConfig.getClassInfoList())
			if(item.getUri().equals(classInfoEntity.getUri()) && item.getMethod().equals(classInfoEntity.getMethod()))
			{
				mConfig.getClassInfoList().remove(item);
				break;
			}

		mConfig.addClassInfoItem(classInfoEntity);
		saveConfig();
	}


	public void tryToFillTreeNodeEntity(TreeNodeEntity entity)
	{
		for(ConfigClassInfoEntity item : mConfig.getClassInfoList())
		{
			if(item.getMethod().equals(entity.getMethod()) && item.getUri().equals(entity.getUri()))
			{
				List<BodyObjectEntity> requests = entity.getRequestBody();
				List<BodyObjectEntity> responses = entity.getResponseBody();

				if(requests!=null) for(ConfigEntityNameEntity bodyItem : item.getRequests())
					for(BodyObjectEntity bodyEntity : requests)
						if(bodyItem.getSerializableName()!=null && bodyEntity.getSerializableName()!=null && bodyItem.getSerializableName().equals(bodyEntity.getSerializableName()))
						{
							bodyEntity.setEntityName(bodyItem.getEntityName());
							break;
						}

				if(responses!=null) for(ConfigEntityNameEntity bodyItem : item.getResponses())
					for(BodyObjectEntity bodyEntity : responses)
						if(bodyItem.getSerializableName().equals(bodyEntity.getSerializableName()))
						{
							bodyEntity.setEntityName(bodyItem.getEntityName());
							break;
						}

				entity.setMethodName(item.getMethodName());
				entity.setHidden(item.isHidden());
				entity.setAsync(item.isAsync());
				entity.setRequestBody(requests);
				entity.setResponseBody(responses);
			}
		}
	}
}
