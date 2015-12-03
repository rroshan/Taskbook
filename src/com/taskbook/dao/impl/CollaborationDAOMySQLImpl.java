package com.taskbook.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.taskbook.bo.CollaboratingTaskBO;
import com.taskbook.bo.CollaborationBO;
import com.taskbook.dao.CollaborationDAO;
import com.taskbook.dao.ConnectionFactory;

public class CollaborationDAOMySQLImpl implements CollaborationDAO {
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet set;

	@Override
	public List<CollaborationBO> getCollaboratorsList(String userId, String title) {
		// TODO Auto-generated method stub
		ArrayList<CollaborationBO> arrCollaboratorsList = new ArrayList<CollaborationBO>();
		CollaborationBO collaborationBO;
		String user, fname, lname, phone, address, taskTitle;
		Timestamp dueDate;
		
		
		conn = ConnectionFactory.getConnection();
		
		try {
			//add a friendship inner query
			String sql = "select * from collaboration_vw where user_id in (select user_id_2 from friendship where user_id_1 = ?) and lower(title) like lower(?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, "%"+title+"%");

			set = pstmt.executeQuery();

			while(set.next()){
				//Retrieve by column name
				user = set.getString("user_id");
				fname = set.getString("fname");
				lname = set.getString("lname");
				phone = set.getString("phone");
				address = set.getString("address");
				taskTitle = set.getString("title");
				dueDate = set.getTimestamp("due_date");
				
				collaborationBO = new CollaborationBO();
				collaborationBO.setUserId(user);
				collaborationBO.setfName(fname);
				collaborationBO.setlName(lname);
				collaborationBO.setPhone(phone);
				collaborationBO.setAddress(address);
				collaborationBO.setTaskTitle(taskTitle);
				collaborationBO.setDueDate(dueDate);

				arrCollaboratorsList.add(collaborationBO);
			}
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}
		return arrCollaboratorsList;
	}

	@Override
	public List<CollaboratingTaskBO> viewAllCollaboratingTasks(String userId) {
		// TODO Auto-generated method stub
		ArrayList<CollaboratingTaskBO> arrCollaboratingTaskList = new ArrayList<CollaboratingTaskBO>();
		CollaboratingTaskBO collaboratingTaskBO;
		String assignedUser, status, scope, title, owner;
		Timestamp dueDate, lastModifiedDate;
		int taskId, tasklistId;
		
		
		conn = ConnectionFactory.getConnection();
		
		try {
			//add a friendship inner query
			String sql = "select * from collaborating_task_vw where assigned_user=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);

			set = pstmt.executeQuery();

			while(set.next()){
				//Retrieve by column name
				title = set.getString("title");
				lastModifiedDate = set.getTimestamp("last_modified_date");
				assignedUser = set.getString("assigned_user");
				status = set.getString("status");
				scope = set.getString("scope");
				dueDate = set.getTimestamp("due_date");
				taskId = set.getInt("task_id");
				tasklistId = set.getInt("tasklist_id");
				owner = set.getString("owner");
				
				collaboratingTaskBO = new CollaboratingTaskBO();
				collaboratingTaskBO.setAssignedUser(assignedUser);
				collaboratingTaskBO.setDueDate(dueDate);
				collaboratingTaskBO.setLastModifiedDate(lastModifiedDate);
				collaboratingTaskBO.setOwner(owner);
				collaboratingTaskBO.setScope(scope);
				collaboratingTaskBO.setStatus(status);
				collaboratingTaskBO.setTaskId(taskId);
				collaboratingTaskBO.setTasklistId(tasklistId);
				collaboratingTaskBO.setTitle(title);

				arrCollaboratingTaskList.add(collaboratingTaskBO);
			}
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}
		return arrCollaboratingTaskList;
	}
}
