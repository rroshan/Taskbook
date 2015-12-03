package com.taskbook.dao.impl;

import com.taskbook.dao.ConnectionFactory;
import com.taskbook.dao.UserProfileDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.taskbook.bo.UserProfile;

public class UserProfileDAOMySQLImpl implements UserProfileDAO {
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet set;

	@Override
	public void addUserProfile(UserProfile userProfile) {
		conn = ConnectionFactory.getConnection();
		try {
			String sql = "insert into user_profile (user_id, fname, lname, phone, address, karma_points_total, karma_points_blocked) values (?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userProfile.getUserId());
			pstmt.setString(2, userProfile.getFirstName());
			pstmt.setString(3, userProfile.getLastName());
			pstmt.setString(4, userProfile.getPhoneNumber());
			pstmt.setString(5, userProfile.getAddress());
			pstmt.setFloat(6, userProfile.getKarmaBalance());

			pstmt.executeUpdate();

		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

	}

	@Override
	public void updateUserProfile(UserProfile userProfile) {

		conn = ConnectionFactory.getConnection();

		try {
			String sql = "update user_profile set fname = ?, lname = ?, phone = ?, address = ?, karma_points_total = ?, karma_points_blocked = ? where user_id = ?";
			
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userProfile.getFirstName());
			pstmt.setString(2, userProfile.getLastName());
			pstmt.setString(3, userProfile.getPhoneNumber());
			pstmt.setString(4, userProfile.getAddress());
			pstmt.setFloat(5, userProfile.getKarmaPointsTotal());
			pstmt.setFloat(6, userProfile.getKarmaPointsBlocked());
			pstmt.setString(7, userProfile.getUserId());

			pstmt.executeUpdate();

		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}
	}

	@Override
	public UserProfile viewUserProfile(String userId) {

		conn = ConnectionFactory.getConnection();
		UserProfile userProfile = null;

		try {
			String sql = "select * from user_profile where user_id=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			set = pstmt.executeQuery();

			while (set.next()) {
				// Retrieve by column name
				String user_id = set.getString("user_id");
				String firstName = set.getString("fname");
				String lastName = set.getString("lname");
				String phoneNumber = set.getString("phone");
				String address = set.getString("address");
				int karmaPointsTotal = set.getInt("karma_points_total");
				int karmaPointsBlocked = set.getInt("karma_points_blocked");

				userProfile = new UserProfile();
				userProfile.setUserId(user_id);
				userProfile.setFirstName(firstName);
				userProfile.setLastName(lastName);
				userProfile.setPhoneNumber(phoneNumber);
				userProfile.setAddress(address);
				userProfile.setKarmaPointsTotal(karmaPointsTotal);
				userProfile.setKarmaPointsBlocked(karmaPointsBlocked);

			}
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, stmt, conn);
		}

		return userProfile;
	}

	@Override
	public boolean validUser(String userId) {
		// TODO Auto-generated method stub
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "select count(*) as rowcount from user where user_id = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			set = pstmt.executeQuery();

			set.next();
			// Retrieve by column name
			int rowcount = set.getInt("rowcount");
			
			if(rowcount == 0) {
				return false;
			}
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, stmt, conn);
		}

		return true;
	}

}
