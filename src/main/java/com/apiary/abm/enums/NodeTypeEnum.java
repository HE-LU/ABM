package com.apiary.abm.enums;


public enum NodeTypeEnum
{
	REQUEST("request"), RESPONSE("response"), PARSER("parser");

	private final String value;


	private NodeTypeEnum(String value)
	{
		this.value = value;
	}


	@Override
	public String toString()
	{
		return value;
	}

}

