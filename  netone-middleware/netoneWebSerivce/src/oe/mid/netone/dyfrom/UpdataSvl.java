package oe.mid.netone.dyfrom;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cav.bean.logic.column.TCsColumn;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.jl.common.app.AppEntry;
import com.jl.common.app.impl2.AnalysisAppFirst;
import com.jl.common.app.impl2.AppFirst;
import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyFormData;
import com.jl.common.netone.UmsProtecte;

public class UpdataSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UpdataSvl() {
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
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String formcode=null;
		boolean fal=false;
		String appname= request.getParameter("appname");
		String parentId = request.getParameter("parentid");
		String userid = request.getParameter("userid");
		String lsh = request.getParameter("lsh");
		if (StringUtils.isEmpty(parentId)) {
			parentId = "1";
		}
		try {
			formcode = AppEntry.iv().loadApp(appname).getDyformCode_();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		DyFormData dydata = new DyFormData();
		dydata.setFatherlsh(parentId);
		dydata.setParticipant(userid);
		dydata.setLsh(lsh);

		try {
			List listz = DyEntry.iv().fetchColumnList(formcode);
			for (Iterator iterator = listz.iterator(); iterator.hasNext();) {
				TCsColumn object = (TCsColumn) iterator.next();
				String columnId=object.getColumnid().toLowerCase();
				if(columnId.startsWith("column")){
					String value = request.getParameter(columnId);
					if (StringUtils.isNotEmpty(value)) {
						BeanUtils.setProperty(dydata, columnId, value);
					}
				}
			}
			fal = DyEntry.iv().modifyData(formcode, dydata);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       response.getWriter().print(fal);
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
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
