package com.apiary.abm.enums;


public enum TreeNodeTypeEnum
{
	// 	root
	//		configuration_root
	//			configuration
	// 		cannot_recognize_root
	// 			cannot_recognize
	// 		not_implemented_root
	//			not_implemented_request_root
	//				not_implemented_request
	//			not_implemented_entity_root
	//				not_implemented_entity
	//		modified_root
	//			modified
	//		removed_root
	//			removed

	ROOT("root"), CONFIGURATION_ROOT("cannot_recognize_root"), CANNOT_RECOGNIZE_ROOT("cannot_recognize_root"),
	NOT_IMPLEMENTED_ROOT("not_implemented_root"), MODIFIED_ROOT("modified_root"), REMOVED_ROOT("removed_root"),
	CONFIGURATION("cannot_recognize"), CANNOT_RECOGNIZE("cannot_recognize"), NOT_IMPLEMENTED_REQUEST_ROOT("not_implemented_request_root"),
	NOT_IMPLEMENTED_ENTITY_ROOT("not_implemented_entity_root"), NOT_IMPLEMENTED_REQUEST("not_implemented_request"),
	NOT_IMPLEMENTED_ENTITY("not_implemented_entity"), MODIFIED("modified"), REMOVED("removed");

	private final String value;


	private TreeNodeTypeEnum(String value)
	{
		this.value = value;
	}


	@Override
	public String toString()
	{
		return value;
	}

}

