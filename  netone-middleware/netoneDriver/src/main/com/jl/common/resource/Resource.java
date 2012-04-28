package com.jl.common.resource;

/**
 * 资源
 * 
 * @version 1.0.0
 * @history
 */
public final class Resource {

	/** 资源ID */
	private String resourceid;
	/** 资源编码 */
	private String resourcecode;
	/** 资源名称 */
	private String resourcename;
	/** 资源类型 */
	private String types;
	private String parentid;
	
	//ext 
	private String id;
	private String text;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getResourceid() {
		return resourceid;
	}

	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}

	public String getResourcecode() {
		return resourcecode;
	}

	public void setResourcecode(String resourcecode) {
		this.resourcecode = resourcecode;
	}

	public String getResourcename() {
		return resourcename;
	}

	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

}
