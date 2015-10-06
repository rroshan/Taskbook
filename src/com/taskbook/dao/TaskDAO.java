package com.taskbook.dao;

import java.util.ArrayList;

import com.taskbook.bo.Task;

public interface TaskDAO {
	
	public ArrayList<Task> viewAllTasks(int tasklistId);
	
	public void insertTasklist(Task task, int tasklistId);
}
