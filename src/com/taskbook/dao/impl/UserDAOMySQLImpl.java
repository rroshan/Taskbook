package com.taskbook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.taskbook.dao.ConnectionFactory;
import com.taskbook.dao.UserDAO;

public class UserDAOMySQLImpl implements UserDAO {
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet set;

	@Override
	public boolean login(String userId, String password) {
		// TODO Auto-generated method stub
		conn = ConnectionFactory.getConnection();
		try {
			String sql = "select count(*) as rowcount from user where user_id=? and password=SHA1(?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, password);

			set = pstmt.executeQuery();

			set.next();
			
			int rowcount = set.getInt("rowcount");
			
			if(rowcount == 1) {
				System.out.println("found user");
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
	
}
