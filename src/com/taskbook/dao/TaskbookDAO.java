package com.taskbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.taskbook.bo.*;

public class TaskbookDAO {
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet set;

	public TaskbookDAO() {	}

	public ArrayList<Tasklist> viewTasklist() {

		ArrayList<Tasklist> arrTasklist = new ArrayList<Tasklist>();
		Tasklist taskList;

		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			stmt = conn.createStatement();
			String sql = "select * from tasklist;";
			set = stmt.executeQuery(sql);

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
				taskList.setTaskName(category);
				taskList.setCreatedDate(createdDate);
				taskList.setLastModifiedDate(lastModifiedDate);

				arrTasklist.add(taskList);
			}
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, stmt, conn);
		}

		return arrTasklist;
	}

	public void insertTasklist(Tasklist taskList) {
		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();
		
		try {
			String sql = "insert into tasklist (category, created_date, last_modified_date, owner) values (?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, taskList.getTaskName());
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
}
