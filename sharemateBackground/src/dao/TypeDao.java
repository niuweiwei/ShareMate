package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DataBase;
import bean.TypeBean;

public class TypeDao {
	/**
	 * 根据typeid得到typebean类型对象
	 */
	public TypeBean getTypeById(int typeId) {
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		String sql="select * from type where type_id=?";
		TypeBean type=new TypeBean();
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, typeId);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				type.setTypeId(typeId);
				type.setTypeName(rs.getString("type_name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
		return type;
	}
	
}
