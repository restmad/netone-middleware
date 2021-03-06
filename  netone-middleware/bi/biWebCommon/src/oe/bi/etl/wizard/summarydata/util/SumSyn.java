package oe.bi.etl.wizard.summarydata.util;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import oe.bi.etl.wizard.summarydata.SummaryDataForm;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.tools.DyObj;
import oe.midware.dyform.service.DyFormDesignService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;


public class SumSyn {
	/**
	 * 进入Syn.jsp即同步页面
	 * 
	 * @param so
	 * @param request
	 */
	public static void main(SummaryDataForm so, HttpServletRequest request) {
		ResourceRmi rsrmi = null;
		try {
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = rsrmi.loadResourceById(so.getChkid());
			so.setFormname(upo.getName());
			so.setFormnaturalname(upo.getNaturalname());
			DyFormDesignService dfd = (DyFormDesignService) RmiEntry.iv("dydesign");
			DyObj objx = dfd.fromDy(upo.getExtendattribute());
			TCsForm tcform = objx.getFrom();
			so.setLevel(tcform.getDimlevel());
			so.setSelpoint(tcform.getTimelevel());
			so.setSqlview(tcform.getSqlinfo());
			so.setDatasource(tcform.getSystemid());
			String formcode = tcform.getFormcode();
			String syntable = tcform.getDescription();
			request.setAttribute("formcode", formcode);
			request.setAttribute("syntable", syntable);
			List columnlist = objx.getColumn();
			StringBuffer chinesenames = new StringBuffer();
			StringBuffer cnames = new StringBuffer();
			StringBuffer ctypes = new StringBuffer();
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			for (Iterator iter = columnlist.iterator(); iter.hasNext();) {
				TCsColumn column = (TCsColumn) iter.next();
				if (column.isUseable()) {
					String chinesename = column.getColumname();
					String htmltype = column.getHtmltype();
					String othername = column.getColumnid();
					chinesenames.append(chinesename + ",");
					cnames.append(othername + ",");
					ctypes.append(htmltype + ",");
					Map<String, String> hashMap = new LinkedHashMap<String, String>();
					hashMap.put("othername", othername);
					hashMap.put("type", htmltype);
					list.add(hashMap);
				}
			}
			so.setChinesename(StringUtils.substringBeforeLast(chinesenames.toString(), ","));
			so.setCname(StringUtils.substringBeforeLast(cnames.toString(), ","));
			so.setCtype(StringUtils.substringBeforeLast(ctypes.toString(), ","));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
}
