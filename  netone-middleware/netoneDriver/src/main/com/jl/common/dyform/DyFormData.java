package com.jl.common.dyform;

import oe.cav.bean.logic.bus.TCsBus;

public final class DyFormData extends TCsBus {

	private String status;
	private boolean run;
	private String runtimeid;
	private String statistical;
	/**
	 * ����LSH ��ʽ��1,2,3,4
	 */
	private String lshs;

	public String getStatistical() {
		return statistical;
	}

	public void setStatistical(String statistical) {
		this.statistical = statistical;
	}

	public String getRuntimeid() {
		return runtimeid;
	}

	public void setRuntimeid(String runtimeid) {
		this.runtimeid = runtimeid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	public String getLshs() {
		return lshs;
	}

	public void setLshs(String lshs) {
		this.lshs = lshs;
	}

}
