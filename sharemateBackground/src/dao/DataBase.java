package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {
	/**
	 * 获取对数据库的链接
	 */
	public static Connection getConnection() {
		Connection conn=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/sharemate?serverTimezone=GMT&useUnicode=true&characterEncoding=utf-8&useSSL=false", 
					"root", "FJXfjx&6");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void close(PreparedStatement pstmt) {
		if(pstmt != null) {
			try {
				pstmt.close();
				pstmt = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void close(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
