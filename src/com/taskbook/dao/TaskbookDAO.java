package com.taskbook.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.taskbook.bo.*;

public class TaskbookDAO {
	private Connection conn;
	private Statement stmt;
	private ResultSet set;

	public TaskbookDAO() {	}

	//modify the return type to an array of tasklists
	public ArrayList<Tasklist> viewTasklist() {

		ArrayList<Tasklist> arrTasklist = new ArrayList<Tasklist>();
		Tasklist taskList;
		
		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			stmt = conn.createStatement();
			String sql = "SELECT tasklist_id, category, owner FROM tasklist";
			set = stmt.executeQuery(sql);

			while(set.next()){
				//Retrieve by column name
				int tasklist_id  = set.getInt("tasklist_id");
				String category = set.getString("category");
				String owner = set.getString("owner");
				
				taskList = new Tasklist();
				taskList.setOwner(owner);
				taskList.setTasklistID(tasklist_id);
				taskList.setTaskName(category);
				
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
}
