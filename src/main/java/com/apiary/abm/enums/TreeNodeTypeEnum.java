package com.apiary.abm.enums;


public enum TreeNodeTypeEnum
{
	// 	root
	//		configuration_problem_root
	//			configuration_problem
	//		blueprint_problem_root
	//			blueprint_problem
	// 		not_implemented_root
	//			not_implemented
	//		modified_root
	//			modified
	//		removed_root
	//			removed
	//		hidden_root
	//			hidden

	ROOT("root"),
	CONFIGURATION_PROBLEM_ROOT("configuration_problem_root"),
	CONFIGURATION_PROBLEM("configuration_problem"),
	BLUEPRINT_PROBLEM_ROOT("blueprint_problem_root"),
	BLUEPRINT_PROBLEM("blueprint_problem"),
	NOT_IMPLEMENTED_ROOT("not_implemented_root"),
	NOT_IMPLEMENTED("not_implemented"),
	MODIFIED_ROOT("modified_root"),
	MODIFIED("modified"),
	REMOVED_ROOT("removed_root"),
	REMOVED("removed"),
	HIDDEN_ROOT("hidden_root"),
	HIDDEN("hidden"),
	NONE("none");

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

