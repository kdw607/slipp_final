package net.sleep.user.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.slipp.user.User;
import net.slipp.user.UserDAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import core.jdbc.DataAccessException;


@WebServlet("/api/users/find")
public class ApiFindUserServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String userId = req.getParameter("userId");
	
		if(userId == null){
			resp.sendRedirect("/slipp");
			return;			
		}
		
		UserDAO userDao = new UserDAO();
		
		try {
			
			User user = userDao.findByUserId(userId);
			if(user == null){
				return;
			}
			
			final GsonBuilder builder = new GsonBuilder();
			builder.excludeFieldsWithoutExposeAnnotation();
			final Gson gson = builder.create();
			
			String jsonData = gson.toJson(user);			
			resp.setContentType("application/json;charset=UTF-8");
			
			PrintWriter out = resp.getWriter();
			out.print(jsonData);
			
		} catch (DataAccessException e) {
		}
		
	}
	
}
