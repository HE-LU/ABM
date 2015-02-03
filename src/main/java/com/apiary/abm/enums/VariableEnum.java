package com.apiary.abm.enums;


public enum VariableEnum
{
	STRING("String"), NUMBER("Number"), INTEGER("Integer"), DOUBLE("Double"),
	BOOLEAN("Boolean"), ENUM("Enum"), COLLECTION("Collection"), MAP("Map"), NONE("None");

	private final String value;


	private VariableEnum(String value)
	{
		this.value = value;
	}


	@Override
	public String toString()
	{
		return value;
	}

}

