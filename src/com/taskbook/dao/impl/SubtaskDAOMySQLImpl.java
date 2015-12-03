package com.taskbook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
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
			String sql = "select * from subtasks where task_id=? order by sno";
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
	public void saveSubtasks(Map<Integer, Subtask> map, int taskId) {
		// TODO Auto-generated method stub
		
		//delete all subtasks
		conn = ConnectionFactory.getConnection();
		try {
			String sql = "delete from subtasks where task_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, taskId);
			pstmt.executeUpdate();
			
			Subtask s = null;
			Map.Entry pair;
			Iterator it = map.entrySet().iterator();
			sql = "insert into subtasks(task_id, status, description) values (?,?,?)";
			while(it.hasNext()) {
				pstmt = conn.prepareStatement(sql);
				pair = (Map.Entry)it.next();
				
				s = (Subtask) pair.getValue();
				pstmt.setInt(1, taskId);
				pstmt.setString(2, s.getStatus());
				pstmt.setString(3, s.getDescription());
				pstmt.executeUpdate();
			}
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}
	}
}
