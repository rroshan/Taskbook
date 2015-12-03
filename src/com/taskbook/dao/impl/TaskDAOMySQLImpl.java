package com.taskbook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.taskbook.bo.Task;
import com.taskbook.dao.ConnectionFactory;
import com.taskbook.dao.TaskDAO;

public class TaskDAOMySQLImpl implements TaskDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet set;

	@Override
	public void insertTask(Task task, int tasklistId) {

		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "insert into tasks (tasklist_id, task_id, created_date, last_modified_date, assigned_user, status, scope, title, due_date) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, tasklistId);
			pstmt.setInt(2, task.getTaskId());
			pstmt.setTimestamp(3, task.getCreatedDate());
			pstmt.setTimestamp(4, task.getLastModifiedDate());
			pstmt.setString(5, task.getAssignedUser());
			pstmt.setString(6, task.getStatus());
			pstmt.setString(7, task.getScope());
			pstmt.setString(8, task.getTitle());
			pstmt.setTimestamp(9, task.getDueDate());

			pstmt.executeUpdate();

			sql = "insert into reminders (task_id, date_time, active) values (LAST_INSERT_ID(), ?, 'Y')";
			pstmt = conn.prepareStatement(sql);

			long reminderTime = task.getDueDate().getTime() - (60 * 60 * 1000); //subtracting 1 hour from due date
			Timestamp reminderTs = new Timestamp(reminderTime);

			pstmt.setTimestamp(1, reminderTs);
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
	public ArrayList<Task> viewAllTasks(int tasklistId) {
		ArrayList<Task> arrTask = new ArrayList<Task>();
		Task task;

		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "select * from tasks where tasklist_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tasklistId);

			set = pstmt.executeQuery();

			while(set.next()){
				//Retrieve by column name
				int taskId = set.getInt("task_id");
				Timestamp createdDate = set.getTimestamp("created_date");
				Timestamp lastModifiedDate = set.getTimestamp("last_modified_date");
				String assignedUser = set.getString("assigned_user");
				String status = set.getString("status");
				String scope = set.getString("scope");
				String title = set.getString("title");
				Timestamp dueDate = set.getTimestamp("due_date");

				task = new Task();
				task.setTaskId(taskId);
				task.setCreatedDate(createdDate);
				task.setLastModifiedDate(lastModifiedDate);
				task.setDueDate(dueDate);
				task.setAssignedUser(assignedUser);
				task.setScope(scope);
				task.setStatus(status);
				task.setTitle(title);


				arrTask.add(task);
			}
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		return arrTask;
	}

	@Override
	public void deleteTask(int taskId) {
		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();
		try {
			String sql = "delete from tasks where task_id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, taskId);

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
	public void updateTask(Task task) {
		// TODO Auto-generated method stub
		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "update tasks set title=?, scope=?, due_date=?, status=?, last_modified_date=?, assigned_user=? where task_id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, task.getTitle());
			pstmt.setString(2, task.getScope());
			pstmt.setTimestamp(3, task.getDueDate());
			pstmt.setString(4, task.getStatus());
			pstmt.setTimestamp(5, task.getLastModifiedDate());
			pstmt.setString(6, task.getAssignedUser());
			pstmt.setInt(7, task.getTaskId());

			pstmt.executeUpdate();

			sql = "update reminders set active=\"N\" where task_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, task.getTaskId());
			pstmt.executeUpdate();


			sql = "insert into reminders (task_id, date_time, active) values (?, ?, 'Y')";
			pstmt = conn.prepareStatement(sql);

			long reminderTime = task.getDueDate().getTime() - (60 * 60 * 1000); //subtracting 1 hour from due date
			Timestamp reminderTs = new Timestamp(reminderTime);

			pstmt.setInt(1, task.getTaskId());
			pstmt.setTimestamp(2, reminderTs);
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
	public Task viewTask(int taskId) {
		// TODO Auto-generated method stub
		Task task = null;
		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "select * from tasks where task_id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, taskId);
			set = pstmt.executeQuery();

			while(set.next()){
				//Retrieve by column name
				Timestamp createdDate = set.getTimestamp("created_date");
				Timestamp lastModifiedDate = set.getTimestamp("last_modified_date");
				String assignedUser = set.getString("assigned_user");
				String status = set.getString("status");
				String scope = set.getString("scope");
				String title = set.getString("title");
				Timestamp dueDate = set.getTimestamp("due_date");

				task = new Task();
				task.setTaskId(taskId);
				task.setCreatedDate(createdDate);
				task.setLastModifiedDate(lastModifiedDate);
				task.setDueDate(dueDate);
				task.setAssignedUser(assignedUser);
				task.setScope(scope);
				task.setStatus(status);
				task.setTitle(title);
			}
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		return task;
	}

	@Override
	public int checkPermission(int tasklistId, int taskId, String userId) {
		// TODO Auto-generated method stub
		//get owner of tasklist
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "select owner from tasklist where tasklist_id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, tasklistId);
			set = pstmt.executeQuery();

			set.next();
			//Retrieve by column name
			String owner = set.getString("owner");

			if(owner.equalsIgnoreCase(userId)) {
				return 0; //updater is the owner
			}

			sql = "select assigned_user from tasks where tasklist_id=? and task_id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, tasklistId);
			pstmt.setInt(2, taskId);
			set = pstmt.executeQuery();

			set.next();
			String assigned_user = set.getString("assigned_user");

			if(assigned_user.equalsIgnoreCase(userId)) {
				return 1; //updater is the assigned user
			}

		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}
		return -1; //mostly will never come to this.
	}
}
