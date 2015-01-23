package com.apiary.abm.enums;


public enum TreeNodeTypeEnum
{
	// 	root
	//		configuration_root
	//			configuration
	//		error_root
	//			error
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
	//		hidden_root
	//			hidden

	ROOT("root"),
	CONFIGURATION_ROOT("configuration_root"),
	CONFIGURATION("configuration"),
	ERROR_ROOT("error_root"),
	ERROR("error"),
	CANNOT_RECOGNIZE_ROOT("cannot_recognize_root"),
	CANNOT_RECOGNIZE("cannot_recognize"),
	NOT_IMPLEMENTED_ROOT("not_implemented_root"),
	NOT_IMPLEMENTED_REQUEST_ROOT("not_implemented_request_root"),
	NOT_IMPLEMENTED_REQUEST("not_implemented_request"),
	NOT_IMPLEMENTED_ENTITY_ROOT("not_implemented_entity_root"),
	NOT_IMPLEMENTED_ENTITY("not_implemented_entity"),
	MODIFIED_ROOT("modified_root"),
	MODIFIED("modified"),
	REMOVED_ROOT("removed_root"),
	REMOVED("removed"),
	HIDDEN_ROOT("hidden_root"),
	HIDDEN("hidden");

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

