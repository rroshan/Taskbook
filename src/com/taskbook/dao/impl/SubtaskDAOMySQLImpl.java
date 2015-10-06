package com.taskbook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.taskbook.bo.Subtask;
import com.taskbook.dao.ConnectionFactory;
import com.taskbook.dao.SubtaskDAO;

public class SubtaskDAOMySQLImpl implements SubtaskDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet set;

	@Override
	public ArrayList<Subtask> viewAllSubtasks(int taskId) {
		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();
		Subtask subtask = null;
		ArrayList<Subtask> arrSubtask = new ArrayList<Subtask>();

		try {
			String sql = "select * from subtasks where task_id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, taskId);

			set = pstmt.executeQuery();

			while(set.next()){
				//Retrieve by column name
				int sNo = set.getInt("sno");
				String status = set.getString("status");
				String description = set.getString("description");
				
				subtask = new Subtask();
				subtask.setsNo(sNo);
				subtask.setStatus(status);
				subtask.setDescription(description);
				
				arrSubtask.add(subtask);
			}

		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		return arrSubtask;
	}

	@Override
	public void saveSubtasks(Subtask[] subtasks) {
		// TODO Auto-generated method stub
		
	}

}
