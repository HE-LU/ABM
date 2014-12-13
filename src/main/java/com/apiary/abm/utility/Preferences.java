package com.apiary.abm.utility;


import com.apiary.abm.ui.ABMToolWindow;


public class Preferences
{
	public static final String PREF_PLUGIN_INITIALIZED = "pref_plugin_initialized";
	public static final String PREF_APIARY_BLUEPRINT_URL = "pref_apiary_blueprint_url";
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


	public String getApiaryBlueprintUrl()
	{
		return new String(mPreferences.getByteArray(PREF_APIARY_BLUEPRINT_URL, null));
	}


	// SETTERS
	public void setPluginInitialized(boolean input)
	{
		mPreferences.putBoolean(PREF_PLUGIN_INITIALIZED, input);
	}


	public void setApiaryBlueprintUrl(String input)
	{
		mPreferences.putByteArray(PREF_APIARY_BLUEPRINT_URL, input.getBytes());
	}
}
