package com.taskbook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.taskbook.bo.Tasklist;
import com.taskbook.dao.ConnectionFactory;
import com.taskbook.dao.TasklistDAO;

public class TasklistDAOMySQLImpl implements TasklistDAO {

	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet set;

	@Override
	public ArrayList<Tasklist> viewAllTasklists(String userId) {
		ArrayList<Tasklist> arrTasklist = new ArrayList<Tasklist>();
		Tasklist taskList;

		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "select * from tasklist where owner=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);

			set = pstmt.executeQuery();

			while(set.next()){
				//Retrieve by column name
				int tasklist_id  = set.getInt("tasklist_id");
				String category = set.getString("category");
				String owner = set.getString("owner");
				Timestamp createdDate = set.getTimestamp("created_date");
				Timestamp lastModifiedDate = set.getTimestamp("last_modified_date");

				taskList = new Tasklist();
				taskList.setOwner(owner);
				taskList.setTasklistID(tasklist_id);
				taskList.setTasklistName(category);
				taskList.setCreatedDate(createdDate);
				taskList.setLastModifiedDate(lastModifiedDate);

				arrTasklist.add(taskList);
			}
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		return arrTasklist;

	}

	@Override
	public Tasklist viewTasklist(int tasklist_id) {
		Tasklist taskList = null;
		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "select * from tasklist where tasklist_id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, tasklist_id);
			set = pstmt.executeQuery();

			while(set.next()){
				//Retrieve by column name
				String category = set.getString("category");
				String owner = set.getString("owner");
				Timestamp createdDate = set.getTimestamp("created_date");
				Timestamp lastModifiedDate = set.getTimestamp("last_modified_date");

				taskList = new Tasklist();
				taskList.setOwner(owner);
				taskList.setTasklistID(tasklist_id);
				taskList.setTasklistName(category);
				taskList.setCreatedDate(createdDate);
				taskList.setLastModifiedDate(lastModifiedDate);
			}
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		return taskList;

	}

	@Override
	public void insertTasklist(Tasklist taskList) {
		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "insert into tasklist (category, created_date, last_modified_date, owner) values (?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, taskList.getTasklistName());
			pstmt.setTimestamp(2, taskList.getCreatedDate());
			pstmt.setTimestamp(3, taskList.getLastModifiedDate());
			pstmt.setString(4, taskList.getOwner());

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
	public void updateTasklist(Tasklist tasklist) {
		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();
		Date dt = new Date();

		try {
			String sql = "update tasklist set category=?, last_modified_date=? where tasklist_id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, tasklist.getTasklistName());
			pstmt.setTimestamp(2, new Timestamp(dt.getTime()));
			pstmt.setInt(3, tasklist.getTasklistID());

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
	public void deleteTasklist(Tasklist tasklist) {
		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();
		try {
			String sql = "delete from tasklist where tasklist_id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, tasklist.getTasklistID());

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
	public int checkPermission(int tasklistId, String userId) {
		// TODO Auto-generated method stub
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "select owner from tasklist where tasklist_id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, tasklistId);
			set = pstmt.executeQuery();

			set.next();
			//Retrieve by column name
			String owner = set.getString("owner");
			
			if(owner.equalsIgnoreCase(userId))
			{
				return 1;
			}
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		return 0;
	}
}
