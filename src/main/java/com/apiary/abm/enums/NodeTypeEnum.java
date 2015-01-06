package com.apiary.abm.enums;


public enum NodeTypeEnum
{
	// 	root
	// 		cannot_recognize
	// 			cannot_recognize_item
	// 		not_implemented
	//			not_implemented_item
	//		modified
	//			modified_item
	//		removed
	//			removed_item

	ROOT("root"), CANNOT_RECOGNIZE_ROOT("cannot_recognize_root"), NOT_IMPLEMENTED_ROOT("not_implemented_root"), MODIFIED_ROOT("modified_root"), REMOVED_ROOT("removed_root"),
	CANNOT_RECOGNIZE("cannot_recognize"), NOT_IMPLEMENTED("not_implemented"), MODIFIED("modified"), REMOVED("removed");

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

