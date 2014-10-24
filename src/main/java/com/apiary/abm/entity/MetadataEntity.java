
package com.apiary.abm.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MetadataEntity
{

	@SerializedName("name")
   	private String mName;

	@SerializedName("value")
   	private String mValue;

 	public String getName(){
		return this.mName;
	}
 	public String getValue(){
		return this.mValue;
	}
}
