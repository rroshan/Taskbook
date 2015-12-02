package com.taskbook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.taskbook.service.UserProfileService;
import com.taskbook.util.Constants;
import com.taskbook.bo.UserProfile;
import com.taskbook.dao.ConnectionFactory;
import com.taskbook.dao.FriendsDAO;

public class FriendsDAOMySQLImpl implements FriendsDAO {
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet set;

	@Override
	public void sendRequest(String userEmail, String recipientEmail) {
		// TODO Auto-generated method stub
		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "insert into friendship (user_id_1, user_id_2, status) values (?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userEmail);
			pstmt.setString(2, recipientEmail);
			pstmt.setString(3, Constants.PENDING);

			pstmt.executeUpdate();
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

	}

	@Override
	public ArrayList<UserProfile> viewAllFriends(String userId) {
		// TODO Auto-generated method stub
		ArrayList<UserProfile> arrUserProfile = new ArrayList<UserProfile>();
		UserProfile profile;
		UserProfileService userProfileService = new UserProfileService();
		
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "select user_id_2 from friendship where user_id_1=? and status='Y'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);

			set = pstmt.executeQuery();

			while(set.next()){
				//Retrieve by column name
				profile = userProfileService.viewUserProfile(set.getString("user_id_2"));
				
				arrUserProfile.add(profile);
			}
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		return arrUserProfile;
	}

	@Override
	public boolean getFriendshipStatus(String userEmail, String recipientEmail) {
		// TODO Auto-generated method stub
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "select count(*) as rowcount from friendship where user_id_1=? and user_id_2=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userEmail);
			pstmt.setString(2, recipientEmail);

			set = pstmt.executeQuery();

			set.next();
			
			int rowcount = set.getInt("rowcount");
			
			if(rowcount > 0) {
				return true;
			}

		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		return false;
	}

	@Override
	public boolean validateRequest(String userEmail, String senderEmail) {
		// TODO Auto-generated method stub
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "select count(*) as rowcount from friendship where user_id_1=? and user_id_2=? and status='N'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, senderEmail);
			pstmt.setString(2, userEmail);

			set = pstmt.executeQuery();

			set.next();
			
			int rowcount = set.getInt("rowcount");
			
			if(rowcount > 0) {
				return true;
			}

		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		return false;
	}

	@Override
	public void acceptRequest(String userEmail, String senderEmail) {
		// TODO Auto-generated method stub
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "update friendship set status=? where user_id_1=? and user_id_2=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, Constants.COMPLETED);
			pstmt.setString(2, senderEmail);
			pstmt.setString(3, userEmail);

			pstmt.executeUpdate();
			
			sql = "insert into friendship (user_id_1, user_id_2, status) values (?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userEmail);
			pstmt.setString(2, senderEmail);
			pstmt.setString(3, Constants.COMPLETED);

			pstmt.executeUpdate();

		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}
	}
	
}
