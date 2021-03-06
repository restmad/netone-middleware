/**
 * 
 */
package com.jl.service.impl;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oe.cav.bean.logic.bus.TCsBus;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.serialize.dao.PageInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jl.common.JSONUtil2;
import com.jl.common.app.AppEntry;
import com.jl.common.app.AppObj;
import com.jl.common.dyform.DyAnalysisXml;
import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyForm;
import com.jl.common.dyform.DyFormBuildHtml;
import com.jl.common.dyform.DyFormComp;
import com.jl.common.dyform.DyFormData;
import com.jl.common.resource.Resource;
import com.jl.common.resource.ResourceNode;
import com.jl.common.security3a.SecurityEntry;
import com.jl.common.workflow.TWfActive;
import com.jl.common.workflow.TWfConsoleIfc;
import com.jl.common.workflow.WfEntry;
import com.jl.dao.CommonDAO;
import com.jl.entity.User;
import com.jl.service.BaseService;
import com.jl.service.FrameService;
import com.jl.web.controller.FrameAction;
import com.sun.org.apache.commons.beanutils.BeanUtils;

public class FrameServiceImpl extends BaseService implements FrameService {

	private final String $DYFORM = "_DYFORM";

	private final Logger LOG = Logger.getLogger(FrameServiceImpl.class);

	private final boolean debug = true;

	private static CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public String saveAndUpdate(HttpServletRequest request, String participant,
			String formcode, DyFormData form, String subform, String fatherlsh)
			throws Exception {
		// long start = new Date().getTime();
		JSONObject json = new JSONObject();
		String id = form.getLsh();
		form.setFatherlsh(fatherlsh);
		boolean isnew = false;
		if (StringUtils.isNotEmpty(id)) {// 根据Id 来判断是 否是修改还是插入
			boolean success = DyEntry.iv().modifyData(formcode, form);
			if (!success) {
				json.put("error", "yes");
				json.put("tip", "保存失败,表单输入字段信息不符合要求.");
				return json.toString();
			}
		} else {
			isnew = true;
			form.setParticipant(StringUtils.substringBetween(participant, "[",
					"]"));
			id = DyEntry.iv().addData(formcode, form);
			if (id == null) {
				json.put("error", "yes");
				json.put("tip", "保存失败,表单输入字段信息不符合要求.");
				return json.toString();
			}
		}

		// START 前置脚本
		DyAnalysisXml dayx = new DyAnalysisXml();
		if (isnew) {
			TCsBus bux = new TCsBus();
			BeanUtils.copyProperties(bux, form);
			dayx.scriptPre(formcode, bux, "PNewSave");
		} else {
			dayx.script(formcode, id, "PUpdateSave"); // 正常保存
		}
		// END 前置脚本

		// 子表单操作
		if (subform != null && !"[]".equals(subform)) {
			// 新增表单
			JSONArray jsonArray = JSONArray.fromObject(subform);

			List result = new ArrayList();
			Map formcodeMap = new HashMap();// 存放更新form数据
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);

				DyFormData subdata = new DyFormData();
				BeanUtils.copyProperties(subdata, obj);
				subdata.setParticipant(participant);
				subdata.setFatherlsh(id);
				result.add(subdata);

				formcodeMap.put(subdata.getFormcode(), subdata.getFormcode());
			}

			// 删除子表单
			DyForm dyform = DyEntry.iv().loadForm(formcode);
			DyForm[] subdyforms = dyform.getSubform_();
			if (subdyforms != null) {
				for (int i = 0; i < subdyforms.length; i++) {
					DyForm subdyform = subdyforms[i];
					if (formcodeMap.containsKey(subdyform.getFormcode())) {// 不更新form数据则不删除
						DyEntry.iv().deleteAll(subdyform.getFormcode(), id);
					}
				}
			}

