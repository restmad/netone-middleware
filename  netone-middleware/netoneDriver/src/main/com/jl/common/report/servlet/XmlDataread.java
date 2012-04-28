package com.jl.common.report.servlet;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jl.common.report.obj.core.Read_dataset;
import com.jl.common.report.obj.core.Read_report;
import com.jl.common.report.obj.core.RecordPools;
import com.jl.common.workflow.DbTools2;



public class XmlDataread {

	public static RecordPools readXMLSql(Read_report r_report,String condition)
	{
		RecordPools rps=new RecordPools();
		for (int i = 0; i < r_report.getDatasetlist().size(); i++) {
			String id=r_report.getDatasetlist().get(i).getId();
			String sqlstr=r_report.getDatasetlist().get(i).getSqlstr();
			rps.getDataset().put(id, datalist(sqlstr,condition));
			//System.out.println(id+":::"+sqlstr);
		}
		for (int i = 0; i < r_report.getRecordlist().size(); i++) {
			String id=r_report.getRecordlist().get(i).getId();
			String sqlstr=r_report.getRecordlist().get(i).getSqlstr();
			rps.getRecord().put(id, datalist(sqlstr,condition));
			//System.out.println(id+":::"+sqlstr);
			
			for (int j = 0; j < r_report.getRecordlist().get(i).getTdlist().size(); j++) {
				if(r_report.getRecordlist().get(i).getTdlist().get(j).getTdclr().getClass().getName()=="reportread.Read_dataset")
				{
					Read_dataset r_d=(Read_dataset)r_report.getRecordlist().get(i).getTdlist().get(j).getTdclr();
					String did=r_d.getId();
					String dsqlstr=r_d.getSqlstr();
					rps.getDataset().put(did, datalist(dsqlstr,condition));
					//System.out.println(did+":::"+dsqlstr);
				}
			}
			
		}	
		
		return rps;
	}
	public static List datalist(String sqlstr,String condition)
	{
		List list=DbTools2.queryData(sqlstr+condition);
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			for (Iterator z = object.keySet().iterator(); z.hasNext();) {
				String xxxxx = (String) z.next();
				//System.out.print(xxxxx+":"+object.get(xxxxx)+";");
				
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
		System.out.println();
		
		return list;
	}
}