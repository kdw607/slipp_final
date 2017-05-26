package net.slipp.user;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.slipp.support.JdbcTemplate;
import net.slipp.support.PreparedStatementSetter;
import net.slipp.support.RowMapper;
import net.slipp.support.bark_SelectJdbcTemplate;

public class UserDAO {

	public void addUser(User user) throws SQLException {
				
		//�뼱�굹誘몃꼫�뒪 �겢�옒�뒪
		JdbcTemplate jdbc = new JdbcTemplate();
		String sql = "insert into users values(?, ?, ?, ?)";
		jdbc.executeUpdate(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
	}

	public void removeUser(String userId) throws SQLException {

		JdbcTemplate jdbc = new JdbcTemplate();		
		String sql = "delete from users where userId = ?";
		jdbc.executeUpdate(sql, userId);
	}
	
	public void updateUser(User user) throws SQLException {		
		
		JdbcTemplate jdbc = new JdbcTemplate();
		String sql = "update users set password=?, name=?, email=? where userId=?";
		jdbc.executeUpdate(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
	}
	
	
	public User findByUserId(String userId) throws SQLException{		
		
		RowMapper<User> rm = new RowMapper<User>() {
			
			@Override
			public User mapRow(ResultSet rs) throws SQLException {
				return new User(
							rs.getString("userId"),
							rs.getString("password"),
			 				rs.getString("name"),
							rs.getString("email"));
			}
		};
		
		JdbcTemplate SJdbc = new JdbcTemplate();		
		String sql = "select * from users where userId = ?";
		return SJdbc.executeQuery(sql, rm, userId);
		
	}

	public List<User> findUsers() throws SQLException {
		
		RowMapper<User> rm = new RowMapper<User>() {
			
			@Override
			public User mapRow(ResultSet rs) throws SQLException {
				return new User(
							rs.getString("userId"),
							rs.getString("password"),
			 				rs.getString("name"),
							rs.getString("email"));
			}
		};
		
		JdbcTemplate SJdbc = new JdbcTemplate();		
		String sql = "select * from users";
		return SJdbc.list(sql, rm);
		
	}
}