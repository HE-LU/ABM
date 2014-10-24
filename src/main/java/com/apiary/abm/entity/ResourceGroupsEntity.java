
package com.apiary.abm.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResourceGroupsEntity
{

	@SerializedName("description")
   	private String mDescription;

	@SerializedName("name")
   	private String mName;

	@SerializedName("resources")
   	private List mResources;

 	public String getDescription(){
		return this.mDescription;
	}
 	public String getName(){
		return this.mName;
	}
 	public List getResources(){
		return this.mResources;
	}
}
