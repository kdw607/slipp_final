package net.slipp.user;

import java.sql.SQLException;

import com.google.gson.annotations.Expose;

import net.slipp.db.Database;

public class User {
	
	@Expose(serialize = false)
	private String userId;
	@Expose
	private String password;
	@Expose
	private String name;
	@Expose
	private String email;
	

	public User(String userId, String password, String name, String email) {
		super();
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
	}
	
	
	
	public User() {
	}



	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean matchPassword(String newPassword) {
		
		return this.password.equals(newPassword);
	}
	

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name="
				+ name + ", email=" + email + "]";
	}


	public static boolean login(String userId, String password) throws UserNotFoundException, PasswordMismatchException{

		//User user = Database.findByUserId(userId);
		
		UserDAO userDAO = new UserDAO();
		User user = null;
		try {
			user = userDAO.findByUserId(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		if(user == null){
			throw new UserNotFoundException();
		}
		
		
		if(!user.matchPassword(password)){
			throw new PasswordMismatchException();
		}
		
		return true;
	}

}
