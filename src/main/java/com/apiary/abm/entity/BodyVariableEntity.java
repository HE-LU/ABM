package com.apiary.abm.entity;


public class BodyVariableEntity
{
	private String mName;
	private Object mType;
	private String mTypeName = "";


	public BodyVariableEntity(String mName, Object mType)
	{
		this.mName = mName;
		this.mType = mType;
		this.mTypeName = "";
	}


	public BodyVariableEntity(String mName, Object mType, String mTypeName)
	{
		this.mName = mName;
		this.mType = mType;
		this.mTypeName = mTypeName;
	}


	@Override
	public int hashCode()
	{
		return mName.hashCode();
	}


	@Override
	public boolean equals(Object object)
	{
		if(object==this) return true;
		if(!(object instanceof BodyVariableEntity)) return false;
		BodyVariableEntity entity = (BodyVariableEntity) object;
		return mName.equals(entity.getName()) && mType.equals(entity.getType()) && mTypeName.equals(entity.getTypeName());
	}


	public String getName()
	{
		return mName;
	}


	public void setName(String mName)
	{
		this.mName = mName;
	}


	public Object getType()
	{
		return mType;
	}


	public void setType(Object mType)
	{
		this.mType = mType;
	}


	public String getTypeName()
	{
		return mTypeName;
	}


	public void setTypeName(String mTypeName)
	{
		this.mTypeName = mTypeName;
	}
}