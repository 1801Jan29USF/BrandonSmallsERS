package com.revature.services;

import java.util.List;
import java.util.Vector;

import com.revature.DAO.ERSDAO;
import com.revature.DAO.ERSDAOJDBC;
import com.revature.beans.Reimbursement;

public class ReimbursementService {
	private ERSDAOJDBC reimbService = new ERSDAOJDBC();
	
	public List<Reimbursement> findAuthor(int id) {
		return reimbService.findAuthor(id);
	}
	
	public List<Reimbursement> findAll() {
		return reimbService.findAll();
	}
}
