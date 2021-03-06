package oe.security3a.seucore.permission.tag;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

/**
 * 通用数据库连接工具
 * 
 * @since 1.0
 * @author chen.jia.xun(Robanco) <br>
 *         <br>
 *         mail:chenjiaxun@oesee.com <br>
 * 
 */
public class DbTools {

	/**
	 * 获得数据库连接,连接参考来自配置文件 dbconfig.properties
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Connection fetchConnection(String driver, String url,
			String username, String password) throws Exception {
		String _DRIVER = driver;
		String _URL = url;
		String _USERNAME = username;
		String _PASSWORD = password;

		Class.forName(_DRIVER).newInstance();
		return DriverManager.getConnection(_URL, _USERNAME, _PASSWORD);
	}

	/**
	 * 执行SQL语句,返回SQl的生效结果,如果值为-1表示该SQL是查询语句,如果是insert,update,delete的sql
	 * 那么将返回被影响数据库的记录个数,如果返回值为0,可能存在两种情况:<br>
	 * 1: SQl是DDL语句 <br>
	 * 2: SQL是insert,update,delete语句,但是执行结果没有修改数据库的记录信息
	 * 
	 * @param sql
	 * 
	 * 
	 */
	public static int execute(String sql, Connection con) {
		try {
			// 获得Statment句柄
			Statement st = con.createStatement();
			// 在连接的目标数据库上执行SQL
			st.execute(sql);
			// 返回SQL的执行结果
			return st.getUpdateCount();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("SQL执行失败:" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 带事务模式<br>
	 * 执行SQL语句,返回SQl的生效结果,如果值为-1表示该SQL是查询语句,如果是insert,update,delete的sql
	 * 那么将返回被影响数据库的记录个数,如果返回值为0,可能存在两种情况:<br>
	 * 1: SQl是DDL语句 <br>
	 * 2: SQL是insert,update,delete语句,但是执行结果没有修改数据库的记录信息 <br>
	 * <br>
	 * 需要注意的是如果需要使用事务,那么请先将con的autocommit设置为false
	 * 
	 * @param sql
	 * 
	 * 
	 */
	public static int executeTransaction(String sql, Connection con) {

		try {

			// 获得Statment句柄
			Statement st = con.createStatement();
			// 在连接的目标数据库上执行SQL
			st.execute(sql);
			// 返回SQL的执行结果
			return st.getUpdateCount();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("SQL执行失败:" + e.getMessage());
		}
	}

	/**
	 * 执行查询操作
	 * 
	 * @param sql
	 *            查询语句
	 * @return sql的执行结果集,存储模式List<Map> 其中map中以column-value的模式保存数据库的数据<br>
	 *         如果参数中的sql不是查询语句那么返回结果为null
	 */
	public static List queryData(String sql, Connection con) {
		// 保存查询的结果信息
		List list = new ArrayList();

		try {

			// 获得Statment句柄
			Statement st = con.createStatement();
			// 在连接的目标数据库上执行SQL
			boolean sqltype = st.execute(sql);
			if (!sqltype) {// 如果不是查询语句那么返回空
				return null;
			}
			// 获得查询结果集
			ResultSet rs = st.getResultSet();
			// 获得查询结果集中的表字段信息
			ResultSetMetaData metaData = rs.getMetaData();

			while (rs.next()) {
				// 创建一个map对象,来存储一条记录信息
				Map preRecord = new HashMap();
				// 遍历结果集中的字段,将当前行的数据库记录数据,写入map对象中
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					// 获得字段名
					String columnname = metaData.getColumnName(i);
					// 获得字段的值
					Object value = rs.getObject(columnname);
					// 写入一个字段的值
					preRecord.put(columnname, value);
				}
				// 将一条记录存入动态数组
				list.add(preRecord);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}
}
