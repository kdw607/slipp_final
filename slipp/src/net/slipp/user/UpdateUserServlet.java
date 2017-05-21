package net.slipp.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import net.slipp.support.MyValidatorFactory;

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
		
		Validator validator = MyValidatorFactory.createValidator();
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		
		if(constraintViolations.size() > 0){
			req.setAttribute("isUpdate", true);
			req.setAttribute("user", user);
			String errorMessage = constraintViolations.iterator().next().getMessage();
			forwardJSP(req, resp, errorMessage);
			return ;
		}	
		
		UserDAO userDao = new UserDAO();
		
		try {
			userDao.updateUser(user);
		} catch (SQLException e) {
		}
		
		resp.sendRedirect("/slipp");
		
	}
	
	private void forwardJSP(HttpServletRequest req, HttpServletResponse resp, String errorMessage) throws ServletException, IOException {
		
		req.setAttribute("errorMessage", errorMessage);
		RequestDispatcher rd = req.getRequestDispatcher("/form.jsp");
		rd.forward(req, resp);
	}
	
}