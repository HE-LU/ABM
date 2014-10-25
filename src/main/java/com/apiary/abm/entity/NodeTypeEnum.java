package com.apiary.abm.entity;


public enum NodeTypeEnum
{
	ROOT("root"), ERROR_ROOT("error_root"), WARNING_ROOT("warning_root"), MISSING_ROOT("missing_root"),
	ERROR("error"), WARNING("warning"), MISSING("missing");

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

