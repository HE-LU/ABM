package com.apiary.abm.utility;

import com.apiary.abm.ABMConfig;

import javax.swing.JOptionPane;


public class Log
{
	public static void d(String msg)
	{
		if(ABMConfig.LOGS) System.out.println(msg);
	}


	public static void e(String msg)
	{
		if(ABMConfig.LOGS) System.err.println(msg);
	}


	public static void a(String msg)
	{
		if(ABMConfig.LOGS)
		{
			JOptionPane.showMessageDialog(null, Utils.generateMessage(msg), "DEBUG", JOptionPane.ERROR_MESSAGE);
		}
	}
}
