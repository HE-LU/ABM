package com.apiary.abm.enums;

public enum ConnectionType
{
	CONNECTION_TYPE_NONE(0), CONNECTION_TYPE_DOC(1), CONNECTION_TYPE_WEB_URL(2), CONNECTION_TYPE_FILE(3);

	private final Integer value;


	private ConnectionType(int value)
	{
		this.value = value;
	}


	public Integer toInteger()
	{
		return value;
	}


	public static ConnectionType toConnectionType(int value)
	{
		switch(value)
		{
			case 1:
				return CONNECTION_TYPE_DOC;
			case 2:
				return CONNECTION_TYPE_WEB_URL;
			case 3:
				return CONNECTION_TYPE_FILE;
		}
		return CONNECTION_TYPE_NONE;
	}
}
