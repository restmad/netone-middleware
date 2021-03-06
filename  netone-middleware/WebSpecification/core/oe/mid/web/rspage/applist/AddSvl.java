package oe.mid.web.rspage.applist;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebTip;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ApplicationRmi;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsApplication;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import org.apache.commons.lang.StringUtils;


public class AddSvl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static ResourceBundle web = ResourceBundle.getBundle(
			"applicationweb", Locale.CHINESE);

	/**
	 * Constructor of the object.
	 */
	public AddSvl() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);

		String loginuser = oluser.getLoginname();
		boolean isAdmin = false;
		try {
			CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
			String admin = (String) cupm.fetchConfig("admin");
			if (!loginuser.equals(admin)) {
				WebTip.htmlInfo("您不是管理员，无权管理目录结构", true, response);
				return;
			}
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ApplicationRmi app = null;
		try {
			// 读取名为resource的rmi服务
			app = (ApplicationRmi) RmiEntry.iv("application");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		ResourceRmi rsrmi = null;
		try {
			// 读取名为resource的rmi服务
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		request.setAttribute("selType", TypeReference.info);

		// 入口参数
		String pagename = request.getParameter("pagename");
		if (StringUtils.isNotEmpty(pagename)) {
			request.setAttribute("pagename", pagename);
		}

		String task = request.getParameter("task");
		// 新建子结点
		if ("addsave".equals(task)) {
			// 页面参数
			String newappname = request.getParameter("name");// 中文名称
			String newappnaturalname = request.getParameter("naturalname");// 名称
			String description = request.getParameter("description");// 描述
			String types = request.getParameter("apptype");// 类型
			String active = request.getParameter("active");// 有效
			String extendattribute = request.getParameter("extendattribute");// 扩展属性

			UmsApplication umsapp = new UmsApplication();
			umsapp.setName(newappname);
			umsapp.setNaturalname(newappnaturalname);
			umsapp.setDescription(description);
			umsapp.setPassword("1");
			umsapp.setApptype(types);
			umsapp.setCreated(new Date(System.currentTimeMillis()));
			umsapp.setExtendattribute(extendattribute);
			if (StringUtils.isEmpty(active)) {
				active = "0";
			}
			umsapp.setActive(active);
			String appid = app.create(umsapp);

			if (appid == null) {
				request.setAttribute("CreateSuccess", "n");
				request.setAttribute("errorinfo", "名称已经存在");
			} else {
				request.setAttribute("CreateSuccess", "y");
			}
		}
		request.getRequestDispatcher(web.getString(pagename + ".create"))
				.forward(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {

	}

}
