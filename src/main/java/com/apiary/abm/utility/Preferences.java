package com.apiary.abm.utility;


import com.apiary.abm.enums.ConnectionType;
import com.apiary.abm.ui.ABMToolWindow;


public class Preferences
{
	public static final String PREF_PLUGIN_INITIALIZED = "pref_plugin_initialized";
	public static final String PREF_BLUEPRINT_CONNECTION_TYPE = "pref_blueprint_connection_type";
	public static final String PREF_BLUEPRINT_CONNECTION_PATH = "pref_blueprint_connection_path";
	public static final String PREF_BLUEPRINT_CONNECTION_DOC_KEY = "pref_blueprint_connection_doc_key";
	public static final String PREF_BLUEPRINT_TMP_FILE_LOCATION = "pref_blueprint_tmp_file_location";
	private java.util.prefs.Preferences mPreferences;


	public Preferences()
	{
		mPreferences = java.util.prefs.Preferences.userNodeForPackage(ABMToolWindow.class);
	}


	// GETTERS
	public boolean getPluginInitialized()
	{
		return mPreferences.getBoolean(PREF_PLUGIN_INITIALIZED, false);
	}


	public ConnectionType getBlueprintConnectionType()
	{
		return ConnectionType.toConnectionType(mPreferences.getInt(PREF_BLUEPRINT_CONNECTION_TYPE, -1));
	}


	public String getBlueprintConnectionPath()
	{
		return new String(mPreferences.getByteArray(PREF_BLUEPRINT_CONNECTION_PATH, null));
	}


	public String getBlueprintConnectionDocKey()
	{
		return new String(mPreferences.getByteArray(PREF_BLUEPRINT_CONNECTION_DOC_KEY, null));
	}


	public String getBlueprintTmpFileLocation()
	{
		return new String(mPreferences.getByteArray(PREF_BLUEPRINT_TMP_FILE_LOCATION, null));
	}


	// SETTERS
	public void setPluginInitialized(boolean value)
	{
		mPreferences.putBoolean(PREF_PLUGIN_INITIALIZED, value);
	}


	public void setBlueprintConnectionType(ConnectionType value)
	{
		mPreferences.putInt(PREF_BLUEPRINT_CONNECTION_TYPE, value.toInteger());
	}


	public void setBlueprintConnectionPath(String value)
	{
		mPreferences.putByteArray(PREF_BLUEPRINT_CONNECTION_PATH, value.getBytes());
	}


	public void setBlueprintConnectionDocKey(String value)
	{
		mPreferences.putByteArray(PREF_BLUEPRINT_CONNECTION_DOC_KEY, value.getBytes());
	}


	public void setBlueprintTmpFileLocation(String value)
	{
		mPreferences.putByteArray(PREF_BLUEPRINT_TMP_FILE_LOCATION, value.getBytes());
	}


}
