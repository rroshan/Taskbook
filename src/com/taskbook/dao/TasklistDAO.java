package com.taskbook.dao;

import java.util.ArrayList;

import com.taskbook.bo.Tasklist;

public interface TasklistDAO {
	
	public ArrayList<Tasklist> viewAllTasklists();
	
	public Tasklist viewTasklist(int tasklist_id);
	
	public void insertTasklist(Tasklist taskList);
	
	public void updateTasklist(Tasklist tasklist);
	
	public void deleteTasklist(int tasklist_id);
}
