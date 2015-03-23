package com.apiary.abm.utility;


import com.apiary.abm.enums.ConnectionTypeEnum;
import com.apiary.abm.ui.ABMToolWindow;


public class Preferences
{
	public static final String PREFS_BLUEPRINT_CONNECTION_TYPE = "prefs_blueprint_connection_type";
	public static final String PREFS_BLUEPRINT_CONNECTION_PATH = "prefs_blueprint_connection_path";
	public static final String PREFS_BLUEPRINT_CONNECTION_DOC_KEY = "prefs_blueprint_connection_doc_key";
	public static final String PREFS_BLUEPRINT_TMP_FILE_LOCATION = "prefs_blueprint_tmp_file_location";
	public static final String PREFS_BLUEPRINT_JSON_TMP_FILE_LOCATION = "prefs_blueprint_json_tmp_file_location";
	public static final String PREFS_GA_CID = "prefs_ga_cid";
	private java.util.prefs.Preferences mPreferences;


	public Preferences()
	{
		mPreferences = java.util.prefs.Preferences.userNodeForPackage(ABMToolWindow.class);
	}


	// GETTERS
	public ConnectionTypeEnum getBlueprintConnectionType()
	{
		try
		{
			return ConnectionTypeEnum.toConnectionType(mPreferences.getInt(PREFS_BLUEPRINT_CONNECTION_TYPE, -1));
		}
		catch(Exception e)
		{
			return ConnectionTypeEnum.toConnectionType(-1);
		}
	}


	public String getBlueprintConnectionPath()
	{
		try
		{
			return mPreferences.get(PREFS_BLUEPRINT_CONNECTION_PATH, null);
		}
		catch(Exception e)
		{
			return null;
		}
	}


	public String getBlueprintConnectionDocKey()
	{
		try
		{
			return mPreferences.get(PREFS_BLUEPRINT_CONNECTION_DOC_KEY, null);
		}
		catch(Exception e)
		{
			return null;
		}
	}


	public String getBlueprintTmpFileLocation()
	{
		try
		{
			return mPreferences.get(PREFS_BLUEPRINT_TMP_FILE_LOCATION, null);
		}
		catch(Exception e)
		{
			return null;
		}
	}


	public String getBlueprintJsonTmpFileLocation()
	{
		try
		{
			return mPreferences.get(PREFS_BLUEPRINT_JSON_TMP_FILE_LOCATION, null);
		}
		catch(Exception e)
		{
			return null;
		}
	}


	public String getGACid()
	{
		try
		{
			return mPreferences.get(PREFS_GA_CID, null);
		}
		catch(Exception e)
		{
			return null;
		}
	}


	// SETTERS
	public void setBlueprintConnectionType(ConnectionTypeEnum value)
	{
		mPreferences.putInt(PREFS_BLUEPRINT_CONNECTION_TYPE, value.toInteger());
	}


	public void setBlueprintConnectionPath(String value)
	{
		mPreferences.put(PREFS_BLUEPRINT_CONNECTION_PATH, value);
	}


	public void setBlueprintConnectionDocKey(String value)
	{
		mPreferences.put(PREFS_BLUEPRINT_CONNECTION_DOC_KEY, value);
	}


	public void setBlueprintTmpFileLocation(String value)
	{
		mPreferences.put(PREFS_BLUEPRINT_TMP_FILE_LOCATION, value);
	}


	public void setBlueprintJsonTmpFileLocation(String value)
	{
		mPreferences.put(PREFS_BLUEPRINT_JSON_TMP_FILE_LOCATION, value);
	}


	public void setGACid(String value)
	{
		mPreferences.put(PREFS_GA_CID, value);
	}
}
