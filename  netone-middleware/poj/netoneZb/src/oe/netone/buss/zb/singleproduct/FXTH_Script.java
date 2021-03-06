package oe.netone.buss.zb.singleproduct;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oescript.parent.OeScript;

public class FXTH_Script extends OeScript {
	public void queRen() {
		try {
			dy.set("$(lsh)" + ":" + "$(formcode)", "column7", "确认");
			dy
					.set("$(lsh)" + ":" + "$(formcode)", "column11",
							"$(participant)");
			SimpleDateFormat dateformat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String a1 = dateformat.format(new java.util.Date());
			dy.set("$(lsh)" + ":" + "$(formcode)", "column12", a1);

			Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
			List list = db
					.queryData(
							con,
							"select t.column3 TMH from DY_661338441749391 t,DY_661338441749392 t2 where t.fatherlsh=t2.lsh and t2.lsh='$(lsh)'");
			db.close(con);
			con = db.con("DATASOURCE.DATASOURCE.DYFORM");
			StringBuffer par = new StringBuffer();
			String split = "";
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
				par.append(split + "'" + object.get("TMH").toString() + "'");
				split = ",";
			}
			if (list.size() > 0) {
				String sql = "update DY_661338441749388 set STATUSINFO='02',column24='已退货' where column3 in ("
						+ par.toString() + ") and STATUSINFO='01'";
				db.execute(con, sql);
				db.close(con);
				con = db.con("DATASOURCE.DATASOURCE.DYFORM");
				sql = "update DY_661338441749388 set STATUSINFO='01',column24='已退回' where column3 in ("
						+ par.toString()
						+ ") and fatherlsh in (select lsh from DY_661338441749389 where column12 = '0001')";
				db.execute(con, sql);
			}
			db.close(con);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
