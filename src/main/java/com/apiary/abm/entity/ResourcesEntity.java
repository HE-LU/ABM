
package com.apiary.abm.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResourcesEntity
{

	@SerializedName("actions")
   	private List mActions;

	@SerializedName("description")
   	private String mDescription;

	@SerializedName("model")
   	private ModelEntity mModel;

	@SerializedName("name")
   	private String mName;

	@SerializedName("parameters")
   	private List mParameters;

	@SerializedName("uriTemplate")
   	private String mUriTemplate;

 	public List getActions(){
		return this.mActions;
	}
 	public String getDescription(){
		return this.mDescription;
	}
 	public ModelEntity getModel(){
		return this.mModel;
	}
 	public String getName(){
		return this.mName;
	}
 	public List getParameters(){
		return this.mParameters;
	}
 	public String getUriTemplate(){
		return this.mUriTemplate;
	}
}
