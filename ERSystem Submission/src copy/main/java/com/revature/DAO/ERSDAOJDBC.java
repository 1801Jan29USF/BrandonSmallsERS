package com.revature.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.file.io.ConnectionUtil;
import com.revature.login.controller.DispatcherServlet;


public class ERSDAOJDBC implements ERSDAO {
	
	private Logger log = Logger.getRootLogger();
	private static ConnectionUtil connUtil = ConnectionUtil.getConnectionUtil();
	
	public static void main(String[] args) { 
		//ERSDAO ersDao = new ERSDAOJDBC();
		//System.out.println(ersDao.getUser("bsmalls", "pass"));
		
	//	Reimbursement r = new Reimbursement(9.95, new Timestamp(System.currentTimeMillis()), "Enthoware", 2, 1, 4);
	//	System.out.println(ersDao.saveReimbursement(r));
	}
	
	@Override
	public int saveReimbursement(Reimbursement r) {
		log.trace("method called to submit Reimbursement");
		log.trace("Attempting to get connection to db");
		
		try (Connection conn = connUtil.getConnection()) {
			log.trace("connection established with db, creating prepared statement");
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ers_reimbursement(reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_status_id, reimb_type_id) VALUES \n" + 
					"(?,SYSTIMESTAMP,?,?,1,?)", new String[] {"reimb_id"}); 
			ps.setDouble(1,r.getAmount());
			ps.setString(2, r.getDescription());
			ps.setInt(3, DispatcherServlet.u.getId());
			//ps.setInt(4, 1);
			ps.setInt(4, r.getType());
			log.trace(r);
			int rowsInserted = ps.executeUpdate(); 
			log.debug("a query inserted " + rowsInserted + " row(s) into the db");
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				r.setId(rs.getInt(1));
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println();
            log.warn("failed to insert new reimbursement");
        }
		
		return 0;
	}

	@Override
	public User getUser(String username, String password) {
		log.trace("method called to get user");
		log.trace("Attempting to get connection to db");
		try (Connection conn = connUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_users WHERE ers_username=? AND ers_password=?");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				User u = new User(rs.getInt("ers_users_id"), rs.getString("ers_username"), rs.getString("ers_password"), rs.getString("user_first_name"), rs.getString("user_last_name"), rs.getString("user_email"), rs.getInt("user_role_id"));
				return u;
			} else {
				log.trace("Username or password invalid");
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.warn("failed to retreive user");
		}
		return null;
	}

	@Override
	public Reimbursement getReimbursement(String username) {
		int id = 0;
		log.trace("method called to get user Reimbursement");
		log.trace("Attempting to get connection to db");
		try (Connection conn = connUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("SELECT ers_users_id FROM ers_users WHERE ers_username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				id = rs.getInt("ers_users_id");
			}
			
			PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM ers_reimbursement WHERE reimb_author=? ORDER BY reimb_submitted");
			ps2.setInt(1, id);
			rs = ps2.executeQuery();
			while(rs.next()) {
				Reimbursement r = new Reimbursement(rs.getInt("reimb_id"), rs.getDouble("reimb_amount"), rs.getTimestamp("reimb_submitted"), rs.getTimestamp("reimb_resolved"), rs.getString("reimb_description"), rs.getInt("reimb_author"), rs.getInt("reimb_resolver"), rs.getInt("reimb_status_id"), rs.getInt("reimb_type_id"));
				return r;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.warn("failed to retreive Reimbursement");
		}
		
		return null;
	}

	
	public void approveUpdate(Reimbursement r) {
		log.trace("method called to update Reimbursement");
		log.trace("Attempting to get connection to db");
		try (Connection conn = connUtil.getConnection()) {
			//Statement s = conn.createStatement();
			 log.trace("Connection established with db, creating prepared statement.");
			 log.trace("reimbursement " + r.getId() + " about to be updated");
			 PreparedStatement ps = conn.prepareStatement("UPDATE ers_reimbursement SET reimb_resolved=?, reimb_status_id= 2, reimb_resolver=? WHERE reimb_id=?");
			ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			 ps.setInt(2, DispatcherServlet.u.getId());
			ps.setInt(3, r.getId());
			 int rowsInserted = ps.executeUpdate();            
	         log.debug("this query updated: " + rowsInserted + " row in the db.");
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to update Reimbursement");
		}
		
	}
	
	public void denyUpdate(Reimbursement r) {
		
		log.trace("method called to update Reimbursement");
		log.trace("Attempting to get connection to db");
		try (Connection conn = connUtil.getConnection()) {
			//Statement s = conn.createStatement();
			 log.trace("Connection established with db, creating prepared statement.");
			 log.trace("reimbursement " + r.getId() + " about to be updated");
			 PreparedStatement ps = conn.prepareStatement("UPDATE ers_reimbursement SET reimb_resolved=?, reimb_status_id= 3, reimb_resolver=? WHERE reimb_id=?");
			ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			 ps.setInt(2, DispatcherServlet.u.getId());
			ps.setInt(3, r.getId());
			 int rowsInserted = ps.executeUpdate();            
	         log.debug("this query updated: " + rowsInserted + " row in the db.");
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to update Reimbursement");
		}
		
	}
	
	public List<Reimbursement> findAuthor(int id) {
		log.trace("method called to select Reimbursment by id " + id);
		log.trace("Attempting to get connection to db");
		try (Connection conn = connUtil.getConnection()) {
			List<Reimbursement> userReimbursements = new ArrayList<>();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_reimbursement WHERE reimb_author=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Reimbursement r = new Reimbursement(rs.getInt("reimb_id"), rs.getDouble("reimb_amount"), rs.getTimestamp("reimb_submitted"), rs.getTimestamp("reimb_resolved"), rs.getString("reimb_description"), rs.getInt("reimb_author"), rs.getInt("reimb_resolver"), rs.getInt("reimb_status_id"), rs.getInt("reimb_type_id"));
				userReimbursements.add(r);
			}
			log.trace("retreived all user reimbursements");
			return userReimbursements;
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to retreive reimbursements");
		}
		return null;
	}
	
	public List<Reimbursement> findAll() {
		log.trace("method called to select all Reimbursments");
		log.trace("Attempting to get connection to db");
		try (Connection conn = connUtil.getConnection()) {
			List<Reimbursement> userReimbursements = new ArrayList<>();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_reimbursement");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Reimbursement r = new Reimbursement(rs.getInt("reimb_id"), rs.getDouble("reimb_amount"), rs.getTimestamp("reimb_submitted"), rs.getTimestamp("reimb_resolved"), rs.getString("reimb_description"), rs.getInt("reimb_author"), rs.getInt("reimb_resolver"), rs.getInt("reimb_status_id"), rs.getInt("reimb_type_id"));
				userReimbursements.add(r);
			}
			log.trace("retreived all user reimbursements");
			return userReimbursements;
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to retreive reimbursements");
		}
		return null;
	}
	
}
