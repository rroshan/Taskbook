package com.taskbook.dao;

import java.util.ArrayList;
import java.util.Map;

import com.taskbook.bo.Subtask;

public interface SubtaskDAO {
	public ArrayList<Subtask> viewAllSubtasks(int taskId);
	
	public void saveSubtasks(Map<Integer, Subtask> map, int taskId);
}
