package oe.netone.buss.zb.finance;

import java.sql.Connection;
import java.util.List;

import oescript.parent.OeScript;

public class SoaScrpit extends OeScript{
	
	// �����ű�  naturalname:SOASCRIPT.SOASCRIPT.ZB.GETVIPLEVELBYPOINT
	public String todo1(){
		String sql="select * from DY_271334208897441 where column4 like '$(q)%'";
		System.out.println(sql);
				Connection con =db.con("DATASOURCE.DATASOURCE.DYFORM");
				List list=db.queryData(con, sql);
				System.out.println("testSize:"+list.size());
				java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
				String split = "";
				for (int i = 0; i < list.size(); i++) {
					System.out.println("testSize1:"+i);
					try{
					String jsonStr = net.sf.json.JSONObject.fromObject(list.get(i))
							.toString();
					System.out.println("testSize2:"+jsonStr);
					jsonBuffer.append(split);
					jsonBuffer.append(jsonStr);
					split = ",";
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				db.close(con);
				return "[" + jsonBuffer.toString() + "]";
	}

}
