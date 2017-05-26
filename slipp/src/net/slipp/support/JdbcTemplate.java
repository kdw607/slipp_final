package net.slipp.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.slipp.user.User;
import net.slipp.user.UserDAO;

public class JdbcTemplate {
	
	public void executeUpdate(String sql, PreparedStatementSetter pss) throws SQLException {
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pss.setParameters(pstmt);
			
			pstmt.executeUpdate();
			
		} finally{
			
			if(pstmt != null){
				pstmt.close();
			}
			
			if(conn != null){
				conn.close();
			}
		}
	}
	
	
	public void executeUpdate(String sql, Object... parameters) throws SQLException {

		executeUpdate(sql, createPreparedstatementSetter(parameters));
		
	}

	
	public <T> T executeQuery(String sql, RowMapper<T> rm, PreparedStatementSetter pss) throws SQLException{
		List<T> list = list(sql, rm, pss);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	
	public <T> T executeQuery(String sql, RowMapper<T> rm, Object... parameObjects) throws SQLException{
		
		return executeQuery(sql, rm, createPreparedstatementSetter(parameObjects));
	}

	public <T> List<T> list(String sql, RowMapper<T> rm, PreparedStatementSetter pss) throws SQLException{
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pss.setParameters(pstmt);
			
			rs = pstmt.executeQuery();
			
			List<T> list = new ArrayList<T>();
			while (rs.next()) {
				list.add(rm.mapRow(rs));
			}
			return list;
					
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
	
	public <T> List<T> list(String sql, RowMapper<T> rm, Object... parameObjects) throws SQLException{
		
		return list(sql, rm, createPreparedstatementSetter(parameObjects));
	}


	private PreparedStatementSetter createPreparedstatementSetter(
			Object... parameObjects) {
				return new PreparedStatementSetter(){
					
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				for (int i = 0; i < parameObjects.length; i++) {
					pstmt.setObject(i+1,  parameObjects[i]);
				}
			}
		};
	}
}