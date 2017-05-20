package net.slipp.user;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/users/update")
public class UpdateUserServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		HttpSession session = req.getSession();
		String sessionUserId = SessionUtils.getStringValue(session, LoginServlet.SESSION_USER_ID);
		
		if(sessionUserId == null){
			resp.sendRedirect("/slipp");
			return ;
		}
		
		String userId = SessionUtils.getStringValue(session, LoginServlet.SESSION_USER_ID);
				
		if (!sessionUserId.equals(userId)) {
			resp.sendRedirect("/slipp");
			return ;
		}

		String password = req.getParameter("password");
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		
		User user = new User(userId, password, name, email);
		UserDAO userDao = new UserDAO();
		
		try {
			userDao.updateUser(user);
		} catch (SQLException e) {
		}
		
		resp.sendRedirect("/slipp");
		
		
	}
	
}