package com.jl.web.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oe.cav.bean.logic.column.TCsColumn;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.serialize.dao.PageInfo;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.common.JSONUtil2;
import com.jl.common.app.AppEntry;
import com.jl.common.app.AppHandleIfc;
import com.jl.common.app.AppObj;
import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyForm;
import com.jl.common.dyform.DyFormBuildHtmlExt;
import com.jl.common.dyform.DyFormComp;
import com.jl.common.dyform.DyFormData;
import com.jl.common.resource.Resource;
import com.jl.common.resource.ResourceNode;
import com.jl.common.security3a.Client3A;
import com.jl.common.security3a.SecurityEntry;
import com.jl.common.workflow.TWfActive;
import com.jl.common.workflow.TWfActivePass;
import com.jl.common.workflow.TWfParticipant;
import com.jl.common.workflow.WfEntry;
import com.jl.entity.User;
import com.jl.service.BaseService;
import com.jl.service.FrameService;

public class FrameActionExt extends AbstractAction {

	public ActionForward portalView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		loadAccordtree(mapping, form, request, response);
		return mapping.findForward("portalView");
	}

	public ActionForward onMainView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// this.getClientPermissions(request, response);
		request.setAttribute("limit", new DyForm().getEachPageSize_());
		String naturalname = request.getParameter("naturalname");
		AppObj app = AppEntry.iv().loadApp(naturalname);
		User user = getOnlineUser(request);// ��ȡ��ǰ�û�
		String formcode = app.getDyformCode_();
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		String columns = DyFormBuildHtmlExt.buildExtColumns(dyform, "0", true,
				user.getUserCode());
		String fields = DyFormBuildHtmlExt.buildExtFields(dyform);
		request.setAttribute("columns", columns);
		request.setAttribute("fields", fields);
		String queryColumnHtml = DyFormBuildHtmlExt.buildQueryColumn(dyform
				.getQueryColumn_());
		request.setAttribute("queryColumnsHtml", queryColumnHtml);
		String queryConditionHtml = DyFormBuildHtmlExt
				.buildQueryCondition(dyform.getAllColumn_());
		request.setAttribute("queryConditionHtml", queryConditionHtml);

		// ��ʽ
		String linkcss = DyFormComp.getStyle(getURL(dyform.getStyleinfourl_()));
		request.setAttribute("linkcss", linkcss);
		request.setAttribute("datecompFunc", DyFormComp.getInitFuncScript());

		// ��ͨ��ѯ
		String lsh = "";
		boolean issub = false;// �Ƿ��ӱ���
		String forms = DyFormBuildHtmlExt.buildQueryForm1(dyform, user
				.getNLevelName()
				+ "/" + user.getUserName() + "," + user.getNLevelName(),
				naturalname, lsh, issub, request.getParameter("url"));
		request.setAttribute("queryform1", forms);

		// �߼���ѯ
		setExtQueryColumnVar(request, formcode);

		return mapping.findForward("onMainView");
	}

	public ActionForward onMainView2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// this.getClientPermissions(request, response);
		request.setAttribute("limit", new DyForm().getEachPageSize_());
		User user = getOnlineUser(request);// ��ȡ��ǰ�û�
		String naturalname = request.getParameter("naturalname");
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		String columns = DyFormBuildHtmlExt.buildExtColumns(dyform, "0", true,
				user.getUserCode());
		String fields = DyFormBuildHtmlExt.buildExtFields(dyform);
		request.setAttribute("columns", columns);
		request.setAttribute("fields", fields);

		// ��ʽ
		String linkcss = DyFormComp.getStyle(getURL(dyform.getStyleinfourl_()));
		request.setAttribute("linkcss", linkcss);
		request.setAttribute("datecompFunc", DyFormComp.getInitFuncScript());

		// �ӱ���0
		DyForm subdyform = dyform.getSubform_()[0];
		String subcolumns = DyFormBuildHtmlExt.buildExtColumns(subdyform, null,
				true, user.getUserCode());
		String subfields = DyFormBuildHtmlExt.buildExtFields(subdyform);
		request.setAttribute("subcolumns", subcolumns);
		request.setAttribute("subfields", subfields);

		// ��ͨ��ѯ
		String lsh = "";
		boolean issub = false;// �Ƿ��ӱ���
		String forms = DyFormBuildHtmlExt.buildQueryForm1(dyform, user
				.getNLevelName()
				+ "/" + user.getUserName() + "," + user.getNLevelName(),
				naturalname, lsh, issub, request.getParameter("url"));
		request.setAttribute("queryform1", forms);

		// �߼���ѯ
		setExtQueryColumnVar(request, formcode);

		String queryColumnHtml = DyFormBuildHtmlExt.buildQueryColumn(dyform
				.getQueryColumn_());
		request.setAttribute("queryColumnsHtml", queryColumnHtml);
		String queryConditionHtml = DyFormBuildHtmlExt
				.buildQueryCondition(dyform.getAllColumn_());
		request.setAttribute("queryConditionHtml", queryConditionHtml);
		return mapping.findForward("onMainView2");
	}

	public ActionForward onMainView3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// this.getClientPermissions(request, response);
		request.setAttribute("limit", new DyForm().getEachPageSize_());
		String naturalname = request.getParameter("naturalname");
		User user = getOnlineUser(request);// ��ȡ��ǰ�û�
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		String columns = DyFormBuildHtmlExt.buildExtColumns(dyform, "0", true,
				user.getUserCode());
		String fields = DyFormBuildHtmlExt.buildExtFields(dyform);
		request.setAttribute("columns", columns);
		request.setAttribute("fields", fields);

		// ��ʽ
		String linkcss = DyFormComp.getStyle(getURL(dyform.getStyleinfourl_()));
		request.setAttribute("linkcss", linkcss);
		request.setAttribute("datecompFunc", DyFormComp.getInitFuncScript());

		// ���ܻ���ϸ
		String extmode = request.getParameter("extmode");
		TCsColumn[] extdyform = null;

		if ("1".equals(extmode)) {
			List<TCsColumn> list = DyEntry.iv().QueryColumn(formcode, "2");
			extdyform = (TCsColumn[]) list.toArray(new TCsColumn[list.size()]);
		} else if ("2".equals(extmode)) {
			List<TCsColumn> list = DyEntry.iv().QueryColumn(formcode, "3");
			extdyform = (TCsColumn[]) list.toArray(new TCsColumn[list.size()]);
		}
		String extcolumns = DyFormBuildHtmlExt.buildExtColumnsX(extdyform,
				null, true);
		String extfields = DyFormBuildHtmlExt.buildExtFieldsX(extdyform);
		request.setAttribute("extcolumns", extcolumns);
		request.setAttribute("extfields", extfields);

		// ��ͨ��ѯ
		String lsh = "";
		boolean issub = false;// �Ƿ��ӱ���
		String forms = DyFormBuildHtmlExt.buildQueryForm1(dyform, user
				.getNLevelName()
				+ "/" + user.getUserName() + "," + user.getNLevelName(),
				naturalname, lsh, issub, request.getParameter("url"));
		request.setAttribute("queryform1", forms);

		// �߼���ѯ
		setExtQueryColumnVar(request, formcode);

		String queryColumnHtml = DyFormBuildHtmlExt.buildQueryColumn(dyform
				.getQueryColumn_());
		request.setAttribute("queryColumnsHtml", queryColumnHtml);
		String queryConditionHtml = DyFormBuildHtmlExt
				.buildQueryCondition(dyform.getAllColumn_());
		request.setAttribute("queryConditionHtml", queryConditionHtml);
		return mapping.findForward("onMainView3");
	}

	public ActionForward onMainView4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// this.getClientPermissions(request, response);
		request.setAttribute("limit", new DyForm().getEachPageSize_());
		String naturalname = request.getParameter("naturalname");
		User user = getOnlineUser(request);// ��ȡ��ǰ�û�
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		String columns = DyFormBuildHtmlExt.buildExtColumns(dyform, "0", true,
				user.getUserCode());
		String fields = DyFormBuildHtmlExt.buildExtFields(dyform);
		request.setAttribute("columns", columns);
		request.setAttribute("fields", fields);

		// ��ʽ
		String linkcss = DyFormComp.getStyle(getURL(dyform.getStyleinfourl_()));
		request.setAttribute("linkcss", linkcss);
		request.setAttribute("datecompFunc", DyFormComp.getInitFuncScript());

		// ���ܻ���ϸ
		String extmode = request.getParameter("extmode");
		TCsColumn[] extdyform = null;

		if ("1".equals(extmode)) {
			List<TCsColumn> list = DyEntry.iv().QueryColumn(formcode, "2");
			extdyform = (TCsColumn[]) list.toArray(new TCsColumn[list.size()]);
		} else if ("2".equals(extmode)) {
			List<TCsColumn> list = DyEntry.iv().QueryColumn(formcode, "3");
			extdyform = (TCsColumn[]) list.toArray(new TCsColumn[list.size()]);
		}
		String extcolumns = DyFormBuildHtmlExt.buildExtColumnsX(extdyform,
				null, true);
		String extfields = DyFormBuildHtmlExt.buildExtFieldsX(extdyform);
		request.setAttribute("extcolumns", extcolumns);
		request.setAttribute("extfields", extfields);

		// �ӱ���0
		DyForm subdyform = dyform.getSubform_()[0];
		String subcolumns = DyFormBuildHtmlExt.buildExtColumns(subdyform, null,
				true, user.getUserCode());
		String subfields = DyFormBuildHtmlExt.buildExtFields(subdyform);
		request.setAttribute("subcolumns", subcolumns);
		request.setAttribute("subfields", subfields);

		// ��ͨ��ѯ
		String lsh = "";
		boolean issub = false;// �Ƿ��ӱ���
		String forms = DyFormBuildHtmlExt.buildQueryForm1(dyform, user
				.getNLevelName()
				+ "/" + user.getUserName() + "," + user.getNLevelName(),
				naturalname, lsh, issub, request.getParameter("url"));
		request.setAttribute("queryform1", forms);

		// �߼���ѯ
		setExtQueryColumnVar(request, formcode);

		String queryColumnHtml = DyFormBuildHtmlExt.buildQueryColumn(dyform
				.getQueryColumn_());
		request.setAttribute("queryColumnsHtml", queryColumnHtml);
		String queryConditionHtml = DyFormBuildHtmlExt
				.buildQueryCondition(dyform.getAllColumn_());
		request.setAttribute("queryConditionHtml", queryConditionHtml);
		return mapping.findForward("onMainView4");
	}

	public void onList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String conditions = request.getParameter("conditions");
		String extconditions = request.getParameter("extconditions");
		JSONObject paramJson = null;
		if (StringUtils.isNotEmpty(conditions)) {
			paramJson = JSONObject.fromObject(conditions);
		}
		if (StringUtils.isNotEmpty(extconditions)) {
			extconditions = DyFormBuildHtmlExt.getQueryCondition(extconditions);
		}
		User user = getOnlineUser(request);
		String naturalname = request.getParameter("naturalname");
		String start = request.getParameter("start");// ��ʼ����
		String limit = request.getParameter("limit");// ҳ��
		PageInfo obj = new PageInfo();
		try {
			AppObj app = AppEntry.iv().loadApp(naturalname);
			String formcode = app.getDyformCode_();

			Integer from_ = Integer.parseInt(start);
			Integer limit_ = Integer.parseInt(limit);
			Integer to_ = from_ + limit_ - 1;

			DyFormData dydata = new DyFormData();
			BeanUtils.copyProperties(dydata, paramJson);
			dydata.setFatherlsh("1");
			dydata.setFormcode(formcode);
			if (!"adminx".equals(user.getUserCode())) {
				dydata.setParticipant(user.getUserCode());
			}

			obj = (PageInfo) ins.queryForPage(dydata, from_, limit_,
					extconditions);
			int total = obj.getTotalRows();
			List result = obj.getResultList();
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				DyFormData group = (DyFormData) iterator.next();
				String lsh = group.getLsh();

				// String clientId = "";
				// if (group.getParticipant() != null
				// && StringUtils.isNotEmpty(group.getParticipant())) {
				// clientId = group.getParticipant();
				// }
				//
				// String runtimeid = WfEntry.iv().getSession(lsh);
				// if (runtimeid != null && StringUtils.isNotEmpty(runtimeid)) {
				//
				// TWfRuntime runtime = WfEntry.iv().useCoreView()
				// .loadRuntime(runtimeid);
				// // ���̽��� runtime.isDone()�Ѱ��
				// // �Ƿ��Ѿ��ύ���� runtime.isRunning()�Ѵ���
				//
				// if (runtime.isReady() == true) {
				// if (user.getUserCode().equals(clientId)) {
				// group.setStatus("<font color='red'>δ�ύ</font>");
				// group.setRun(false);// �༭
				// } else {
				// group.setStatus("<font color='red'>δ�ύ</font>");
				// group.setRun(true);// �鿴
				// }
				// }
				// if (runtime.isDone() == true) {
				// group.setStatus("<font color='#1285C9'>�Ѱ��</font>");
				// group.setRun(true);// �鿴
				// }
				// if (runtime.isRunning() == true) {
				// group.setStatus("<font color='green'>������</font>");
				// group.setRun(true);// �鿴
				// }
				// } else {
				// if (user.getUserCode().equals(clientId)) {
				// group.setStatus("<font color='red'>δ�ύ</font>");
				// group.setRun(false);// �༭
				// } else {
				// group.setStatus("<font color='red'>δ�ύ</font>");
				// group.setRun(true);// �鿴
				// }
				// }
				// group.setRuntimeid(runtimeid);

				String jsonStr = JSONUtil2.fromBean(group, "yyyy-MM-dd")
						.toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}
			super.writeJsonStr(response, super.buildJsonStr(total, jsonBuffer
					.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ��ʾ��ϸ
	public void onListdetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String naturalname = request.getParameter("naturalname");
		String fatherlsh = request.getParameter("fatherlsh");
		try {
			AppObj app = AppEntry.iv().loadApp(naturalname);
			String formcode = app.getDyformCode_();
			DyForm dyform = DyEntry.iv().loadForm(formcode);

			List list = new ArrayList();

			if (dyform.getSubform_() != null) {
				DyForm subdyform = dyform.getSubform_()[0];

				DyFormData dydata = new DyFormData();
				dydata.setFormcode(subdyform.getFormcode());
				dydata.setFatherlsh(fatherlsh);

				if (StringUtils.isNotEmpty(fatherlsh)) {
					list = DyEntry.iv().queryData(dydata, 0, 9999999, "");
				}
			}

			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				DyFormData group = (DyFormData) iterator.next();

				String jsonStr = JSONUtil2.fromBean(group, "yyyy-MM-dd")
						.toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}
			super.writeJsonStr(response, super.buildJsonStr(9999999, jsonBuffer
					.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ��ʾ��չ��ϸ�������Ϣ�б�
	public void onListExtdata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String naturalname = request.getParameter("naturalname");
		String conditions = request.getParameter("conditions");
		String extmode = request.getParameter("extmode");
		try {
			AppObj app = AppEntry.iv().loadApp(naturalname);
			String formcode = app.getDyformCode_();

			JSONArray jsonArray = JSONArray.fromObject(conditions);
			StringBuffer extconditions = new StringBuffer();
			if (jsonArray.size() > 0) {
				extconditions.append(" and lsh IN (");
				String split = "";
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					extconditions.append(split + "'" + obj.getString("lsh")
							+ "'");
					split = ",";
				}
				extconditions.append(")");
			}

			DyFormData dydata = new DyFormData();
			dydata.setFormcode(formcode);
			dydata.setFatherlsh("");
			List list = new ArrayList();
			list = DyEntry.iv().queryData(dydata, 0, 9999999,
					extconditions.toString());

			StringBuffer jsonBuffer = new StringBuffer();
			if ("1".equals(extmode)) {
				String split = "";
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					DyFormData group = (DyFormData) iterator.next();

					String jsonStr = JSONUtil2.fromBean(group, "yyyy-MM-dd")
							.toString();
					jsonBuffer.append(split);
					jsonBuffer.append(jsonStr);

					split = ",";
				}

			} else if ("2".equals(extmode)) {
				List<TCsColumn> listx = DyEntry.iv().QueryColumn(formcode, "3");
				TCsColumn[] extdyformx = (TCsColumn[]) listx
						.toArray(new TCsColumn[listx.size()]);
				Map<String, DyFormData> summaryMap = new HashMap<String, DyFormData>();

				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					DyFormData group = (DyFormData) iterator.next();
					StringBuffer key = new StringBuffer();
					for (TCsColumn csColumn : extdyformx) {
						String cid = csColumn.getColumnid();
						String value = BeanUtils.getProperty(group, cid);
						key.append("|^|" + value);
					}
					if (summaryMap.containsKey(key.toString())) {
						DyFormData data = summaryMap.get(key.toString());
						Integer sum = Integer.parseInt(data.getStatistical());
						data.setStatistical("" + (sum + 1));
						summaryMap.put(key.toString(), data);
					} else {
						group.setStatistical("1");
						summaryMap.put(key.toString(), group);
					}
				}
				String split = "";
				for (Iterator iterator = summaryMap.keySet().iterator(); iterator
						.hasNext();) {
					String key = (String) iterator.next();
					DyFormData data = summaryMap.get(key);

					String jsonStr = JSONUtil2.fromBean(data, "yyyy-MM-dd")
							.toString();
					jsonBuffer.append(split);
					jsonBuffer.append(jsonStr);

					split = ",";
				}
			}

			super.writeJsonStr(response, super.buildJsonStr(9999999, jsonBuffer
					.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Map<String, Boolean> permission(HttpServletRequest request,
			boolean isedit, boolean ispermission) throws Exception {
		String lsh = request.getParameter("lsh");
		String query = request.getParameter("query");
		String workcode = request.getParameter("workcode");
		String naturalname = request.getParameter("naturalname");
		if (StringUtils.isNotEmpty(workcode)) {
			isedit = false;
			TWfWorklist wlx = WfEntry.iv().loadWorklist(workcode);
			TWfActive active = WfEntry.iv().loadRuntimeActive(
					wlx.getProcessid(), wlx.getActivityid(), naturalname, "",
					wlx.getRuntimeid());

			boolean isFormEdit = active.isFormEdit();
			boolean isFirstAct = WfEntry.iv().checkFirstAct(workcode);
			if (isFormEdit) {
				ispermission = true;
				isedit = true;
			} else {
				isedit = false;
				ispermission = false;
			}
			if (isFirstAct) {// �˻ش����� �����޸ı�������
				ispermission = true;
				isedit = true;
				request.setAttribute("isFirstAct", isFirstAct);
			}
			request.setAttribute("isEditAct", isedit);
		} else {
			ispermission = true;
			isedit = true;
		}

		if (StringUtils.isEmpty(lsh)) {// �½�
			User user = getOnlineUser(request);
			// boolean permission = SecurityEntry.iv().permission(1
			// user.getUserCode(), naturalname);
			boolean permission = AppEntry.iv().canCreate(naturalname,
					user.getUserName() + "[" + user.getUserCode() + "]");

			request.setAttribute("permission", permission);
		} else {
			request.setAttribute("permission", true);
		}
		if ("look".equals(query)) {// �鿴
			isedit = false;
		}

		// ���ȷ��״̬ �鱦ҵ��
		if (StringUtils.isNotEmpty(lsh)) {
			AppObj app = AppEntry.iv().loadApp(naturalname);
			String formcode = app.getDyformCode_();
			DyFormData dydata = DyEntry.iv().loadData(formcode, lsh);
			if ("01".equals(dydata.getStatusinfo())) {
				isedit = false;
			}
		}

		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("isedit", isedit);
		map.put("ispermission", ispermission);
		return map;
	}

	public ActionForward onEditViewMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String naturalname = request.getParameter("naturalname");
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		request.setAttribute("htmltitleinfo", dyform.getHtmltitleinfo_());
		request.setAttribute("htmlendinfo", dyform.getHtmlendinfo_());
		String workcode = request.getParameter("workcode");
		String query = request.getParameter("query");
		String lsh = request.getParameter("lsh");
		loadNavInfo(request);
		boolean isedit = true;
		boolean ispermission = true;// �Ƿ����ü�Ȩ
		Map<String, Boolean> pmap = permission(request, isedit, ispermission);
		isedit = pmap.get("isedit");
		ispermission = pmap.get("ispermission");

		load(mapping, form, request, response, isedit, ispermission, false);
		return mapping.findForward("onEditView");
	}
	
	//��ӡ
	public ActionForward onPrintViewMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String naturalname = request.getParameter("naturalname");
		String excel = request.getParameter("excel");
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		request.setAttribute("htmltitleinfo", dyform.getHtmltitleinfo_());
		request.setAttribute("htmlendinfo", dyform.getHtmlendinfo_());
		String workcode = request.getParameter("workcode");
		String query = request.getParameter("query");
		String lsh = request.getParameter("lsh");
		loadNavInfo(request);
		boolean isedit = true;
		boolean ispermission = true;// �Ƿ����ü�Ȩ
		Map<String, Boolean> pmap = permission(request, isedit, ispermission);
		isedit = pmap.get("isedit");
		ispermission = pmap.get("ispermission");

		load(mapping, form, request, response, isedit, ispermission, true);
		
		if ("1".equals(excel)){
			return mapping.findForward("onPrintExcelView");
		} else {
			return mapping.findForward("onPrintView");
		}
	}
	
	// ����Ԥ��
	public ActionForward onPreviewMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String formcode = request.getParameter("formcode");
		String $isedit = request.getParameter("isedit");
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		request.setAttribute("htmltitleinfo", dyform.getHtmltitleinfo_());
		boolean isedit = false;
		if ("1".equals($isedit))
			isedit = true;

		Map issubmap = new HashMap();
		issubmap.put(-1, isedit);
		loadDyForm(mapping, form, request, response, isedit, issubmap);
		return mapping.findForward("onPreviewMain");
	}

	private void loadNavInfo(HttpServletRequest request) throws Exception {
		String workcode = request.getParameter("workcode");
		String naturalname = request.getParameter("naturalname");
		String cuibang = request.getParameter("cuibang");
		if ("true".equals(cuibang)) {
			User user = getOnlineUser(request);
			request.setAttribute("curruser", user.getUserCode());
		}
		if (StringUtils.isEmpty(workcode))
			workcode = "";
		String activityName = WfEntry.iv().getActivityName(naturalname,
				workcode);
		if (StringUtils.isNotEmpty(workcode)) {
			TWfActive twfActive = WfEntry.iv()
					.loadActive(naturalname, workcode);
			request.setAttribute("isFobitzb", twfActive.isFobitzb());
		} else {
			request.setAttribute("isFobitzb", true);
		}

		request.setAttribute("activityName", activityName);
	}

	// ����
	private void load(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			boolean isedit, boolean permission, boolean isprint)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String lsh = request.getParameter("lsh");
		String workcode = request.getParameter("workcode");
		String naturalname = request.getParameter("naturalname");
		Map groupMap = new HashMap();
		try {
			AppObj app = AppEntry.iv().loadApp(naturalname);
			String formcode = app.getDyformCode_();
			String processid = app.getWorkflowCode_();
			User user = getOnlineUser(request);// ��ȡ��ǰ�û�
			String formhtml = "";
			DyForm dyform = DyEntry.iv().loadForm(formcode);
			if (permission) {

				TWfActive act = WfEntry.iv().listCurrentActive(naturalname,
						workcode, user.getUserCode());
				DyEntry.iv().permission(dyform, user.getUserCode(), act);// ������Ȩ
			}

			TWfActive act = WfEntry.iv().listCurrentActive(naturalname,
					workcode, user.getUserCode());
			Map subformmode = act.getSubformmode();
			// if (StringUtils.isNotEmpty(lsh) &&
			// StringUtils.isNotEmpty(formcode)) {
			formhtml = ins.load(workcode, naturalname, dyform, lsh, isedit,
					subformmode, user.getUserName(), request
							.getParameter("url"), user, isprint);
			// }
			request.setAttribute("form", formhtml.toString());

			String linkcss = DyFormComp.getStyle(getURL(dyform
					.getStyleinfourl_()));
			request.setAttribute("linkcss", linkcss);
			request.setAttribute("AvailWidth", DyFormBuildHtmlExt.AvailWidth
					- DyFormBuildHtmlExt.AvailWidthCorrect);
			if (StringUtils.isNotEmpty(workcode)) {
				TWfWorklist twf = WfEntry.iv().loadWorklist(workcode);
				request.setAttribute("runtimeid", twf.getRuntimeid());

			} else {
				request.setAttribute("processid", processid);
			}
			request.setAttribute("ValidateScript", DyFormBuildHtmlExt
					.buildValidateScript(dyform));
			request
					.setAttribute("datecompFunc", DyFormComp
							.getInitFuncScript());
			request.setAttribute("formcode", formcode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ����
	private void loadDyForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			boolean isedit, Map issubedit) throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String formcode = request.getParameter("formcode");
		Map groupMap = new HashMap();
		try {
			User user = getOnlineUser(request);// ��ȡ��ǰ�û�
			String formhtml = "";
			DyForm dyform = DyEntry.iv().loadForm(formcode);

			// if (StringUtils.isNotEmpty(lsh) &&
			// StringUtils.isNotEmpty(formcode)) {
			formhtml = ins.load("", "", dyform, "", isedit, issubedit, user
					.getNLevelName()
					+ "/" + user.getUserName() + "," + user.getNLevelName(),
					request.getParameter("url"), user, false);
			// }
			request.setAttribute("form", formhtml.toString());

			String linkcss = DyFormComp.getStyle(getURL(dyform
					.getStyleinfourl_()));
			request.setAttribute("linkcss", linkcss);
			request.setAttribute("AvailWidth", DyFormBuildHtmlExt.AvailWidth
					- DyFormBuildHtmlExt.AvailWidthCorrect);
			request.setAttribute("ValidateScript", DyFormBuildHtmlExt
					.buildValidateScript(dyform));
			request
					.setAttribute("datecompFunc", DyFormComp
							.getInitFuncScript());
			request.setAttribute("formcode", formcode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	public ActionForward onEditView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workcode = request.getParameter("workcode");
		boolean isedit = true;
		if (StringUtils.isNotEmpty(workcode)) {
			isedit = false;
		}
		load(mapping, form, request, response, isedit, false, false);
		return mapping.findForward("onEditView");
	}

	public ActionForward onQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		load(mapping, form, request, response, false, false, false);
		return mapping.findForward("onEditView");
	}

	// ����
	public ActionForward onSavaOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String naturalname = request.getParameter("naturalname");
		request.setAttribute("ErrorJson", "Yes");// Json������ʾ
		JSONObject json = new JSONObject();
		DyFormData mainform = this.getBean(request);
		String subform = request.getParameter("subform");
		try {
			User user = getOnlineUser(request);
			AppObj app = AppEntry.iv().loadApp(naturalname);
			String formcode = app.getDyformCode_();
			String jsonx = ins.saveAndUpdate(request, user.getUserName() + "["
					+ user.getUserCode() + "]", formcode, mainform, subform,
					"1");
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "������Ϣʧ��!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
		return null;
	}

	// ����ɾ��
	public void onDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		request.setAttribute("ErrorJson", "Yes");// Json������ʾ
		JSONObject json = new JSONObject();
		String naturalname = request.getParameter("naturalname");
		String lsh = request.getParameter("lsh");
		try {
			AppObj app = AppEntry.iv().loadApp(naturalname);
			String formcode = app.getDyformCode_();
			String jsonx = "";
			jsonx = ins.delete(request, formcode, lsh);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "����ʧ��!");
			json.put("error", "yes");
			log.error("����", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// �߼�ɾ��
	public void onLogicDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		request.setAttribute("ErrorJson", "Yes");// Json������ʾ
		JSONObject json = new JSONObject();
		String naturalname = request.getParameter("naturalname");
		String lsh = request.getParameter("lsh");
		String workcode = request.getParameter("workcode");
		try {
			AppObj app = AppEntry.iv().loadApp(naturalname);
			String formcode = app.getDyformCode_();
			String jsonx = "";
			if (StringUtils.isNotEmpty(workcode)) {// ����workcode�߼�ɾ��
				boolean isFirstAct = WfEntry.iv().checkFirstAct(workcode);
				if (!isFirstAct) {
					json.put("error", "yes");
					json.put("tip", "���̲��ڷ���㣬��������!");
				} else {
					jsonx = ins.deleteByLogic(request, formcode, lsh);
					json = JSONObject.fromObject(jsonx);
				}
			}
		} catch (Exception e) {
			json.put("tip", "����ʧ��!");
			json.put("error", "yes");
			log.error("����", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	/**
	 * �ͻ�ѡ��ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward onEditViewClient(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		User user = BaseService.getOnlineUser(request);// ��ȡ��ǰ�û�
		request.setAttribute("onlineName", user.getUserName());
		return mapping.findForward("onEditViewClient");
	}

	/**
	 * ��ȡ���Žڵ�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void onGetWfNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String lsh = request.getParameter("lsh");
		String runtimeid = request.getParameter("runtimeid");
		String naturalname = request.getParameter("naturalname");
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isNotEmpty(lsh)) {
				User user = getOnlineUser(request);
				AppObj app = AppEntry.iv().loadApp(naturalname);
				Map map = ins.newWfNode(request, naturalname, app
						.getWorkflowCode_(), runtimeid, user, lsh, user
						.getUserName()
						+ "[" + user.getUserCode() + "]");
				json = JSONObject.fromObject(map);
			}
		} catch (Exception e) {
			json.put("tip", "ʧ��!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// �½�����
	public void onNewEnd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String workcode = request.getParameter("workcode");
		String userCodes = request.getParameter("usercode");
		String userNames = request.getParameter("username");
		String activityid = request.getParameter("activityid");
		String limittimes = request.getParameter("limittime");
		String processlen = request.getParameter("processlen");
		String operatemode = request.getParameter("operatemode");
		String issync_ = request.getParameter("issync");
		JSONObject json = new JSONObject();
		String notice = request.getParameter("notice");
		String naturalname = request.getParameter("naturalname");
		String lsh = request.getParameter("lsh");
		String filteractiveids = request.getParameter("filteractiveids");
		try {
			User user = getOnlineUser(request);
			boolean issync = false;
			if (StringUtils.isNotEmpty(issync_)) {
				if ("1".equals(issync_)) {
					issync = true;
				} else if ("0".equals(issync_)) {
					issync = false;
				}
			}
			AppObj app = AppEntry.iv().loadApp(naturalname);
			String formcode = app.getDyformCode_();
			String jsonx = ins.newEnd(request, naturalname, formcode + lsh,
					activityid, filteractiveids, workcode, user.getUserName()
							+ "[" + user.getUserCode() + "]", userCodes,
					userNames, limittimes, processlen, issync, operatemode);
			json = JSONObject.fromObject(jsonx);
			// ���Ͷ���
			if (StringUtils.isNotEmpty(notice)) {
				if ("0".equals(notice) && !json.containsKey("error")) {// 0����1������
					String[] userCode_ = userCodes.split(",");
					for (int i = 0; i < userCode_.length; i++) {
						WfEntry.iv().notice(user.getUserCode(), userCode_[i],
								"", workcode, lsh, naturalname);
					}
				}
			}

			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "ʧ��!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// �½�����
	public void onAuditEnd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String workcode = request.getParameter("workcode");
		String userCodes = request.getParameter("usercode");
		String userNames = request.getParameter("username");
		String limittimes = request.getParameter("limittime");
		String activityid = request.getParameter("activityid");
		String issync_ = request.getParameter("issync");
		JSONObject json = new JSONObject();
		String notice = request.getParameter("notice");
		String naturalname = request.getParameter("naturalname");
		String lsh = request.getParameter("lsh");
		String[] userCode_ = userCodes.split(",");
		try {
			User user = getOnlineUser(request);
			boolean issync = false;
			if (StringUtils.isNotEmpty(issync_)) {
				if ("1".equals(issync_)) {
					issync = true;
				} else if ("0".equals(issync_)) {
					issync = false;
				}
			}
			String jsonx = ins.auditEnd(request, naturalname, activityid,
					workcode, user.getUserName() + "[" + user.getUserCode()
							+ "]", userCodes, userNames, limittimes, issync);
			json = JSONObject.fromObject(jsonx);

			// ���Ͷ���
			if (StringUtils.isNotEmpty(notice)) {
				if ("0".equals(notice) && !json.containsKey("error")) {// 0����1������
					for (int i = 0; i < userCode_.length; i++) {
						WfEntry.iv().notice(user.getUserCode(), userCode_[i],
								"", workcode, lsh, naturalname);
					}
				}
			}

		} catch (Exception e) {
			json.put("tip", "ʧ��!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// ת�����
	public void onAssignEnd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String workcode = request.getParameter("workcode");
		String userCodes = request.getParameter("usercode");
		String userNames = request.getParameter("username");
		String activityid = request.getParameter("activityid");
		String limittimes = request.getParameter("limittime");
		String processlen = request.getParameter("processlen");
		String operatemode = request.getParameter("operatemode");
		String issync_ = request.getParameter("issync");
		JSONObject json = new JSONObject();
		String notice = request.getParameter("notice");
		String naturalname = request.getParameter("naturalname");
		String lsh = request.getParameter("lsh");
		String filteractiveids = request.getParameter("filteractiveids");
		try {
			User user = getOnlineUser(request);
			String jsonx = ins.assignEnd(request, naturalname, activityid,
					workcode, user.getUserName() + "[" + user.getUserCode()
							+ "]", userCodes, userNames);
			json = JSONObject.fromObject(jsonx);
			// ���Ͷ���
			if (StringUtils.isNotEmpty(notice)) {
				if ("0".equals(notice) && !json.containsKey("error")) {// 0����1������
					String[] userCode_ = userCodes.split(",");
					for (int i = 0; i < userCode_.length; i++) {
						WfEntry.iv().notice(user.getUserCode(), userCode_[i],
								"", workcode, lsh, naturalname);
					}
				}
			}
		} catch (Exception e) {
			json.put("tip", "ʧ��!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// �������
	public void onSaveYijian(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String workcode = request.getParameter("workcode");
		String yijian = request.getParameter("yijian");
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isNotEmpty(yijian)) {
				yijian = yijian.trim();
			}
			User user = getOnlineUser(request);
			String jsonx = ins.saveYijian(request, workcode,
					user.getUserCode(), yijian);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "ʧ��!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// �������
	public void onLoadYijian(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workcode = request.getParameter("workcode");
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isNotEmpty(workcode)) {
				workcode = workcode.trim();
			}
			// ����WORKCODE ��ȡ��� �������
			User user = getOnlineUser(request);
			// ����WORKCODE ��ȡ��� �������
			TWfParticipant worklist = WfEntry.iv().loadParticipantinfo(
					workcode, user.getUserCode());
			String jsonx = "";
			if (worklist != null) {
				jsonx = worklist.getAuditnode();
			}

			json.put("yinjian", jsonx);
		} catch (Exception e) {
			json.put("tip", "ʧ��!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	/**
	 * ���ҳ�� ͨ�������͡��˰�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward onAuditView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		loadNavInfo(request);
		String map = show(request, false);
		request.setAttribute("permission", true);
		/** ��ʽ */
		String naturalname = request.getParameter("naturalname");
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		String linkcss = DyFormComp.getStyle(getURL(dyform.getStyleinfourl_()));
		request.setAttribute("linkcss", linkcss);
		request.setAttribute("htmltitleinfo", dyform.getHtmltitleinfo_());
		return mapping.findForward("onAuditView");
	}

	// ȷ��״̬
	public void onConfirmStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("ErrorJson", "Yes");// Json������ʾ
		JSONObject json = new JSONObject();
		String naturalname = request.getParameter("naturalname");
		String lsh = request.getParameter("lsh");
		try {
			AppObj app = AppEntry.iv().loadApp(naturalname);
			String formcode = app.getDyformCode_();

			DyFormData data = new DyFormData();
			data.setLsh(lsh);
			data.setStatusinfo("01");
			boolean succ = DyEntry.iv().modifyData(formcode, data);
			if (succ) {
				json.put("tip", "ȷ�ϳɹ�!");
			} else {
				json.put("tip", "ȷ��ʧ��!");
				json.put("error", "yes");
			}
		} catch (Exception e) {
			json.put("tip", "ȷ��ʧ��!");
			json.put("error", "yes");
			log.error("����", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// ��ȷ��״̬
	public void onUnConfirmStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("ErrorJson", "Yes");// Json������ʾ
		JSONObject json = new JSONObject();
		String naturalname = request.getParameter("naturalname");
		String lsh = request.getParameter("lsh");
		try {
			AppObj app = AppEntry.iv().loadApp(naturalname);
			String formcode = app.getDyformCode_();

			DyFormData data = new DyFormData();
			data.setLsh(lsh);
			data.setStatusinfo("00");
			boolean succ = DyEntry.iv().modifyData(formcode, data);
			if (succ) {
				json.put("tip", "��ȷ�ϳɹ�!");
			} else {
				json.put("tip", "��ȷ��ʧ��!");
				json.put("error", "yes");
			}
		} catch (Exception e) {
			json.put("tip", "��ȷ��ʧ��!");
			json.put("error", "yes");
			log.error("����", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	private DyFormData getBean(HttpServletRequest request) {
		DyFormData data = new DyFormData();
		super.setJavaBean(request, data);
		return data;
	}

	public ActionForward onShowView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String map = show(request, true);
		return mapping.findForward(map);
	}

	/**
	 * ��ʾ���̽ڵ�
	 * 
	 * @param request
	 * @param isShowSpecialNode
	 *            �Ƿ���ʾ���ͻ���
	 * @return
	 * @throws Exception
	 */
	private String show(HttpServletRequest request, boolean isShowSpecialNode)
			throws Exception {
		request.setAttribute("permission", true);
		String chooseFlag = request.getParameter("chooseresult");
		String workcode = request.getParameter("workcode");
		String operatemode = request.getParameter("operatemode");
		String naturalname = request.getParameter("naturalname");
		String commiter = request.getParameter("commiter");
		String filteractiveids = request.getParameter("filteractiveids");// ���˺�Ľڵ�

		TWfWorklist bean = (TWfWorklist) WfEntry.iv().loadWorklist(workcode);
		String activityid = bean.getActivityid();
		String runtimeid = bean.getRuntimeid();// ����ʵ��id
		List<Map> result = new ArrayList();

		User user = getOnlineUser(request);
		String node = user.getDepartmentId();
		request.setAttribute("isAndBranchMode", false);
		if ("03".equals(operatemode)) {// ����
			request.setAttribute("helpTip", "������ʾ: ������,������ϡ�");
			request.setAttribute("processEndTip", "������ϡ�");
			request.setAttribute("processTitle", "�������");
			result.addAll(listTrackActionEnd("�������"));
			request.setAttribute("processList", result);
			return "onShowEndView";
		} else if (filteractiveids != null
				&& filteractiveids
						.contains(FrameService.trackActionSpecialTypeEND)) {// �鵵
			request.setAttribute("helpTip", "������ʾ: ���̽���,�������,�������̡�");
			request.setAttribute("processEndTip", "���̽�����");
			request.setAttribute("processTitle", "�鵵");
			result.addAll(listTrackActionEnd("�鵵"));
			request.setAttribute("processList", result);
			request.setAttribute("isend", "true");
			return "onShowEndView";
		}

		AppObj app = AppEntry.iv().loadApp(naturalname);
		String processid = app.getWorkflowCode_();

		String[] filteractiveids_ = null;
		if (StringUtils.isNotEmpty(filteractiveids)) {
			filteractiveids_ = filteractiveids.split(",");
		}
		boolean isNeedReader = false;
		boolean isNeedAssistant = false;

		/** ��ʽ */
		String formcode = app.getDyformCode_();
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		String linkcss = DyFormComp.getStyle(getURL(dyform.getStyleinfourl_()));
		request.setAttribute("linkcss", linkcss);
		request.setAttribute("htmltitleinfo", dyform.getHtmltitleinfo_());

		if (chooseFlag.equals("0")) {

			// ������е���һ���ڵ�
			List resultList = WfEntry.iv().listNextRouteActive(processid,
					activityid, runtimeid, user.getUserCode());
			if (resultList.size() > 0) {
				for (Iterator iterator = resultList.iterator(); iterator
						.hasNext();) {
					TWfActive object = (TWfActive) iterator.next();

					Map tempMap = new HashMap();

					if (object.isNeedReader() == true) {
						isNeedReader = object.isNeedReader();
					}
					if (object.isNeedAssistant() == true) {
						isNeedAssistant = object.isNeedAssistant();
					}

					tempMap.put("pmode", object.getParticipantmode());
					tempMap.put("autoroute", object.isAutoroute());
					tempMap.put("needsync", object.isNeedsync());
					tempMap.put("needsearch", object.isNeedsearch());
					tempMap.put("singleman", object.isSingleman());
					tempMap.put("needtree", object.isNeedTree());
					tempMap.put("name", object.getName());
					tempMap.put("type", object.getParticipantmode());
					String participant = object.getParticipant();
					participant = participant == null ? "" : participant;
					setWfUser(participant, tempMap);
					tempMap.put("value", "0");
					tempMap.put("activeids", object.getId());

					if (filteractiveids_ != null) {
						for (int i = 0; i < filteractiveids_.length; i++) {
							if (object.getId().equalsIgnoreCase(
									filteractiveids_[i])) {
								result.add(tempMap);
							}
						}
					} else {
						result.add(tempMap);
					}
				}
				// �жϴ��ڹ鵵��ť ��ʾ�鵵��ť
				TWfWorklist wlx = WfEntry.iv().loadWorklist(workcode);
				TWfActive active = WfEntry.iv().loadRuntimeActive(
						wlx.getProcessid(), wlx.getActivityid(), naturalname,
						"", wlx.getRuntimeid());
				boolean isfirst = WfEntry.iv().loadProcess(wlx.getProcessid())
						.getActivity(wlx.getActivityid()).isStartActivity();
				// �Ƿ��Ǵ�����
				if (AppHandleIfc._PARTICIPANT_MODE_CREATER.equals(active
						.getParticipantmode())
						&& StringUtils.isEmpty(filteractiveids) && !isfirst) {
					result.addAll(listTrackActionEnd("�鵵"));
				}
			} else {
				request.setAttribute("helpTip", "������ʾ: ���̽���,�������,�������̡�");
				request.setAttribute("processEndTip", "���̽�����");
				request.setAttribute("processTitle", "�鵵");
				result.addAll(listTrackActionEnd("�鵵"));
				request.setAttribute("processList", result);
				request.setAttribute("isend", "true");
				return "onShowEndView";
			}

			request.setAttribute("helpTip", "������ʾ����ѡ����һ�����̽ڵ㼰�����ýڵ���Ա��");
			if (result.size() > 1) {
				request.setAttribute("processTitle", "���̷�֧");
				// ��鵱ǰ�ڵ����һ����֧�Ƿ�Ϊͬ��ģʽ
				request.setAttribute("isAndBranchMode", WfEntry.iv()
						.isAndBranchMode(processid, activityid));
			} else {
				request.setAttribute("processTitle", "��Աѡ��");
			}
			if (isNeedAssistant && isShowSpecialNode) {
				result.addAll(listTrackAction1("0"));
			}
			if (isNeedReader && isShowSpecialNode) {
				result.addAll(listTrackAction2("0"));
			}
			// �жϷֲ�ʽ�ύ
			TWfWorklist wlx = WfEntry.iv().loadWorklist(workcode);
			TWfActive active = WfEntry.iv().loadRuntimeActive(
					wlx.getProcessid(), wlx.getActivityid(), naturalname, "",
					wlx.getRuntimeid());
			// 1 start
			if (active.isSyncto()) {
				if ((filteractiveids == null || "".equals(filteractiveids) || FrameService.trackActionSpecialType3
						.equals(filteractiveids))
						&& !WfEntry.iv().checkFirstAct(workcode)) {
					if (StringUtils.isNotEmpty(filteractiveids)) {
						result.addAll(listTrackAction3_2("3"));// �ֲ�ʽ�Ķ�
					} else {
						result.addAll(listTrackAction3("3"));// �ֲ�ʽ�Ķ�
					}

				}
			}
			// 1 end

			request.setAttribute("processList", result);
			return "onShowView";
		} else if (chooseFlag.equals("1")) {
			// /////////////������п��˰�ڵ�//////////////////
			List processList = WfEntry.iv().listNextBackActive(runtimeid);
			for (Iterator iterator = processList.iterator(); iterator.hasNext();) {
				TWfActivePass object = (TWfActivePass) iterator.next();
				if (object.isNeedReader() == true) {
					isNeedReader = object.isNeedReader();
				}
				if (object.isNeedAssistant() == true) {
					isNeedAssistant = object.isNeedAssistant();
				}
				Map tempMap = new HashMap();
				tempMap.put("autoroute", object.isAutoroute());
				tempMap.put("needsync", object.isNeedsync());
				tempMap.put("singleman", object.isSingleman());
				tempMap.put("needtree", object.isNeedTree());
				tempMap.put("needsearch", object.isNeedsearch());
				tempMap.put("name", object.getName());
				tempMap.put("type", object.getParticipantmode());
				tempMap.put("pmode", AppHandleIfc._PARTICIPANT_MODE_HUMAN);
				if (true) {// �˰�
					tempMap.put("value", "0");

					String[] humens = object.getParticipantOld();
					String[] assistants = object.getAssistant();
					String[] readers = object.getReader();
					StringBuffer usercodeSB = new StringBuffer();
					StringBuffer usernameSB = new StringBuffer();

					for (int i = 0; i < humens.length; i++) {
						usercodeSB.append(StringUtils.substringBetween(
								humens[i], "[", "]")
								+ ",");
						usernameSB.append(StringUtils.substringBefore(
								humens[i], "[")
								+ ",");
					}

					for (int i = 0; i < assistants.length; i++) {
						usercodeSB.append(StringUtils.substringBetween(
								assistants[i], "[", "]")
								+ ",");
						usernameSB.append(StringUtils.substringBefore(
								assistants[i], "[")
								+ ",");
					}

					for (int i = 0; i < readers.length; i++) {
						usercodeSB.append(StringUtils.substringBetween(
								readers[i], "[", "]")
								+ ",");
						usernameSB.append(StringUtils.substringBefore(
								readers[i], "[")
								+ ",");
					}

					tempMap
							.put("usercode", StringUtils.substring(usercodeSB
									.toString(), 0, usercodeSB.toString()
									.length() - 1));
					tempMap
							.put("username", StringUtils.substring(usernameSB
									.toString(), 0, usernameSB.toString()
									.length() - 1));

				}
				tempMap.put("activeids", object.getId());
				if (filteractiveids_ != null) {
					for (int i = 0; i < filteractiveids_.length; i++) {
						if (object.getId()
								.equalsIgnoreCase(filteractiveids_[i])) {
							result.add(tempMap);
						}
					}
				} else {
					result.add(tempMap);
				}
			}
			if (isNeedAssistant && isShowSpecialNode) {
				result.addAll(listTrackAction1("0"));
			}
			if (isNeedReader && isShowSpecialNode) {
				result.addAll(listTrackAction2("0"));
			}
			request.setAttribute("helpTip",
					"������ʾ��������Ҫ�ǽ������˻ص�ָ���㣬ȷ����������ɡ�������޸ĵ����һ������ȡ����");
			request.setAttribute("processTitle", "�˰�");
			request.setAttribute("processList", result);
			return "onShowView";
		} else if (chooseFlag.equals("2")) {
			// // /////////////����ذ�ڵ�//////////////////
			// processList = WfEntry.iv().listNextJumpActive(processid,
			// runtimeid);
			//
			// for (Iterator iterator = processList.iterator();
			// iterator.hasNext();) {
			// TWfActive object = (TWfActive) iterator.next();
			//
			// Map tempMap = new HashMap();
			// tempMap.put("autoroute", object.isAutoroute());
			// tempMap.put("needsync", object.isNeedsync());
			// tempMap.put("singleman", object.isSingleman());
			// tempMap.put("name", object.getName());
			// tempMap.put("type", object.getParticipantmode());
			// tempMap.put("pmode", object.getParticipantmode());
			// String participant = object.getParticipant();
			// participant = participant == null ? "" : participant;
			// setWfUser(participant, tempMap);
			// tempMap.put("value", "0");
			// tempMap.put("activeids", object.getId());
			// result.add(tempMap);
			// }
			//
			// // result.addAll(listTrackAction(node));
			// request.setAttribute("helpTip",
			// "������ʾ��������Ҫ�ǽ��������͵�ָ���㣬ȷ����������ɡ�������޸ĵ����һ������ȡ����");
			// request.setAttribute("processTitle", "����");
			// request.setAttribute("processList", result);
			// return mapping.findForward("onShowView");
			return "onShowView";
		} else if (chooseFlag.equals("3")) {// ����
			TWfActive object = WfEntry.iv().listNextZhuanbangActive(workcode,
					commiter);

			Map tempMap = new HashMap();

			if (object.isNeedReader() == true) {
				isNeedReader = object.isNeedReader();
			}
			if (object.isNeedAssistant() == true) {
				isNeedAssistant = object.isNeedAssistant();
			}

			tempMap.put("pmode", object.getParticipantmode());
			tempMap.put("autoroute", object.isAutoroute());
			tempMap.put("needsync", object.isNeedsync());
			tempMap.put("singleman", object.isSingleman());
			tempMap.put("needtree", object.isNeedTree());
			tempMap.put("name", object.getName());
			tempMap.put("needsearch", object.isNeedsearch());
			tempMap.put("type", object.getParticipantmode());
			String participant = object.getParticipant();
			participant = participant == null ? "" : participant;
			setWfUser(participant, tempMap);
			tempMap.put("value", "0");
			tempMap.put("activeids", object.getId());

			if (filteractiveids_ != null) {
				for (int i = 0; i < filteractiveids_.length; i++) {
					if (object.getId().equalsIgnoreCase(filteractiveids_[i])) {
						result.add(tempMap);
					}
				}
			} else {
				result.add(tempMap);
			}
			request.setAttribute("helpTip", "������ʾ����ѡ����һ�����̽ڵ㼰�����ýڵ���Ա��");
			request.setAttribute("processTitle", "��Աѡ��");
			request.setAttribute("processList", result);
			return "onShowView";
		}
		return null;
	}

	// ϵͳ�Զ�����ҳ��
	public ActionForward onAutorouteView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("onAutorouteView");
	}

	// ��ѡ
	public ActionForward onMultiSelectResource(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		selectrec(request);
		return mapping.findForward("onMultiSelectResource");
	}

	// ��ѡ
	public ActionForward onSingleSelectResource(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		selectrec(request);
		return mapping.findForward("onSingleSelectResource");
	}

	private void selectrec(HttpServletRequest request) throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String naturalname = request.getParameter("naturalname");
		String node = request.getParameter("node");
		ResourceNode recnode = ins.getResourceNode(naturalname, node);
		request.setAttribute("node_", recnode.getNode_());
		request.setAttribute("nodeid", recnode.getNodeid());
		request.setAttribute("nodecode", recnode.getNodecode());
		request.setAttribute("nodename", recnode.getNodename());
		request.setAttribute("parentnode", recnode.getParentnode());
	}

	// ������Դ��
	public void onFindResourceTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String node = request.getParameter("node");
		String str = ins.findResourceTree(node);
		super.writeJsonStr(response, str);
	}

	// ������Դ���ڵ�
	public void onListResourceNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String condition = request.getParameter("condition");
		JSONObject paramJson = null;
		if (StringUtils.isNotEmpty(condition)) {
			paramJson = JSONObject.fromObject(condition);
		}
		String node = paramJson.getString("node");
		String type = paramJson.getString("type");
		String conditions = "";
		if (StringUtils.isNotEmpty(conditions)) {
			conditions = paramJson.getString("conditions");
		}
		String condtition = "";
		if (paramJson.containsKey("query")) {
			condtition = paramJson.getString("query");
			if (StringUtils.isNotEmpty(condtition)) {
				condtition = condtition.trim();
			} else {
				condtition = "";
			}
		}
		String start = request.getParameter("start");// ��ʼ����
		String limit = request.getParameter("limit");// ҳ��
		PageInfo pageinfo = ins.findResourceNodeInfo(type, node, condtition,
				start, limit);

		StringBuffer jsonBuffer = new StringBuffer();
		String split = "";
		for (Iterator iterator = pageinfo.getResultList().iterator(); iterator
				.hasNext();) {
			Resource rec = (Resource) iterator.next();

			String jsonStr = JSONUtil2.fromBean(rec, "yyyy-MM-dd").toString();
			jsonBuffer.append(split);
			jsonBuffer.append(jsonStr);
			split = ",";
		}
		super.writeJsonStr(response, super.buildJsonStr(
				pageinfo.getTotalRows(), jsonBuffer.toString()));
	}

	/**
	 * ��ʾ����ڵ�
	 * 
	 * @param node
	 * @return
	 */
	private List listTrackAction1(String node) {
		List list = new ArrayList();
		Map tempMap = new HashMap();
		tempMap.put("name", "����");
		tempMap.put("type", AppHandleIfc._PARTICIPANT_MODE_DEPT);
		tempMap.put("pmode", AppHandleIfc._PARTICIPANT_MODE_DEPT);
		tempMap.put("needsync", false);
		tempMap.put("value", node);
		tempMap.put("singleman", false);
		tempMap.put("autoroute", false);
		tempMap.put("activeids", FrameService.trackActionSpecialType1);
		list.add(tempMap);
		return list;
	}

	/**
	 * ��ʾ����ڵ�
	 * 
	 * @param node
	 * @return
	 */
	private List listTrackAction2(String node) {
		List list = new ArrayList();
		Map tempMap = new HashMap();
		tempMap.put("name", "����");
		tempMap.put("type", AppHandleIfc._PARTICIPANT_MODE_DEPT);
		tempMap.put("pmode", AppHandleIfc._PARTICIPANT_MODE_DEPT);
		tempMap.put("needsync", false);
		tempMap.put("value", node);
		tempMap.put("singleman", false);
		tempMap.put("autoroute", false);
		tempMap.put("activeids", FrameService.trackActionSpecialType2);
		list.add(tempMap);
		return list;
	}

	/**
	 * ��ʾ����ڵ�
	 * 
	 * @param node
	 * @return
	 */
	private List listTrackAction3(String node) {
		List list = new ArrayList();
		Map tempMap = new HashMap();
		tempMap.put("name", "�׶��Իظ�");
		tempMap.put("type", AppHandleIfc._PARTICIPANT_MODE_DEPT);
		tempMap.put("pmode", AppHandleIfc._PARTICIPANT_MODE_DEPT);
		tempMap.put("needsync", false);
		tempMap.put("value", node);
		tempMap.put("singleman", false);
		tempMap.put("autoroute", false);
		tempMap.put("activeids", FrameService.trackActionSpecialType3);
		list.add(tempMap);
		return list;
	}

	private List listTrackAction3_2(String node) {
		List list = new ArrayList();
		Map tempMap = new HashMap();
		tempMap.put("name", "�׶��Իظ�");
		tempMap.put("type", AppHandleIfc._PARTICIPANT_MODE_DEPT);
		tempMap.put("pmode", AppHandleIfc._PARTICIPANT_MODE_DEPT);
		tempMap.put("needsync", false);
		tempMap.put("value", node);
		tempMap.put("singleman", false);
		tempMap.put("autoroute", false);
		tempMap.put("activeids", FrameService.trackActionSpecialType3);
		list.add(tempMap);
		return list;
	}

	/**
	 * ��ʾ����ڵ�
	 * 
	 * @param node
	 * @return
	 */
	private List listTrackActionEnd(String name) {
		List list = new ArrayList();
		Map tempMap = new HashMap();
		tempMap.put("name", name);
		tempMap.put("type", "");
		tempMap.put("pmode", "");
		tempMap.put("needsync", false);
		tempMap.put("value", "0");
		tempMap.put("singleman", false);
		tempMap.put("autoroute", false);
		tempMap.put("activeids", FrameService.trackActionSpecialTypeEND);
		list.add(tempMap);
		return list;
	}

	public static void setWfUser(String[] humens, Map tempMap) {
		StringBuffer usercodeSB = new StringBuffer();
		StringBuffer usernameSB = new StringBuffer();

		for (int i = 0; i < humens.length; i++) {
			usercodeSB.append(StringUtils.substringBetween(humens[i], "[", "]")
					+ ",");
			usernameSB
					.append(StringUtils.substringBefore(humens[i], "[") + ",");
		}

		if (humens.length > 0) {
			tempMap.put("usercode", StringUtils.substring(
					usercodeSB.toString(), 0,
					usercodeSB.toString().length() - 1));
			tempMap.put("username", StringUtils.substring(
					usernameSB.toString(), 0,
					usernameSB.toString().length() - 1));
		}
	}

	public static void setWfUser(String humenstrs, Map tempMap) {
		if (StringUtils.isNotEmpty(humenstrs)) {
			String[] humens = humenstrs.split(",");
			setWfUser(humens, tempMap);
		}
	}

	private String[] getUsers_(List<Client3A> list) {
		String split = "";
		StringBuffer sb = new StringBuffer();
		for (Client3A client3A : list) {
			String usercode = client3A.getClientId();
			String username = client3A.getName();

			sb.append(split + username + "[" + usercode + "]");
			split = ",";
		}
		return sb.toString().split(",");
	}

	public void bussTipListView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String workcode = request.getParameter("workcode");
		String runtimeid = request.getParameter("runtimeid");
		String string = "";

		try {
			if (StringUtils.isEmpty(workcode)) {
				if (StringUtils.isEmpty(runtimeid)) {
					return;
				}
			} else {
				runtimeid = WfEntry.iv().getRuntimeIdByWorkcode(workcode);
			}
			List list = WfEntry.iv().listAllParticipantinfo(runtimeid);

			if (list.size() != 0) {
				// TWfWorklistExt o = (TWfWorklistExt) list.get(0);
				// if(StringUtils.isNotEmpty(o.getCustomer()))
				// {

				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					TWfParticipant object = (TWfParticipant) iterator.next();

					String donetime = StringUtils.substringBefore(object
							.getDonetime(), ".");
					if ("04".equals(object.getTypes())) {// �׶��Իظ�
						donetime = StringUtils.substringBefore(object
								.getCreatetime(), ".");
					}

					if (StringUtils.isNotEmpty(object.getAuditnode())) {
						string += "<div style=\"padding:10px\">"
								+ object.getAuditnode()
								+ "</div><div style=\"text-align:center;border-bottom:1px solid #58b4f0;\"><B>"
								+ object.getUsername() + "</B>  " + donetime
								+ "</div>";
					}
				}
				// }
			}

			if (StringUtils.isEmpty(string)) {
				string = "<div style=\"padding:10px\">��</div>";
			}
			// System.out.println("<div style=\"padding:20px\">��</div><div
			// id=\"whodate\" style=\"text-align:center\"></div>");
			response.setContentType("text/json;charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.write(string);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void queryActive(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String workcode = request.getParameter("workcode");
			String runtimeid = request.getParameter("runtimeid");
			if (StringUtils.isNotEmpty(workcode)) {
				runtimeid = WfEntry.iv().getRuntimeIdByWorkcode(workcode);
			}
			List list = WfEntry.iv().listAllParticipantinfo(runtimeid);

			String title = "<tr><td nowrap='nowrap' class='table_td_title' width='20%'>�ύ����</td><td nowrap='nowrap' class='table_td_title' width='20%'>�ύ��</td><td nowrap='nowrap' class='table_td_title' width='40%'>���̽ڵ�</td><td nowrap='nowrap' class='table_td_title' width='20%'>ִ����</td><td nowrap='nowrap' class='table_td_title' width='20%'>���ʱ��</td></tr>";

			String showHtmlView = "<table  width=\"100%\" border=1>" + title;

			for (int i = 0; i < list.size(); i++) {
				TWfParticipant object = (TWfParticipant) list.get(i);
				String opemode = "";
				if ("02".equals(object.getTypes())) {
					opemode = "[����]";
				}
				if ("03".equals(object.getTypes())) {
					opemode = "[����]";
				}
				if ("04".equals(object.getTypes())) {
					opemode = "[�׶��Իظ�]";
				}
				String sync = object.isSync() ? "[ͬ��]" : "";

				String opemodeX = "";
				if ("01".equals(object.getOpemode())) {
					opemodeX = "�ύ ";
				} else if ("02".equals(object.getOpemode())) {
					opemodeX = "����/�˰� ";
				} else if ("03".equals(object.getOpemode())) {
					opemodeX = "�鵵 ";
				} else if ("04".equals(object.getOpemode())) {
					opemodeX = "�߰� ";
				} else if ("05".equals(object.getOpemode())) {
					opemodeX = "ת�� ";
				}

				String createtime = object.getCreatetime().substring(0, 16);
				String donetime = object.getDonetime();
				donetime = donetime == null || "".equals(donetime) ? "&nbsp;"
						: donetime.substring(0, 16);

				if ("04".equals(object.getTypes())) {// �׶��Իظ�
					donetime = createtime;
				}

				showHtmlView += "<tr>"
						+ "<td nowrap=\"nowrap\" width=\"20%\"  class=\"label_nd_2\">"
						+ createtime
						+ "</td>"
						+ "<td nowrap=\"nowrap\" width=\"20%\"  class=\"label_nd_2\">"
						+ object.getCommitername()
						+ "</td>"
						+ "<td nowrap=\"nowrap\" width=\"40%\"  class=\"label_nd_2\">"
						+ opemodeX
						+ object.getActname()
						+ "</td>"
						+ "<td nowrap=\"nowrap\" width=\"20%\"  class=\"label_nd_2\">"
						+ object.getUsername()
						+ opemode
						+ sync
						+ "</td>"
						+ "<td nowrap=\"nowrap\" width=\"20%\"  class=\"label_nd_2\">"
						+ donetime + "</td>";

				showHtmlView += "</tr>";
			}
			showHtmlView += "</table>";
			// System.out.println(showHtmlView);
			response.setContentType("text/json;charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.write(showHtmlView);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <br>
	 * ����˵���������� <br>
	 * ���������String URL ����������ҳ��ַ�� <br>
	 * �������ͣ�
	 */
	public String getURL(String URL) throws Exception {
		StringBuffer sb = new StringBuffer();
		// ����һ��URL����
		URL url = new URL(URL);

		// ��ȡ�ӷ��������ص������ı�
		BufferedReader in = new BufferedReader(new InputStreamReader(url
				.openStream()));
		String str;
		while ((str = in.readLine()) != null) {
			// ������ı�����
			sb.append(str);
		}
		in.close();
		return sb.toString();
	}

	// start Ext

	public ActionForward onEditFrameExt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workcode = request.getParameter("workcode");
		boolean isedit = true;
		boolean ispermission = true;// �Ƿ����ü�Ȩ
		if (StringUtils.isNotEmpty(workcode)) {
			isedit = false;
		}
		Map<String, Boolean> pmap = permission(request, isedit, ispermission);
		isedit = pmap.get("isedit");
		ispermission = pmap.get("ispermission");
		loadExt(mapping, form, request, response, isedit, ispermission);
		return mapping.findForward("onEditFrameExt");
	}

	public void onListExt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String conditions = request.getParameter("conditions");
		JSONObject paramJson = null;
		if (StringUtils.isNotEmpty(conditions)) {
			paramJson = JSONObject.fromObject(conditions);
		}
		User user = getOnlineUser(request);
		String formcode = request.getParameter("formcode");
		String fatherlsh = request.getParameter("fatherlsh");
		String start = request.getParameter("start");// ��ʼ����
		String limit = request.getParameter("limit");// ҳ��
		PageInfo obj = new PageInfo();
		try {
			Integer from_ = Integer.parseInt(start);
			Integer limit_ = Integer.parseInt(limit);
			Integer to_ = from_ + limit_ - 1;

			DyFormData dydata = new DyFormData();
			BeanUtils.copyProperties(dydata, paramJson);
			dydata.setFatherlsh(fatherlsh);
			dydata.setFormcode(formcode);

			obj = (PageInfo) ins.queryForPage(dydata, from_, limit_, "");
			int total = obj.getTotalRows();
			List result = obj.getResultList();
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				DyFormData data = (DyFormData) iterator.next();

				String jsonStr = JSONUtil2.fromBean(data, "yyyy-MM-dd")
						.toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}
			super.writeJsonStr(response, super.buildJsonStr(total, jsonBuffer
					.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ����
	public ActionForward onSavaOrUpdateExt(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String formcode = request.getParameter("formcode");
		String fatherlsh = request.getParameter("fatherlsh");
		request.setAttribute("ErrorJson", "Yes");// Json������ʾ
		JSONObject json = new JSONObject();
		DyFormData mainform = this.getBean(request);
		String subform = request.getParameter("subform");
		try {
			User user = getOnlineUser(request);
			String jsonx = ins.saveAndUpdate(request, user.getUserName() + "["
					+ user.getUserCode() + "]", formcode, mainform, subform,
					fatherlsh);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "������Ϣʧ��!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
		return null;
	}

	// ����ɾ��
	public void onDeleteExt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		request.setAttribute("ErrorJson", "Yes");// Json������ʾ
		JSONObject json = new JSONObject();
		String formcode = request.getParameter("formcode");
		String lsh = request.getParameter("lsh");
		try {
			String jsonx = "";
			jsonx = ins.delete(request, formcode, lsh);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "����ʧ��!");
			json.put("error", "yes");
			log.error("����", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// ����
	private void loadExt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			boolean isedit, boolean permission) throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String lsh = request.getParameter("lsh");
		String formcode = request.getParameter("formcode");
		String fatherlsh = request.getParameter("fatherlsh");
		Map groupMap = new HashMap();
		try {
			User user = getOnlineUser(request);// ��ȡ��ǰ�û�
			String formhtml = "";
			DyForm dyform = DyEntry.iv().loadForm(formcode);
			if (permission) {

			}
			// if (StringUtils.isNotEmpty(lsh) &&
			// StringUtils.isNotEmpty(formcode)) {
			Map issubmap = new HashMap();
			issubmap.put(-1, isedit);
			formhtml = ins.load("", "", dyform, lsh, isedit, issubmap, user
					.getUserName()
					+ "," + user.getDepartmentName(), request
					.getParameter("url"), user, false);
			// }
			request.setAttribute("form", formhtml.toString());
			String linkcss = DyFormComp.getStyle(getURL(dyform
					.getStyleinfourl_()));
			request.setAttribute("linkcss", linkcss);
			request.setAttribute("ValidateScript", DyFormBuildHtmlExt
					.buildValidateScript(dyform));
			request
					.setAttribute("datecompFunc", DyFormComp
							.getInitFuncScript());
			request.setAttribute("formcode", formcode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// end ext

	// ������ѯҳ��
	public ActionForward dyformDetailQueryMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return null;
	}

	// �������鱨��
	public void dyformDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		ins.dyformDetail(request, response);
	}

	// �������鱨��
	public void dyformDealDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		ins.dyformDealDetail(request, response);
	}

	// �����ַ�����HTML
	private void loadAccordtree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String naturalurl = request.getParameter("naturalurl");
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		User user = getOnlineUser(request);
		String usercode = user.getUserCode();
		List<UmsProtectedobject> list = FrameResource.findByPId(naturalurl,
				usercode);
		for (UmsProtectedobject umsProtectedobject : list) {

			boolean perm = SecurityEntry.iv().permission(usercode,
					umsProtectedobject.getNaturalname());

			if (perm) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", umsProtectedobject.getName());
				map.put("json", FrameResource.buildRootTreeRelation(
						umsProtectedobject.getNaturalname(), true, false,
						usercode));

				jsonList.add(map);
			}
		}

		String html = DyFormComp.getEasyuiAccordionTree(jsonList);
		request.setAttribute("accordhtml", html);
	}

	private void setExtQueryColumnVar(HttpServletRequest request,
			String formcode) {
		request
				.setAttribute("ExtQueryColumn",
						getExtQueryColumn(formcode, "1"));
		request.setAttribute("ExtQueryColumn2", getExtQueryColumn2(formcode,
				"1"));
	}

	private String getExtQueryColumn(String formcode, String model) {
		StringBuffer s = new StringBuffer();

		// c1
		s.append("'"
				+ DyFormComp.getSelectKV("c1", "", "width:\"99%\"", "", false,
						" - ,(-(", "", false) + "'");
		s.append(",");

		// c2
		s.append("'" + getExtQueryColumnC2(formcode, model) + "'");
		s.append(",");

		// c3
		s.append("'"
				+ DyFormComp.getSelectKV("c3", "", "width:\"99%\"", "", false,
						"&gt;->,&lt;-<,=-=,&gt;=->=,&lt;=-<=,like-like", "",
						false) + "'");
		s.append(",");

		// c4
		s.append("'"
				+ DyFormComp.getText("c4", "", "width:\"99%\"", "", false, "")
				+ "'");
		s.append(",");

		// c5
		s.append("'"
				+ DyFormComp.getSelectKV("c5", "", "width:\"99%\"", "", false,
						" - ,)-)", "", false) + "'");
		s.append(",");

		// c6
		s.append("'"
				+ DyFormComp.getSelectKV("c6", "", "width:\"99%\"", "", false,
						" - ,OR-OR,AND-AND", "", false) + "'");

		return s.toString();
	}

	private String getExtQueryColumnC2(String formcode, String model) {

		try {
			List<TCsColumn> list = DyEntry.iv().QueryColumn(formcode, model);
			String split = "";
			StringBuffer value = new StringBuffer();
			for (TCsColumn dyFormColumn : list) {
				// if (dyFormColumn.isHidden() == false) {
				value.append(split + dyFormColumn.getColumnid() + "-"
						+ dyFormColumn.getColumname());
				split = ",";
				// }
			}
			return DyFormComp.getSelectKV("c2", "", "width:\"99%\"", "", false,
					value.toString(), "", false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private String getExtQueryColumn2(String formcode, String model) {
		try {
			List<TCsColumn> list = DyEntry.iv().QueryColumn(formcode, model);
			String split = "";
			StringBuffer value = new StringBuffer();
			for (TCsColumn dyFormColumn : list) {
				// if (dyFormColumn.isHidden() == false) {
				value.append(split + "['" + dyFormColumn.getColumname() + "','"
						+ dyFormColumn.getColumnid() + "']");
				split = ",";
				// }
			}
			return value.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// ������
	public ActionForward colconfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String naturalname = request.getParameter("naturalname");
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();
		DyForm dyform = DyEntry.iv().loadForm(formcode);

		User user = getOnlineUser(request);
		String forms = DyFormBuildHtmlExt.buildCustomColConfigHtml(dyform, user
				.getUserCode());
		String addtabscript = DyFormBuildHtmlExt.buildCustomColConfigJs(dyform);
		request.setAttribute("form", forms);
		request.setAttribute("addtabscript", addtabscript);
		return mapping.findForward("colconfig");
	}

	// �����ñ���
	public void savecolconfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject json = new JSONObject();

		String subform = request.getParameter("subform");
		JSONArray jsonArray = JSONArray.fromObject(subform);

		User user = getOnlineUser(request);
		List result = new ArrayList();
		Map formcodeMap = new HashMap();// ��Ÿ���form����
		try {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);

				DyFormData subdata = new DyFormData();
				BeanUtils.copyProperties(subdata, obj);
				subdata.setFatherlsh("");

				DyFormData data = new DyFormData();
				data.setColumn3(user.getUserCode());
				data.setColumn5(subdata.getFormcode());
				String lsh = DyFormBuildHtmlExt.checkColConfigExist(data);
				if (StringUtils.isNotEmpty(lsh)) {
					data.setColumn4(JSONObject.fromObject(subdata).toString());
					data.setParticipant(user.getUserCode());
					data.setFatherlsh("");
					data.setLsh(lsh);
					DyEntry.iv().modifyData(
							"4ea6cde8893211e1aecf5961a4b828b8_", data);
				} else {
					data.setColumn4(JSONObject.fromObject(subdata).toString());
					data.setParticipant(user.getUserCode());
					data.setFatherlsh("");
					DyEntry.iv().addData("4ea6cde8893211e1aecf5961a4b828b8_",
							data);
				}
			}
			json.put("tip", "����ɹ�!");
		} catch (Exception e) {
			json.put("tip", "����ʧ��!");
			json.put("error", "yes");
			log.error("����", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}

	}

}