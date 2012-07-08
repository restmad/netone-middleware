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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.jl.common.app.AppEntry;
import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyFormData;

public class AddSvl extends HttpServlet {

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
		request.setCharacterEncoding("utf-8");
		String formcode = null;
		String appname = request.getParameter("appname");
		String parentId = request.getParameter("parentId");
		String userid = request.getParameter("userid");

		DyFormData dydata = new DyFormData();
		dydata.setFatherlsh(parentId);
		dydata.setParticipant(userid);
		if (StringUtils.isEmpty(parentId)) {
			parentId = "1";
		}
		try {
			formcode = AppEntry.iv().loadApp(appname).getDyformCode_();
			List list = DyEntry.iv().fetchColumnList(formcode);
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				TCsColumn object = (TCsColumn) iterator.next();
				String columnId=object.getColumnid().toLowerCase();
				if(columnId.startsWith("column")){
					String value = request.getParameter(columnId);
					if (StringUtils.isNotEmpty(value)) {

						BeanUtils.setProperty(dydata, columnId, value);

					}
				}
			}
			String str = DyEntry.iv().addData(formcode, dydata);
			response.getWriter().print(str);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}


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
