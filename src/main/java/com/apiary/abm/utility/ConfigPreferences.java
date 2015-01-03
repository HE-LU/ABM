package com.apiary.abm.utility;


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
				mConfig = null;
				return;
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

	// Setters

	// Getters
}
