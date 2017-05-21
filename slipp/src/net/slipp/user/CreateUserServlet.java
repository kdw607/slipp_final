package net.slipp.user;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.slipp.db.Database;


@WebServlet("/users/create")
public class CreateUserServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");
		String name = req.getParameter("name");
		String email = req.getParameter("email");

		User user = new User(userId, password, name, email);	
		UserDAO userDAO = new UserDAO();
		
		try {
			userDAO.addUser(user);
		} catch (SQLException e) {
		}
		
		resp.sendRedirect("/slipp");
	}
	
}