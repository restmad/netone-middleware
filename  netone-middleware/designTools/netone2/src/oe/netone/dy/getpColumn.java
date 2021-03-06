package oe.netone.dy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;

import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyForm;
import com.jl.common.dyform.DyFormColumn;
import com.sun.xml.internal.bind.v2.model.core.ID;

import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.web.data.dyform.utils.DymaticFormCheck;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.web.tag.resource.LoadClerkTag;

public class getpColumn {
	public List getc(String formid) throws Exception {
		DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
		List aList = dysc.fetchColumnList(formid);
		List rList= new ArrayList();
	 	for (Iterator iterator = aList.iterator(); iterator.hasNext();) {
	   		TCsColumn name = (TCsColumn) iterator.next();
	   		if(name.getColumnid().toUpperCase().equals("BELONGX") || name.getColumnid().toUpperCase().equals("TIMEX") ){
		 	}else{
	   		rList.add(name);
		 	}
	   		}
		return rList;
	}
	public List getColumn(String formid) throws Exception {
		DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
		List<DyFormColumn> aList=DyEntry.iv().fetchColumnListForDesign(formid);
		List listremove=new ArrayList();
		//过滤了belongx与TIMEX字段
		for (Iterator iterator = aList.iterator(); iterator.hasNext();) {
			DyFormColumn object = (DyFormColumn) iterator.next();
		 	if(object.getColumnid().toUpperCase().equals("BELONGX") || object.getColumnid().toUpperCase().equals("TIMEX") ){
		 	}else{
		 		String ext=object.getExtendattribute();
		 		if (ext != null && !ext.equals("")) {	 
				  //设置长度
					String size = StringUtils.substringBetween(ext,
							"size$:",
							DymaticFormCheck._FINAL_CHECK);
					object.setColumncode(size);
					    object.setInitScript(StringUtils.substringBetween(ext,
					    		DymaticFormCheck._CHECK_INITSCRIPT,
								DymaticFormCheck._FINAL_CHECK));
						object.setFocusScript(StringUtils.substringBetween(ext,
					    		DymaticFormCheck._CHECK_FOCUSSCRIPT,
								DymaticFormCheck._FINAL_CHECK));
						object.setLoseFocusScript(StringUtils.substringBetween(ext,
					    		DymaticFormCheck._CHECK_LOSEFOCUSSCRIPT,
								DymaticFormCheck._FINAL_CHECK));
						object.setOnchangeScript(StringUtils.substringBetween(ext,
					    		DymaticFormCheck._CHECK_ONCHANGESCRIPT,
								DymaticFormCheck._FINAL_CHECK));
						object.setRegExpression(StringUtils.substringBetween(ext,
					    		DymaticFormCheck._CHECK_CHECKRULE,
								DymaticFormCheck._FINAL_CHECK));
				 }else{
					    object.setInitScript("");
						object.setFocusScript("");
						object.setLoseFocusScript("");
						object.setOnchangeScript("");
						object.setRegExpression("");
		 		}
				 listremove.add( object);
				}
		 	}
		if(aList.size()>1){
		 String column=aList.get(aList.size()-1).getColumnid();
		 column=StringUtils.substringAfterLast(column, "column");
		 listremove.add(column);
		}
		return listremove;
	}
}
