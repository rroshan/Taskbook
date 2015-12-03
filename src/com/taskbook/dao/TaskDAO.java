package com.taskbook.dao;

import java.util.ArrayList;

import com.taskbook.bo.Task;

public interface TaskDAO {
	
	public ArrayList<Task> viewAllTasks(int tasklistId);
	
	public Task viewTask(int taskId);
	
	public void insertTask(Task task, int tasklistId);
	
	public void deleteTask(int taskId);
	
	public void updateTask(Task task);
	
	public int checkPermission(int tasklistId, int taskid, String userId);
}
