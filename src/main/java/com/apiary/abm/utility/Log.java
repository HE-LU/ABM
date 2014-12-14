package com.apiary.abm.utility;

import com.apiary.abm.ABMConfig;


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
}
