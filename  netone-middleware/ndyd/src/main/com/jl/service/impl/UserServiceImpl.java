/**
 * 
 */
package com.jl.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import oe.serialize.dao.PageInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jl.common.JSONUtil2;
import com.jl.common.MD5Util;
import com.jl.common.app.SpringBeanUtilExam;
import com.jl.common.security3a.SecurityEntry;
import com.jl.common.workflow.DbTools;
import com.jl.dao.CommonDAO;
import com.jl.entity.User;
import com.jl.service.BaseService;
import com.jl.service.UserService;

/**
 * 用户业务管理实现
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-11-24 下午02:48:50
 * @history
 */
public class UserServiceImpl extends BaseService implements UserService {

	/** 日志 */
	private final Logger log = Logger.getLogger(UserServiceImpl.class);

	private final boolean enableSyncComponent = true;// 是否同步组件

	private final boolean enableSyncExam = ("yes".equalsIgnoreCase(config
			.getString("syncexam"))) ? true : false;

	private CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public void queryInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Map conditionMap = new HashMap();
		String condition = request.getParameter("condition");
		String special = request.getParameter("special");
		JSONObject paramJson = null;
		if (StringUtils.isNotEmpty(condition)) {
			paramJson = JSONObject.fromObject(condition);
		}
		User userX = getOnlineUser(request);
		conditionMap.put("permission", userX.getDepartmentId());
		if ("adminx".equals(userX.getUserCode())) {// 管理员
			conditionMap.put("permission", "0");
		}
		if ("1".equals(special)) {
			conditionMap.put("permission", "0");
		}
		if (paramJson.containsKey("departmentId")) {
			conditionMap.put("departmentId", paramJson
					.getString("departmentId"));
			conditionMap.put("departmentId_", paramJson
					.getString("departmentId"));
		}
		if (paramJson.containsKey("userCode")) {
			conditionMap.put("userCode", paramJson.getString("userCode"));
		}

