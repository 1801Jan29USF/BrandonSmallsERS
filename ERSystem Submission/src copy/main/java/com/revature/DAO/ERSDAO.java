package com.revature.DAO;

import com.revature.file.io.ConnectionUtil;
import com.revature.beans.Reimbursement;
import com.revature.beans.User;

public interface ERSDAO {
	
	// C
	int saveReimbursement(Reimbursement r);
	
	// R
	User getUser(String username, String password);
	Reimbursement getReimbursement(String username);
	
	// U
	void approveUpdate(Reimbursement r);
	void denyUpdate(Reimbursement r);
	
	// D
	//void delete();
}
