package com.taskbook.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.taskbook.bo.Tasklist;
import com.taskbook.dao.TasklistDAO;
import com.taskbook.dao.impl.TasklistDAOMySQLImpl;

public class TasklistService {
	TasklistDAO dao = new TasklistDAOMySQLImpl();

	public void createTasklist(String category, String owner) {
		Tasklist tasklistBO = new Tasklist();
		Date dt = new Date();
		tasklistBO.setOwner(owner);
		// based on user login
		tasklistBO.setTasklistName(category);
		tasklistBO.setCreatedDate(new Timestamp(dt.getTime()));
		tasklistBO.setLastModifiedDate(new Timestamp(dt.getTime()));

		dao.insertTasklist(tasklistBO);
	}

	public void updateTasklist(int tasklistId, String category) {
		Tasklist tasklistBO = new Tasklist();
		tasklistBO.setTasklistName(category);
		tasklistBO.setTasklistID(tasklistId);
		dao.updateTasklist(tasklistBO);
	}

	public void deleteTasklist(int taskListId) {
		Tasklist tasklistBO = new Tasklist();
		tasklistBO.setTasklistID(taskListId);
		dao.deleteTasklist(tasklistBO);
	}

	public Tasklist viewTasklist(int taskListId) {
		Tasklist tasklistBO = dao.viewTasklist(taskListId);
		return tasklistBO;
	}

	public ArrayList<Tasklist> viewAllTasklists(String userId) {
		ArrayList<Tasklist> completeTaskList = new ArrayList<Tasklist>();
		completeTaskList = dao.viewAllTasklists(userId);

		return completeTaskList;
	}
	
	public int checkPermission(int tasklistId, String userId) {
		
		return dao.checkPermission(tasklistId, userId);
	}
}
