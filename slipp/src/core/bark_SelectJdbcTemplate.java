package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import core.jdbc.ConnectionManager;
import net.slipp.user.User;

public abstract class bark_SelectJdbcTemplate {
		
	//결과값을 알수 없어서 User -> Object로
	public Object executeQuery(String sql) throws SQLException{
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			setParameters(pstmt);	
			
			rs = pstmt.executeQuery();
			
			return maoRow(rs);			
		}finally{
			if(rs != null){
				rs.close();
			}
			
			if(pstmt != null){
				pstmt.close();
			}
			
			if(conn != null){
				conn.close();
			}
		}
	}
	
	public abstract void setParameters(PreparedStatement pstmt)throws SQLException;
	public abstract User maoRow(ResultSet rs) throws SQLException;
}
