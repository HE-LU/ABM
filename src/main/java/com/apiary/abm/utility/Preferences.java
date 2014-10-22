package com.apiary.abm.utility;


import com.apiary.abm.ui.ABMToolWindow;


public class Preferences
{
	private java.util.prefs.Preferences mPreferences;

	public static final String PREF_PLUGIN_INITIALIZED = "pref_plugin_initialized";


	public Preferences()
	{
		mPreferences = java.util.prefs.Preferences.userNodeForPackage(ABMToolWindow.class);
	}

	// GETTERS


	public boolean getPluginInitialized()
	{
		return mPreferences.getBoolean(PREF_PLUGIN_INITIALIZED, false);
	}


	// SETTERS


	public void setPluginInitialized(boolean initialized)
	{
		mPreferences.putBoolean(PREF_PLUGIN_INITIALIZED, initialized);
	}
}
