package com.apiary.abm.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class BodyObjectEntity
{
	private String mEntityName;
	private String mSerializableName;
	private List<BodyVariableEntity> mVariables;


	public BodyObjectEntity(String mSerializableName, String mEntityName, List<BodyVariableEntity> mVariables)
	{
		this.mSerializableName = mSerializableName;
		this.mEntityName = mEntityName;
		this.mVariables = mVariables;
	}


	public static BodyObjectEntity findBySerializableName(List<BodyObjectEntity> list, String name)
	{
		for(BodyObjectEntity entity : list)
			if(entity.getSerializableName().equals(name)) return entity;
		return null;
	}


	@Override
	public int hashCode()
	{
		return mSerializableName.hashCode();
	}


	@Override
	public boolean equals(Object object)
	{
		if(object == this) return true;
		if(!(object instanceof BodyObjectEntity)) return false;
		BodyObjectEntity entity = (BodyObjectEntity) object;
		if(mEntityName.equals(entity.getEntityName()) && mSerializableName.equals(entity.getSerializableName()))
		{
			Set<BodyVariableEntity> set1 = new HashSet<BodyVariableEntity>();
			set1.addAll(mVariables);
			Set<BodyVariableEntity> set2 = new HashSet<BodyVariableEntity>();
			set2.addAll(entity.getVariables());
			if(set1.equals(set2)) return true;
		}
		return false;
	}


	public String getEntityName()
	{
		return mEntityName;
	}


	public void setEntityName(String mEntityName)
	{
		this.mEntityName = mEntityName;
	}


	public String getSerializableName()
	{
		return mSerializableName;
	}


	public void setSerializableName(String mSerializableName)
	{
		this.mSerializableName = mSerializableName;
	}


	public List<BodyVariableEntity> getVariables()
	{
		return mVariables;
	}


	public void setVariables(List<BodyVariableEntity> mVariables)
	{
		this.mVariables = mVariables;
	}
}