			// 保存表单
			if (result.size() > 0)
				DyEntry.iv().addAll(result);
		}

		// START 后置脚本
		// System.out.println("new data coming:" + lsh);
		if (isnew) {
			dayx.script(formcode, id, "NewSave");
		} else {
			dayx.script(formcode, id, "UpdateSave"); // 正常保存
		}
		// END 后置脚本

		// long end = new Date().getTime();
		// System.out.println("保存花费时间：" + (double) (end - start) / 1000 + "秒");
		json.put("tip", "保存成功");
		json.put("lsh", id);
		request.setAttribute("$lsh", id);
		// WebCache.removeCache($DYFORM + formcode + id + "true");
		// WebCache.removeCache($DYFORM + formcode + id + "false");
		return json.toString();
	}

	public String delete(HttpServletRequest request, String formcode, String lsh)
			throws Exception {
		JSONObject json = new JSONObject();

		// START 前置脚本
		DyAnalysisXml dayx = new DyAnalysisXml();
		dayx.script(formcode, lsh, "PDelete");
		// END 前置脚本

		// true 表示表单锁定 不能编辑
		boolean isformLock = WfEntry.iv().bussFormLock(lsh);
		if (isformLock) {
			json.put("tip", "已归档或在审核中的单子，不能作废!");
			json.put("error", "yes");
			return json.toString();
		}

		if (StringUtils.isEmpty(lsh)) {
			json.put("tip", "该单未保存,无须作废!");
		} else {
			DyForm dyform = DyEntry.iv().loadForm(formcode);
			DyForm[] subdyforms = dyform.getSubform_();
			if (subdyforms != null) {
				for (int i = 0; i < subdyforms.length; i++) {
					DyForm subdyform = subdyforms[i];
					DyEntry.iv().deleteAll(subdyform.getFormcode(), lsh);
				}
			}
			boolean success = DyEntry.iv().deleteData(formcode, lsh);
			if (success) {
				String runtimeidx = WfEntry.iv().getSession(lsh);
				if (runtimeidx != null) {
					WfEntry.iv().deleteProcess(runtimeidx);
				}
				json.put("tip", "作废成功!");
			} else {
				json.put("error", "yes");
				json.put("tip", "作废失败!");
			}
			// WebCache.removeCache($DYFORM + formcode + lsh + "true");
			// WebCache.removeCache($DYFORM + formcode + lsh + "false");
		}

		// START 后置脚本
		if (!json.containsKey("error")) {
			dayx.script(formcode, lsh, "Delete");
		}
		// END 后置脚本

		return json.toString();
	}

	public String deleteByLogic(HttpServletRequest request, String formcode,
			String lsh) throws Exception {
		JSONObject json = new JSONObject();

		// START 前置脚本
		DyAnalysisXml dayx = new DyAnalysisXml();
		dayx.script(formcode, lsh, "PDelete");
		// END 前置脚本

		// true 表示表单锁定 不能编辑
		boolean isformLock = WfEntry.iv().bussFormLock(lsh);
		if (isformLock) {
			json.put("tip", "已归档或在审核中的单子，不能作废!");
			json.put("error", "yes");
			return json.toString();
		}

		if (StringUtils.isEmpty(lsh)) {
			json.put("tip", "该单未保存,无须归档!");
		} else {
			boolean success = DyEntry.iv().deleteDataByLogic(formcode, lsh);
			if (success) {
				String runtimeid = WfEntry.iv().getSession(lsh);
				if (runtimeid != null && !runtimeid.equals("")) {
					String clientId = WfEntry.iv().getVarByRuntimeId(runtimeid,
							TWfConsoleIfc._DEFAULT_REV_KEY_CUSTOMER);
					List list = WfEntry.iv().listAllRunningWorklistByRuntimeid(
							runtimeid);
					for (Iterator iterator = list.iterator(); iterator
							.hasNext();) {
						TWfWorklist object = (TWfWorklist) iterator.next();
						String error = WfEntry.iv().nextToEnd(
								object.getWorkcode(), clientId);
						if (StringUtils.isNotEmpty(error)) {
							json.put("error", "yes");
							json.put("tip", "归档失败(" + error + ")");
							return json.toString();
						}
					}
				}
				json.put("tip", "归档成功!");
			} else {
				json.put("error", "yes");
				json.put("tip", "归档失败!");
			}
			// WebCache.removeCache($DYFORM + formcode + lsh + "true");
			// WebCache.removeCache($DYFORM + formcode + lsh + "false");
		}

		// START 后置脚本
		if (!json.containsKey("error")) {
			dayx.script(formcode, lsh, "Delete");
		}
		// END 后置脚本

		return json.toString();
	}

	public String load(String workcode, String naturalname, DyForm dyform,
			String lsh, boolean isedit, Map subformmode, String userinfo,
			String parameter) throws Exception {
		// long start = new Date().getTime();
		StringBuffer html = new StringBuffer();
		userinfo = userinfo.replace("//", "/");
		html.append(load_(dyform, isedit, subformmode, userinfo, workcode,
				naturalname, lsh, parameter));
		// long end = new Date().getTime();
		// System.out.println("加载花费时间：" + (double) (end - start) / 1000 + "秒");
		return html.toString();
	}

	private String load_(DyForm dyform, boolean isedit, Map subformmode,
			String userinfo, String workcode, String naturalname, String lsh,
			String parameter) throws Exception {
		StringBuffer html = new StringBuffer();
		DyForm[] subdyforms = dyform.getSubform_();
		Boolean ishidden = false;// 是否隐藏
		if (subformmode != null && subformmode.containsKey("MAINFORM")) {
			String submode = (String) subformmode.get("MAINFORM");
			if ("0".equals(submode)) {// 编辑
				isedit = true;
				ishidden = false;
			} else if ("1".equals(submode)) {// 只读
				isedit = false;
				ishidden = false;
			} else if ("2".equals(submode)) {// 隐藏
				ishidden = true;
			} else {
				ishidden = false;
			}
		}

		String hiddenid = DyFormComp.getHiddenInput("naturalname", naturalname);
		String hiddenunid = DyFormComp.getHiddenInput("unid", lsh);
		String hiddenlsh = DyFormComp.getHiddenInput("lsh", lsh);
		if (!ishidden) {
			html.append(DyFormBuildHtml.buildForm(dyform, isedit, userinfo,
					naturalname, lsh, false, false, parameter, hiddenid
							+ hiddenunid + hiddenlsh));
		} else {
			html.append("<div style='display:none'>"
					+ DyFormBuildHtml.buildForm(dyform, isedit, userinfo,
							naturalname, lsh, false, false, parameter, hiddenid
									+ hiddenunid + hiddenlsh) + "</div>");
		}
		if (subdyforms != null && subdyforms.length > 0) {
			Boolean issubedit = true;// 是否可编辑
			Boolean issubhidden = false;// 是否隐藏
			for (int i = 0; i < subdyforms.length; i++) {
				DyForm subdyform = subdyforms[i];
				if (subformmode == null) {
					issubedit = true;
				} else if (subformmode.containsKey(-1)) {
					String submode = (String) subformmode.get(-1);
					if ("0".equals(submode)) {// 编辑
						issubedit = true;
						issubhidden = false;
					} else if ("1".equals(submode)) {// 只读
						issubedit = false;
						issubhidden = false;
					} else if ("2".equals(submode)) {// 隐藏
						issubhidden = true;
					} else {
						issubedit = true;
						issubhidden = false;
					}
				} else {
					String submode = (String) subformmode.get(i);
					if ("0".equals(submode)) {// 编辑
						issubedit = true;
						issubhidden = false;
					} else if ("1".equals(submode)) {// 只读
						issubedit = false;
						issubhidden = false;
					} else if ("2".equals(submode)) {// 隐藏
						issubhidden = true;
					} else {
						issubedit = true;
						issubhidden = false;
					}
				}

				if (issubedit == null)
					issubedit = true;

				// 特殊处理
				if (isedit == false)
					issubedit = false;

				String submode = subdyform.getSubmode();
				if ("2".equals(submode)) {// 2:链接展示-多条子表单记录(需要保存主表单)
					if (!issubhidden)
						html.append(DyFormBuildHtml.buildLinkForm(subdyform,
								lsh, issubedit, userinfo, submode, workcode,
								naturalname, parameter));
				} else if ("3".equals(submode)) {// 3:链接展示-单子表单记录(需要保存主表单、且系统控制只能一条)
					if (!issubhidden)
						html.append(DyFormBuildHtml.buildLinkForm(subdyform,
								lsh, issubedit, userinfo, submode, workcode,
								naturalname, parameter));
				} else if ("4".equals(submode)) {// 4:集成展示-单子表单记录(系统控制只能一条)
					if (!issubhidden)
						html.append(DyFormBuildHtml.buildForm(subdyform,
								issubedit, userinfo, naturalname, lsh, true,
								true, parameter, ""));
				} else if ("5".equals(submode)) {// 5:集成展示-单子表单记录(系统控制只能一条)
					// 不显示标题
					if (!issubhidden)
						html.append(DyFormBuildHtml.buildForm(subdyform,
								issubedit, userinfo, naturalname, lsh, true,
								false, parameter, ""));
				} else if ("9".equals(submode)) {// 1+:集成展示-布局表单多条记录
					if (!issubhidden)
						html.append(DyFormBuildHtml.buildSubForms(subdyform,
								lsh, issubedit, userinfo, parameter));
				} else {// 1:集成展示-多条子表单记录（默认模式）
					if (!issubhidden)
						html.append(DyFormBuildHtml.buildSubForm(subdyform,
								lsh, issubedit, userinfo, parameter));
				}
			}
		}
		return html.toString();
	}

	public Map newWfNode(HttpServletRequest request, String naturalname,
			String processid, String runtimeid, User user, String id,
			String participant_) throws Exception {
		Map wfmap = new HashMap();
		String clientId = participant_;
		String mode = naturalname;
		String ddid = id;

		boolean reuseform = false;
		// 流程共用一个表单特殊处理

		// 促销核销
		if ("APPFRAME.APPFRAME.HGMY.HXTASK".equals(naturalname)) {
			reuseform = true;
		} else if ("APPFRAME.APPFRAME.HGMY.PROJECTHXTASK".equals(naturalname)) {// 项目方案核销
			reuseform = true;
		}

		String ddurl = "frame.do?method=onEditViewMain&naturalname="
				+ naturalname + "&lsh=";
		boolean isspecial = false;//
		if (StringUtils.isEmpty(runtimeid) || "null".equals(runtimeid)) {
			String runtimeidx = WfEntry.iv().getSession(id);
			if (runtimeidx != null && !runtimeidx.equals("") && !reuseform) {
				runtimeid = runtimeidx;
				isspecial = true;
			} else {
				runtimeid = WfEntry.iv().newProcess(processid, clientId, mode,
						"", ddid, ddurl);
				WfEntry.iv().runProcess(runtimeid);
			}
		}
		List listx = WfEntry.iv().useCoreView().fetchRunningWorklist(runtimeid);
		TWfWorklist TWfWorklistx = (TWfWorklist) listx.get(0);

		String workcode = TWfWorklistx.getWorkcode();
		// 获得所有的下一步节点
		List xx = WfEntry.iv().listNextRouteActive(processid,
				TWfWorklistx.getActivityid(), runtimeid, user.getUserCode());
		if (isspecial == false) {
			saveYijian(request, workcode, user.getUserCode(), "新建");
		}
		if (xx.size() == 1) {
			TWfActive Activity = (TWfActive) xx.get(0);
			String participant = Activity.getParticipant();

			boolean isNeedReader = false;
			boolean isNeedAssistant = false;
			if (Activity.isNeedReader() == true) {
				isNeedReader = Activity.isNeedReader();
			}
			if (Activity.isNeedAssistant() == true) {
				isNeedAssistant = Activity.isNeedAssistant();
			}

			wfmap.put("pmode", Activity.getParticipantmode());
			wfmap.put("autoroute", Activity.isAutoroute());
			wfmap.put("needsync", Activity.isNeedsync());
			wfmap.put("singleman", Activity.isSingleman());
			wfmap.put("needtree", Activity.isNeedTree());
			wfmap.put("name", Activity.getName());
			wfmap.put("type", Activity.getParticipantmode());
			participant = participant == null ? "" : participant;
			FrameAction.setWfUser(participant, wfmap);
			wfmap.put("value", "0");
			wfmap.put("activeids", Activity.getId());

			wfmap.put("runtimeid", runtimeid);
			wfmap.put("workcode", workcode);
			wfmap.put("ismultinode", false);// 单分支
		} else {
			wfmap.put("ismultinode", true);// 多分支
			wfmap.put("commiter", user.getUserCode());
			wfmap.put("runtimeid", runtimeid);
			wfmap.put("workcode", workcode);
		}
		return wfmap;
	}

	// 提交 正常流程
	public String newEnd(HttpServletRequest request, String naturalname,
			String cachekey, String activityid, String filteractiveids,
			String workcode, String participant, String userCodes,
			String userNames, String limittimes, String processlen,
			boolean issync, String operatemode) throws Exception {
		JSONObject json = new JSONObject();
		json.put("tip", "提交成功");
		TWfWorklist worklist = WfEntry.iv().loadWorklist(workcode);
		String runtimeid = worklist.getRuntimeid();
		String yijian = worklist.getExtendattribute();

		// 原先用operatemode 判断，但是发现传递过来的operatemode参数总是为空
		if ("03".equals(operatemode)) {
			json.put("tip", "抄阅成功");
			return json.toString();
		} else if ("04".equals(operatemode)) {
			json.put("tip", "阶段性回复成功");
			// WebCache.removeCache($DYFORM + cachekey);
			String error = WfEntry.iv().nextToEnd(workcode, participant);
			if (StringUtils.isNotEmpty(error)) {
				json.put("error", "yes");
				json.put("tip", "阶段性回复失败(" + error + ")");
			}
			return json.toString();

		} else if ("end".equals(operatemode)
				|| filteractiveids
						.contains(FrameService.trackActionSpecialTypeEND)) {
			json.put("tip", "归档成功");
			// WebCache.removeCache($DYFORM + cachekey);
			String error = WfEntry.iv().nextToEnd(workcode, participant);
			if (StringUtils.isNotEmpty(error)) {
				json.put("error", "yes");
				json.put("tip", "归档失败(" + error + ")");
			}
			return json.toString();
		}

		String[] usercode_ = userCodes.split(",");
		String[] username_ = userNames.split(",");
		String[] activityid_ = activityid.split(",");
		String[] limittimes_ = limittimes.split(",");

		Map<String, StringBuffer> activityMap = new HashMap();// 流程活动节点
		Map<String, StringBuffer> specialActivityMap = new HashMap();// 特殊活动节点
		Map<String, String> limitMap = new HashMap();//
		setActivityMap(activityMap, specialActivityMap, limitMap, activityid_,
				usercode_, username_, limittimes_);

		boolean end = false;
		String filteractiveids_ = filteractiveids + ",";

		if (StringUtils.isNotEmpty(processlen)) {
			Integer len = Integer.parseInt(processlen);
			filteractiveids_.replaceAll(FrameService.trackActionSpecialType1,
					"");
			filteractiveids_.replaceAll(FrameService.trackActionSpecialType2,
					"");
			filteractiveids_.replaceAll(FrameService.trackActionSpecialType3,
					"");
			// filteractiveids_.replaceAll(FrameService.trackActionSpecialTypeEND,
			// "");
			filteractiveids_ = StringUtils.substring(filteractiveids_, 0,
					filteractiveids_.length() - 1);
			String[] aids = filteractiveids_.split(",");

			if (specialActivityMap
					.containsKey(FrameService.trackActionSpecialType3)) {
				if (aids.length <= 0) {
					json.put("tip", "请在分支流程指派人员!");
					json.put("error", "yes");
					return json.toString();
				}
			} else {
				if (len == 0) {
					end = true;
				} else if (aids.length != activityMap.size()) {
					json.put("tip", "存在多个流程,请在所有分支流程指派人员!");
					json.put("error", "yes");
					return json.toString();
				}
			}

		}

		if (end == false) {
			if (!specialActivityMap
					.containsKey(FrameService.trackActionSpecialType3)) {
				String error = "";
				if (activityMap.size() == 1) {
					error = nextNormalByManual(activityMap, workcode,
							participant);
				} else {
					error = nextNormalByAuto(workcode, participant);
				}
				if (StringUtils.isNotEmpty(error)) {
					json.put("error", "yes");
					json.put("tip", "提交失败(" + error + ")");
					return json.toString();
				}
			}

			List listWorklistnext = WfEntry.iv().useCoreView()
					.fetchRunningWorklist(runtimeid);

			// 正常流程
			for (Iterator iteratorX = listWorklistnext.iterator(); iteratorX
					.hasNext();) {
				TWfWorklist object = (TWfWorklist) iteratorX.next();
				String activityid_for_check = object.getActivityid();

				if (specialActivityMap
						.containsKey(FrameService.trackActionSpecialType3)) {
					ready_to_specify_reader(naturalname, activityMap,
							specialActivityMap, limitMap, activityid_for_check,
							object, participant, issync, "01");
				} else {
					ready_to_specify(naturalname, activityMap,
							specialActivityMap, limitMap, activityid_for_check,
							object, participant, issync, "01");
				}
			}
		} else {
			// WebCache.removeCache($DYFORM + cachekey+"false");
			String error = WfEntry.iv().nextToEnd(workcode, participant);
			if (StringUtils.isNotEmpty(error)) {
				json.put("error", "yes");
				json.put("tip", "提交失败(" + error + ")");
				return json.toString();
			}
		}

		return json.toString();
	}

	// 退办、特送
	public String auditEnd(HttpServletRequest request, String naturalname,
			String activityid, String workcode, String participant,
			String userCodes, String userNames, String limittimes,
			boolean issync) throws Exception {
		JSONObject json = new JSONObject();
		json.put("tip", "提交成功");
		String[] usercode_ = userCodes.split(",");
		String[] username_ = userNames.split(",");
		String[] activityid_ = activityid.split(",");
		String[] limittimes_ = limittimes.split(",");

		Map<String, StringBuffer> activityMap = new HashMap();// 流程活动节点
		Map<String, StringBuffer> specialActivityMap = new HashMap();// 特殊活动节点
		Map<String, String> limitMap = new HashMap();//
		setActivityMap(activityMap, specialActivityMap, limitMap, activityid_,
				usercode_, username_, limittimes_);

		// 正常流程
		TWfWorklist TWfWorklist = WfEntry.iv().loadWorklist(workcode);
		String runtimeid = TWfWorklist.getRuntimeid();

		String error = "";
		if (activityMap.size() == 1) {
			error = nextNormalByManual(activityMap, workcode, participant);
		} else {
			error = nextNormalByAuto(workcode, participant);
		}
		if (StringUtils.isNotEmpty(error)) {
			json.put("error", "yes");
			json.put("tip", "提交失败(" + error + ")");
			return json.toString();
		}

		String[] workcodearr = WfEntry.iv().getRunningWorkCodeByRuntimeid(
				runtimeid);
		for (int i = 0; i < workcodearr.length; i++) {
			String runningWorkcode = workcodearr[i];
			TWfWorklist worklist = WfEntry.iv().loadWorklist(runningWorkcode);

			ready_to_specify(naturalname, activityMap, specialActivityMap,
					limitMap, worklist.getActivityid(), worklist, participant,
					issync, "02");
		}
		return json.toString();
	}

	public String assignEnd(HttpServletRequest request, String naturalname,
			String activityid, String workcode, String participant,
			String userCodes, String userNames) throws Exception {
		JSONObject json = new JSONObject();
		json.put("tip", "提交成功");
		String[] usercode_ = userCodes.split(",");
		String[] username_ = userNames.split(",");
		String[] activityid_ = activityid.split(",");
		String[] limittimes_ = "".split(",");

		Map<String, StringBuffer> activityMap = new HashMap();// 流程活动节点
		Map<String, StringBuffer> specialActivityMap = new HashMap();// 特殊活动节点
		Map<String, String> limitMap = new HashMap();//
		setActivityMap(activityMap, specialActivityMap, limitMap, activityid_,
				usercode_, username_, limittimes_);

		TWfWorklist worklist = WfEntry.iv().loadWorklist(workcode);
		String error = WfEntry.iv().nextToZhuanbang(workcode, participant);
		if (StringUtils.isNotEmpty(error)) {
			json.put("error", "yes");
			json.put("tip", "提交失败(" + error + ")");
			return json.toString();
		}
		assign_to_specify(naturalname, activityMap, worklist.getActivityid(),
				worklist, participant);
		return json.toString();
	}

	private void setActivityMap(Map<String, StringBuffer> activityMap,
			Map<String, StringBuffer> specialActivityMap,
			Map<String, String> limitMap, String[] activityid_,
			String[] usercode_, String[] username_, String[] limittimes_) {
		for (int i = 0; i < usercode_.length; i++) {
			String tmp_activityid_ = activityid_[i];
			if (FrameService.trackActionSpecialType1
					.equalsIgnoreCase(tmp_activityid_)
					|| FrameService.trackActionSpecialType2
							.equalsIgnoreCase(tmp_activityid_)
					|| FrameService.trackActionSpecialType3
							.equalsIgnoreCase(tmp_activityid_)) {// 特殊
				if (specialActivityMap.containsKey(tmp_activityid_)) {
					StringBuffer sbx = specialActivityMap.get(tmp_activityid_);
					sbx.append(username_[i] + "[" + usercode_[i] + "]" + ",");
					specialActivityMap.put(tmp_activityid_, sbx);
				} else {
					StringBuffer sbx = new StringBuffer();
					sbx.append(username_[i] + "[" + usercode_[i] + "]" + ",");
					specialActivityMap.put(tmp_activityid_, sbx);
				}
			} else {// 正常
				if (activityMap.containsKey(tmp_activityid_)) {
					StringBuffer sbx = activityMap.get(tmp_activityid_);
					sbx.append(username_[i] + "[" + usercode_[i] + "]" + ",");
					activityMap.put(tmp_activityid_, sbx);
				} else {
					StringBuffer sbx = new StringBuffer();
					sbx.append(username_[i] + "[" + usercode_[i] + "]" + ",");
					activityMap.put(tmp_activityid_, sbx);
				}
			}
			limitMap.put(tmp_activityid_, limittimes_[i]);
		}
	}

	public String saveYijian(HttpServletRequest request, String workcode,
			String userCode, String yijian) throws Exception {
		JSONObject json = new JSONObject();
		WfEntry.iv().saveAuditNote(workcode, userCode, yijian);
		json.put("tip", "意见填写成功");
		return json.toString();
	}

	public PageInfo queryForPage(DyFormData dydata, int from, int to,
			String condition) {
		PageInfo obj = new PageInfo();
		try {
			List list = DyEntry.iv().queryData(dydata, from, to, condition);
			int total = DyEntry.iv().queryDataNum(dydata, condition);

			obj.setTotalRows(total);
			obj.setResultList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	// 下一步 正常流程
	private String nextNormalByManual(Map activityMap, String workcode,
			String participant) throws Exception {
		String error = "";
		for (Iterator iterator = activityMap.keySet().iterator(); iterator
				.hasNext();) {
			String activityidx = (String) iterator.next();
			error = WfEntry.iv().nextByManual(workcode, activityidx,
					participant);
			if (StringUtils.isNotEmpty(error)) {
				return error;
			}
		}
		return error;
	}

	// 下一步自动 正常流程
	private String nextNormalByAuto(String workcode, String participant)
			throws Exception {
		return WfEntry.iv().nextByAuto(workcode, participant);
	}

	// 下一步 抄送流程
	@Deprecated
	private void nextAssistantByManual(Map specialActivityMap, String workcode,
			String participant, String yijian) throws Exception {
		for (Iterator iterator = specialActivityMap.keySet().iterator(); iterator
				.hasNext();) {
			String activityidx = (String) iterator.next();
			if (FrameService.trackActionSpecialType1.equals(activityidx)) {// 抄送
				// WfEntry.iv().nextByAuto(workcode, participant);
			}
		}
	}

	// 开始分配
	private void ready_to_specify(String naturalname, Map activityMap,
			Map specialActivityMap, Map<String, String> limitMap,
			String activityid, TWfWorklist worklist, String participant,
			boolean issync, String opemode) throws Exception {
		for (Iterator iterator = activityMap.keySet().iterator(); iterator
				.hasNext();) {
			String activityidx = (String) iterator.next();
			if (activityidx != null && activityidx.equals(activityid)) {
				String person = activityMap.get(activityidx).toString();
				String limit = limitMap.get(activityidx).toString();

				if (limit == null || StringUtils.isEmpty(limit))
					limit = "0";

				person = StringUtils.substring(person.toString(), 0, person
						.toString().length() - 1);
				String[] persons = person.split(",");

				TWfActive actinfo = WfEntry.iv().loadRuntimeActive(
						worklist.getProcessid(), worklist.getActivityid(),
						naturalname, "", worklist.getRuntimeid());

				for (int i = 0; i < persons.length; i++) {
					WfEntry.iv().specifyParticipantByWorkcode(participant,
							worklist.getWorkcode(), persons[i],
							actinfo.isNeedsync(), opemode);
					WfEntry.iv().specifyLimit(worklist.getWorkcode(),
							persons[i], Long.valueOf(limit));
				}

				for (Iterator iteratorx = specialActivityMap.keySet()
						.iterator(); iteratorx.hasNext();) {
					String activityidxx = (String) iteratorx.next();
					String personx = specialActivityMap.get(activityidxx)
							.toString();
					String limitx = limitMap.get(activityidxx).toString();

					if (limitx == null || StringUtils.isEmpty(limitx))
						limitx = "0";

					personx = StringUtils.substring(personx.toString(), 0,
							personx.toString().length() - 1);
					String[] personxs = personx.split(",");

					for (int i = 0; i < personxs.length; i++) {
						if (FrameService.trackActionSpecialType1
								.equals(activityidxx)) {// 抄送
							WfEntry.iv().specifyAssistantByWorkcode(
									participant, worklist.getWorkcode(),
									personxs[i], issync, opemode);
						} else if (FrameService.trackActionSpecialType2
								.equals(activityidxx)) {// 抄阅
							WfEntry.iv().specifyReaderByWorkcode(participant,
									worklist.getWorkcode(), personxs[i],
									opemode);
						}
						WfEntry.iv().specifyLimit(worklist.getWorkcode(),
								personxs[i], Long.valueOf(limitx));
					}
				}

			}
		}
	}

	// 开始分配 阅读
	private void ready_to_specify_reader(String naturalname, Map activityMap,
			Map specialActivityMap, Map<String, String> limitMap,
			String activityid, TWfWorklist worklist, String participant,
			boolean issync, String opemode) throws Exception {

		for (Iterator iteratorx = specialActivityMap.keySet().iterator(); iteratorx
				.hasNext();) {
			String activityidxx = (String) iteratorx.next();
			String personx = specialActivityMap.get(activityidxx).toString();
			String limitx = limitMap.get(activityidxx).toString();

			if (limitx == null || StringUtils.isEmpty(limitx))
				limitx = "0";

			personx = StringUtils.substring(personx.toString(), 0, personx
					.toString().length() - 1);
			String[] personxs = personx.split(",");

			for (int i = 0; i < personxs.length; i++) {
				if (FrameService.trackActionSpecialType1.equals(activityidxx)) {// 抄送
					WfEntry.iv().specifyAssistantByWorkcode(participant,
							worklist.getWorkcode(), personxs[i], issync,
							opemode);
				} else if (FrameService.trackActionSpecialType2
						.equals(activityidxx)) {// 抄阅
					WfEntry.iv().specifyReaderByWorkcode(participant,
							worklist.getWorkcode(), personxs[i], opemode);
				} else if (FrameService.trackActionSpecialType3
						.equals(activityidxx)) {// 阅读
					WfEntry.iv().distributedSubmit(participant,
							worklist.getWorkcode(), personxs[i], opemode);
				}
				WfEntry.iv().specifyLimit(worklist.getWorkcode(), personxs[i],
						Long.valueOf(limitx));
			}
		}

	}

	// 转办分配
	private void assign_to_specify(String naturalname, Map activityMap,
			String activityid, TWfWorklist worklist, String participant)
			throws Exception {
		for (Iterator iterator = activityMap.keySet().iterator(); iterator
				.hasNext();) {
			String activityidx = (String) iterator.next();
			if (activityidx != null && activityidx.equals(activityid)) {
				String person = activityMap.get(activityidx).toString();

				person = StringUtils.substring(person.toString(), 0, person
						.toString().length() - 1);
				String[] persons = person.split(",");

				for (int i = 0; i < persons.length; i++) {
					WfEntry.iv().specifyzhuanbangByWorkcode(participant,
							worklist.getWorkcode(), persons[i]);
				}

			}
		}
	}

	public String findResourceTree(String naturalname) throws Exception {
		String str = "";
		List set = SecurityEntry.iv().listDirRs(naturalname);
		str = buildResourceJsonStr(set);
		return str;
	}

	public PageInfo findResourceNodeInfo(String type, String naturalname,
			String condition, String start, String limit) throws Exception {
		PageInfo pageinfo = new PageInfo();
		int startindex = 0;
		if (StringUtils.isNotEmpty(start)) {
			startindex = Integer.parseInt(start);
		}
		int pagesize = 15;
		if (StringUtils.isNotEmpty(limit)) {
			pagesize = Integer.parseInt(limit);
		}
		List list = SecurityEntry.iv().listRsInDir(naturalname, type,
				startindex, pagesize, condition);
		Long total_ = SecurityEntry.iv().countRsInDir(naturalname, type,
				condition);
		int total = total_.intValue();
		pageinfo.setTotalRows(total);
		pageinfo.setResultList(list);
		return pageinfo;
	}

	public ResourceNode getResourceNode(String naturalname, String node)
			throws Exception {
		String v1 = "";
		String v2 = "";
		if (StringUtils.isNotEmpty(naturalname)) {
			naturalname = URLDecoder.decode(naturalname, "UTF-8");
			v1 = StringUtils.substringBefore(naturalname, "[");
			v2 = StringUtils.substringBetween(naturalname, "[", "]");
		}
		ResourceNode recnode = new ResourceNode();
		recnode.setNode_(v2);
		recnode.setNodeid(v2);
		recnode.setNodecode(v2);
		recnode.setNodename(v1);
		recnode.setParentnode(node);
		return recnode;
	}

	private String buildResourceJsonStr(Collection col) {
		List jSonSet = new ArrayList();

		for (Iterator itr = col.iterator(); itr.hasNext();) {
			Resource d = (Resource) itr.next();
			d.setId(d.getResourceid());
			d.setText(d.getResourcename());
			jSonSet.add(JSONUtil2.fromBean(d));
		}

		String str = StringUtils.join(jSonSet.iterator(), ",");
		str = "[" + str + "]";
		return str;
	}

	public String saveConfirmStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		String naturalname = request.getParameter("naturalname");
		String lsh = request.getParameter("lsh");
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();

		DyFormData data = new DyFormData();
		data.setLsh(lsh);
		data.setStatusinfo("01");
		data.setFatherlsh("1");
		data.setFormcode(formcode);
		User user = getOnlineUser(request);

		int count = DyEntry.iv().queryDataNum(data, " or statusinfo = '02' ");
		if (count > 0) {
			json.put("tip", "已审核状态,不能进行其他操作!");
			json.put("error", "yes");
		} else {
			// data.setParticipant(user.getUserCode() + "[" +
			// user.getUserName()
			// + "]");
			boolean succ = DyEntry.iv().modifyData(formcode, data);
			if (succ) {
				json.put("tip", "确认成功!");
			} else {
				json.put("tip", "确认失败!");
				json.put("error", "yes");
			}
		}

		// START 脚本事件
		if (!json.containsKey("error")) {
			DyAnalysisXml dayx = new DyAnalysisXml();
			dayx.script(formcode, lsh, "Yesaffirm");// 确认
		}
		// END 脚本事件

		return json.toString();
	}

	public String saveUnConfirmStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();

		String naturalname = request.getParameter("naturalname");
		String lsh = request.getParameter("lsh");
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();

		DyFormData data = new DyFormData();
		data.setLsh(lsh);
		data.setStatusinfo("02");
		data.setFatherlsh("1");
		data.setFormcode(formcode);
		User user = getOnlineUser(request);

		int count = DyEntry.iv().queryDataNum(data, " or statusinfo = '01' ");
		if (count > 0) {
			json.put("tip", "已审核状态,不能进行其他操作!");
			json.put("error", "yes");
		} else {
			// data.setParticipant(user.getUserCode() + "[" +
			// user.getUserName()
			// + "]");

			boolean succ = DyEntry.iv().modifyData(formcode, data);
			if (succ) {
				json.put("tip", "反确认成功!");
			} else {
				json.put("tip", "反确认失败!");
				json.put("error", "yes");
			}
		}

		// START 脚本事件
		if (!json.containsKey("error")) {
			DyAnalysisXml dayx = new DyAnalysisXml();
			dayx.script(formcode, lsh, "Onaffirm");// 反确认
		}
		// END 脚本事件

		return json.toString();
	}

}
