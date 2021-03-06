package com.jl.common.security3a;

/**
 * 客户信息
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public final class Client3A {
	// 用户显示名
	String name;
	// 用户名
	String clientId;
	// 用户隶属 中间件的节点
	String belongto;
	// 用户隶属 业务系统的节点
	String bussid;
	// 用户 安全KEY
	String key;

	String mobile;
	String email;
	
	String deptid;
	String deptname;

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getBelongto() {
		return belongto;
	}

	public void setBelongto(String belongto) {
		this.belongto = belongto;
	}

	public String getBussid() {
		return bussid;
	}

	public void setBussid(String bussid) {
		this.bussid = bussid;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
