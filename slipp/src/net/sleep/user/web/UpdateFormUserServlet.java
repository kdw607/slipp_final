package net.sleep.user.web;

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

import core.MyValidatorFactory;
import core.SessionUtils;
import net.slipp.user.User;
import net.slipp.user.UserDAO;


//Contoller
@WebServlet("/users/update_Form")
public class UpdateFormUserServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String userId = SessionUtils.getStringValue(session, LoginServlet.SESSION_USER_ID);
		
		if(userId==null){
			resp.sendRedirect("/");
			return ;
		}
		
		System.out.println("User Id : " + userId);
		

		
		UserDAO userDao = new UserDAO();							//model
		
			User user = userDao.findByUserId(userId);				//model
			req.setAttribute("isUpdate", true);
			req.setAttribute("user", user);
			RequestDispatcher rd = req.getRequestDispatcher("/form.jsp");
			rd.forward(req, resp);
		
	}
}