		if (paramJson.containsKey("list")) {
			String list = paramJson.getString("list");
			if (StringUtils.isNotEmpty(list)) {
				conditionMap.put("list", list);
				if ("all".equals(list)) {
					conditionMap.put("departmentId", "");
				}
			}
		}
		String start = request.getParameter("start");// 开始索引
		String limit = request.getParameter("limit");// 开始索引
		PageInfo obj = new PageInfo();
		conditionMap.put("startIndex", start);
		conditionMap.put("pageSize", limit);
		try {
			obj = commonDAO.selectForPage("User.queryUserPageCount",
					"User.queryUserResultPage", conditionMap, obj);
			int total = obj.getTotalRows();
			List result = obj.getResultList();
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				Map user = (Map) iterator.next();

				String types = (String) user.get("types");
				if (BussVar.BUSSTYPE_4.equals(types)) {
					user.put("types", "客户");
				} else if (BussVar.BUSSTYPE_X.equals(types)) {
					user.put("types", "公司");
				} else {
					user.put("types", "未知");
				}

				String jsonStr = JSONUtil2.fromBean(user, "yyyy-MM-dd")
						.toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}
			super.writeJsonStr(response, super.buildJsonStr(total, jsonBuffer
					.toString()));
		} catch (Exception e) {
			log.error("出错了!", e);
		}
	}

	public void queryInfoX(HttpServletRequest request,
			HttpServletResponse response) {
		Map conditionMap = new HashMap();
		String includedept = request.getParameter("includedept");
		String condition = request.getParameter("condition");
		JSONObject paramJson = null;
		if (StringUtils.isNotEmpty(condition)) {
			paramJson = JSONObject.fromObject(condition);
		}
		if (paramJson.containsKey("departmentId")) {
			conditionMap.put("departmentId", paramJson
					.getString("departmentId"));
			conditionMap.put("departmentId_", paramJson
					.getString("departmentId"));
		}
		if (paramJson.containsKey("userCode")) {
			conditionMap.put("userCode", paramJson.getString("userCode"));
		}

		if (paramJson.containsKey("list")) {
			String list = paramJson.getString("list");
			if (StringUtils.isNotEmpty(list)) {
				conditionMap.put("list", list);
				if ("all".equals(list)) {
					conditionMap.put("departmentId", "");
				}
			}
		}
		if (StringUtils.isNotEmpty(includedept)) {
			conditionMap.put("includedept", includedept.trim());
		} else {
			conditionMap.put("includedept", 0);
		}
		conditionMap.put("enabled", 1);
		String start = request.getParameter("start");// 开始索引
		String limit = request.getParameter("limit");// 开始索引
		PageInfo obj = new PageInfo();
		conditionMap.put("startIndex", start);
		conditionMap.put("pageSize", limit);
		try {
			obj = commonDAO.selectForPage("User.queryUserPageCountX",
					"User.queryUserResultPageX", conditionMap, obj);
			int total = obj.getTotalRows();
			List result = obj.getResultList();
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				Map user = (Map) iterator.next();

				String jsonStr = JSONUtil2.fromBean(user, "yyyy-MM-dd")
						.toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}
			super.writeJsonStr(response, super.buildJsonStr(total, jsonBuffer
					.toString()));
		} catch (Exception e) {
			log.error("出错了!", e);
		}
	}

	public void saveOrUpdate(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		String userId = request.getParameter("userId");
		User user = getBean(request);
		JSONObject json = new JSONObject();
		try {
			Map extAttribute = new HashMap();
			extAttribute.put("orders", user.getOrders());
			if (StringUtils.isNotEmpty(userId)) {// 保存
				user = (User) commonDAO.update("User.updateUser", user);

				if (enableSyncComponent) {
					getSecurityAPI(request).editAccount(user.getDepartmentId(),
							user.getUserCode(), user.getUserName(),
							extAttribute);
				}
				if (enableSyncExam) {
					SpringBeanUtilExam.getInstance().updateStudent(user);
				}
				json.put("tip", "保存成功!");
			} else {// 创建
				if (findUserCodeIsExist(request)) {
					json.put("tip", "用户编码已存在!");
				} else {
					user.setStatus("1");
					user = (User) commonDAO.insert("User.insertUser", user);
					if (enableSyncComponent) {
						getSecurityAPI(request).newAccount(
								user.getDepartmentId(), user.getUserCode(),
								user.getUserName(), extAttribute);
					}
					if (enableSyncExam) {
						SpringBeanUtilExam.getInstance().insertStudent(user);
					}
					json.put("tip", "保存成功!");
				}
			}
		} catch (Exception e) {
			json.put("error", "yes");
			json.put("tip", "保存失败!");
			log.error("保存失败!", e);
			throw new RuntimeException("保存失败!");
		} finally {
			super.writeJsonStr(response, json.toString());
		}

	}

	public void loadInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String userId = request.getParameter("userId");

		try {
	
			if(StringUtils.isEmpty(userId)){
				userId=SecurityEntry.iv().onlineUserSessionId(request);
				userId = (String) commonDAO.findForObject("User.selectUserIdByUserCode",
						userId);
			}
			Map user = (Map) commonDAO.findForObject("User.loadUserById",
					userId);
			request.setAttribute("user", user);
		} catch (Exception e) {
			log.error("加载数据失败!", e);
		}
	}

	public void deleteInfo(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		String userIdStr = request.getParameter("userId") == null ? ""
				: request.getParameter("userId").trim();
		String userCodeStr = request.getParameter("userCode") == null ? ""
				: request.getParameter("userCode").trim();

		try {
			String[] userIdStr1 = userIdStr.split(",");
			List deleteList = new ArrayList();
			for (int i = 0; i < userIdStr1.length; i++) {
				deleteList.add(userIdStr1[i]);
			}
			commonDAO.deleteBatch("User.deleteUser", deleteList);
			String[] userCodeStr1 = userCodeStr.split(",");
			for (int i = 0; i < userCodeStr1.length; i++) {
				if (enableSyncComponent) {
					getSecurityAPI(request).deleteAccount(userCodeStr1[i]);
				}
				if (enableSyncExam) {
					SpringBeanUtilExam.getInstance().deleteStudent(userCodeStr1[i]);
				}
			}
			json.put("tip", "删除成功!");
		} catch (Exception e) {
			json.put("error", "yes");
			json.put("tip", "删除失败!");
			log.error("删除数据信息出错!", e);
			throw new RuntimeException("删除失败!");
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// ----Private Methods

	private User getBean(HttpServletRequest request) {
		User user = new User();
		super.setJavaBean(request, user);
		String password = request.getParameter("password");

		String zw = request.getParameter("zw");// 职务
		user.setZw(zw);
		int accounttypes = Integer.parseInt(request
				.getParameter("accounttypes"));// 帐号类型
		user.setAccounttypes(accounttypes);
		String ltime = request.getParameter("leaveTime");// 离开时间
		String btime = request.getParameter("backTime");// 回去时间
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date leavetime = null;
		Date backtime = null;
		if (StringUtils.isNotEmpty(ltime)) {
			try {
				leavetime = format.parse(ltime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			user.setLeavetime(leavetime);
		}

		if (StringUtils.isNotEmpty(btime)) {
			try {
				backtime = format.parse(btime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			user.setBacktime(backtime);
		}

		String dlr = request.getParameter("departmentNamex");// 代理人
		user.setDlr(dlr);
		String notice_ = request.getParameter("notice");
		if (StringUtils.isNotEmpty(notice_) && !"undefined".equals(notice_)) {
			int notice = Integer.parseInt(notice_);// 通知方式
			user.setNotice(notice);
		}
		if (StringUtils.isNotEmpty(password)) {
			password = MD5Util.getMD5String(password);
			user.setPassword(password);
		}
		user.setCreatedDate(new Date());
		return user;
	}

	/**
	 * 判断用户编码是否已存在
	 * 
	 * @param request
	 * @return
	 */
	private Boolean findUserCodeIsExist(HttpServletRequest request)
			throws Exception {
		Boolean flag = true;
		String userCode = request.getParameter("userCode");
		Integer result = (Integer) commonDAO.findForObject(
				"User.findUserCodeIsExist", userCode);
		if (result == 0) {
			flag = false;
		} else {
			flag = true;
		}

		return flag;
	}

}
