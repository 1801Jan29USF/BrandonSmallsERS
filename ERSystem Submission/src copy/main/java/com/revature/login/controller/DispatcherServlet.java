package com.revature.login.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlets.DefaultServlet;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.DAO.ERSDAOJDBC;
import com.revature.beans.User;
import com.revature.controllers.ReimbursementController;
import com.revature.controllers.UserController;
import com.revature.services.UserService;

public class DispatcherServlet extends DefaultServlet {
	private Logger log = Logger.getRootLogger();
	private ReimbursementController rc = new ReimbursementController();
	private UserController uc = new UserController();
	private ERSDAOJDBC userService = new ERSDAOJDBC();
	
	public static User u = new User();
	
	protected void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {    
       super.service(request, response);
       response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
       response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
       response.addHeader("Access-Control-Allow-Headers","Origin, Methods, Credentials, X-Requested-With, Content-Type, Accept");
       response.addHeader("Access-Control-Allow-Credentials", "true");
       response.setContentType("application/json");
    }


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.trace(request.getParameter("username"));
		String url = request.getPathInfo();
		log.trace("Get request made with path " + url);
		if (url.startsWith("/static/")) {
			super.doGet(request, response); // all for default handling for static content
			return;
		} else {
			if (url.startsWith("/users")) {
				uc.doGet(request, response);
			} else if (url.startsWith("/reimbursement")) {
				rc.doGet(request, response);
			} else {
				log.trace("url not mapped");
				request.getRequestDispatcher("static/index.html").forward(request, response);
			}
		}
	}

	@Override
	/*protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String url = request.getPathInfo();
		log.trace("Get request made with path " + url);
		if (url.startsWith("/users")) {
			uc.doGet(request, response);
		} else if (url.startsWith("/reimbursement")) {
			rc.doGet(request, response);
		} else {
			log.trace("url not mapped");
			request.getRequestDispatcher("static/index.html").forward(request, response);
		}
	}*/
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // using JSON
		String url = request.getPathInfo();
		if (url.startsWith("/login")) {
			String json = request.getReader().lines().reduce((acc, cur) -> acc + cur).get();
			log.trace("json " + json);
			ObjectMapper om = new ObjectMapper();
			User credentials = (User) om.readValue(json, User.class);
			log.trace(credentials);
			u = userService.getUser(credentials.getUsername(), credentials.getPassword());
			log.trace(u);
        
			if (u != null) {
				HttpSession sess = request.getSession();
				sess.setAttribute("user", u);
				String respjson = om.writeValueAsString(u);
				response.getWriter().write(respjson);
			} else {
				response.setStatus(401);
			}
		} else if (url.startsWith("/reimbursement")) {
			rc.doPost(request, response);
		} else if (url.startsWith("/update")) {
			rc.doPostUpdate(request, response);
		}
    }
}