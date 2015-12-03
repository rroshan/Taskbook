package com.taskbook.service;

import java.util.ArrayList;
import java.util.Date;

import com.taskbook.bo.Task;
import com.taskbook.dao.TaskDAO;
import com.taskbook.dao.impl.TaskDAOMySQLImpl;

public class TaskService {
	TaskDAO dao = new TaskDAOMySQLImpl();
	
	public ArrayList<Task> viewAllTasks(int tasklistId) {
		ArrayList<Task> tasks = new ArrayList<Task>();
		tasks = dao.viewAllTasks(tasklistId);

		return tasks;
	}
	
	public void deleteTask(int taskId, int tasklistId)
	{
		dao.deleteTask(taskId);
	}
	
	public int checkPermission(int tasklistId, int taskId, String userId) {
		
		return dao.checkPermission(tasklistId, taskId, userId);	
	}
	
	public void createTask(int tasklistId, String taskTitle, String owner, String scope, String date, String time)
	{
		Date today = new Date();
		
		java.sql.Timestamp dueDate = com.taskbook.util.Timestamp.getTimeStamp(date, time);
		
		java.sql.Timestamp createdDate = new java.sql.Timestamp(today.getTime());
		
		Task task = new Task();
		
		task.setAssignedUser(owner);
		task.setCreatedDate(createdDate);
		task.setLastModifiedDate(createdDate);
		task.setDueDate(dueDate);
		task.setScope(scope);
		task.setStatus("N");
		task.setTitle(taskTitle);
		
		dao.insertTask(task, tasklistId);
	}
	
	public void updateTask(int taskId, Task task)
	{	
		dao.updateTask(task);
	}
	
	public Task viewTask(int taskId)
	{
		Task task = new Task();
		task.setTaskId(taskId);
		
		return dao.viewTask(taskId);
	}
}
