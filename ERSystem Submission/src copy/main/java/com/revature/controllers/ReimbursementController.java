package com.revature.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.DAO.ERSDAO;
import com.revature.DAO.ERSDAOJDBC;
import com.revature.beans.Reimbursement;
import com.revature.exceptions.InvalidCredentialException;
import com.revature.file.io.ResponseUtil;
import com.revature.login.controller.DispatcherServlet;
import com.revature.services.ReimbursementService;


public class ReimbursementController implements HttpController {
	private static final String BY_REIMB_AUTHOR = "/reimbursement";
	private ReimbursementService reimbs = new ReimbursementService();
	private ResponseUtil respUtil = new ResponseUtil();
	private Logger log = Logger.getRootLogger();
	ERSDAO ersDao = new ERSDAOJDBC();
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String url = req.getPathInfo();
		log.trace("get request delegated to reimbursement controller");
		if(url.startsWith("/reimbursement")) {
			int id = DispatcherServlet.u.getId();
			int role = DispatcherServlet.u.getRoleID();
			if(role == 2) {
				System.out.println(id);
				log.trace("request made to find reimbursements with author id " + id);
				List<Reimbursement> reimb = reimbs.findAuthor(id);
				respUtil.writeObjectToResponse(reimb, resp);
			} else {
				System.out.println(id);
				log.trace("request made to find all reimbursements");
				List<Reimbursement> reimb = reimbs.findAll();
				respUtil.writeObjectToResponse(reimb, resp);
			}
			
		}
		
//		List<Reimbursement> reimb = new ArrayList<>();
//		ObjectMapper om = new ObjectMapper();
//		String json = om.writeValueAsString(reimb);
//		resp.getWriter().write(json);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		try {
			String json = req.getReader().lines().reduce((acc, cur) -> acc + cur).get();
			ObjectMapper om = new ObjectMapper();
			Reimbursement r = om.readValue(json, Reimbursement.class);
			
			log.trace("post request delegated to Reimbursement controller");
			ersDao.saveReimbursement(r);
			String json2 = om.writeValueAsString(r);
			resp.getWriter().write(json2);
			
		} catch (InvalidCredentialException e) {
			resp.setStatus(401);
		}
	}
	
	public void doPostUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		try {
			String json = req.getReader().lines().reduce((acc, cur) -> acc + cur).get();
			ObjectMapper om = new ObjectMapper();
			Reimbursement r = om.readValue(json, Reimbursement.class);
			
			if(r.getStatus() == 2) {
				log.trace("post request delegated to Reimbursement controller");
				ersDao.approveUpdate(r);
				String json2 = om.writeValueAsString(r);
				resp.getWriter().write(json2);
			} else if(r.getStatus() == 3) {
				log.trace("post request delegated to Reimbursement controller");
				ersDao.denyUpdate(r);
				String json2 = om.writeValueAsString(r);
				resp.getWriter().write(json2);
			}
			
		} catch (InvalidCredentialException e) {
			resp.setStatus(401);
		}
	}
	
	public void doPostDeny(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		try {
			String json = req.getReader().lines().reduce((acc, cur) -> acc + cur).get();
			ObjectMapper om = new ObjectMapper();
			Reimbursement r = om.readValue(json, Reimbursement.class);
			
			log.trace("post request delegated to Reimbursement controller");
			ersDao.denyUpdate(r);
			String json2 = om.writeValueAsString(r);
			resp.getWriter().write(json2);
			
		} catch (InvalidCredentialException e) {
			resp.setStatus(401);
		}
	}

	@Override
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub

	}
}
