package net.slipp.user;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.bark_SelectJdbcTemplate;
import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;

public class UserDAO {

	public void addUser(User user){
				
		//�뼱�굹誘몃꼫�뒪 �겢�옒�뒪
		JdbcTemplate jdbc = new JdbcTemplate();
		String sql = "insert into users values(?, ?, ?, ?)";
		jdbc.executeUpdate(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
	}

	public void removeUser(String userId){

		JdbcTemplate jdbc = new JdbcTemplate();		
		String sql = "delete from users where userId = ?";
		jdbc.executeUpdate(sql, userId);
	}
	
	public void updateUser(User user){		
		
		JdbcTemplate jdbc = new JdbcTemplate();
		String sql = "update users set password=?, name=?, email=? where userId=?";
		jdbc.executeUpdate(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
	}
	
	
	public User findByUserId(String userId){		
		
		RowMapper<User> rm =  rs ->
				new User(
					rs.getString("userId"),
					rs.getString("password"),
	 				rs.getString("name"),
					rs.getString("email"));
		
				JdbcTemplate SJdbc = new JdbcTemplate();		
				String sql = "select * from users where userId = ?";
				return SJdbc.executeQuery(sql, rm, userId);

	}

	public List<User> findUsers(){
		
		RowMapper<User> rm = rs -> 
					new User(
							rs.getString("userId"),
							rs.getString("password"),
			 				rs.getString("name"),
							rs.getString("email"));
		
		JdbcTemplate SJdbc = new JdbcTemplate();		
		String sql = "select * from users";
		return SJdbc.list(sql, rm);
		
	}
}