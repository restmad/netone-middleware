package com.jl.common.report.obj.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordPools {
	
	

	Map record=new HashMap();

	Map dataset=new HashMap();
	Map table=new HashMap();
	Map tablewei=new HashMap();

	public Map getRecord() {
		return record;
	}

	public void setRecord(Map record) {
		this.record = record;
	}

	public Map getDataset() {
		return dataset;
	}

	public void setDataset(Map dataset) {
		this.dataset = dataset;
	}

	public static void main(String[] args) {

		RecordPools dp = new RecordPools();
		String ds = "ds1";
		List dsv1 = new ArrayList();
		dp.getDataset().put(ds, dsv1);
		
		

	}

	public Map getTable() {
		return table;
	}

	public void setTable(Map table) {
		this.table = table;
	}

	public Map getTablewei() {
		return tablewei;
	}

	public void setTablewei(Map tablewei) {
		this.tablewei = tablewei;
	}

}
