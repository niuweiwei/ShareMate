package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.TypeBean;

public class TitleDao {

	public void insertTitle(int userId,int typeId) {
		Connection conn = null;
		String sql = "insert into title(user_id,type_id) values(?,?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, typeId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
	}
	/**
	 * 根据userId取type
	 */
	public List<Integer> getType(int userId){
			List<Integer> type = new ArrayList<>();
		   Connection conn = DataBase.getConnection();
			PreparedStatement pstmt = null;
			String sql="select type_id from title where user_id=?";
			ResultSet rs = null;
			try {
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, userId);
				rs=pstmt.executeQuery();
				while(rs.next()) {
					type.add(rs.getInt("type_id"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				DataBase.close(rs);
				DataBase.close(pstmt);
				DataBase.close(conn);
			}
		return type;
	}
}
