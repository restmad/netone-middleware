package oe.netone.buss.zb.singleproduct;

public class Dsell_script {
	
	public String todo1(){
		String sql="select * from $(sr_table)  where column4 ike '%$(q)%'";

		Connection con =db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list=db.queryData(con, sql);

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
