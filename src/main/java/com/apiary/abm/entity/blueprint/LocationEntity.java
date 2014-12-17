package com.apiary.abm.entity.blueprint;

import com.google.gson.annotations.SerializedName;


/**
 * Created by tuxilero on 26.10.14.
 */
public class LocationEntity
{
	@SerializedName("index")
	private Number mIndex;

	@SerializedName("length")
	private Number mLength;


	public Number getIndex()
	{
		return this.mIndex;
	}


	public Number getLength()
	{
		return this.mLength;
	}

}
