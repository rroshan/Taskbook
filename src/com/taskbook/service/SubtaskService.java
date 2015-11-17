package com.taskbook.service;

import java.util.ArrayList;
import java.util.Map;
import com.taskbook.bo.Subtask;
import com.taskbook.dao.SubtaskDAO;
import com.taskbook.dao.impl.SubtaskDAOMySQLImpl;

public class SubtaskService {
	SubtaskDAO dao = new SubtaskDAOMySQLImpl();
	
	public ArrayList<Subtask> viewAllSubtasks(int taskId)
	{
		ArrayList<Subtask> subtasks = new ArrayList<Subtask>();
		subtasks = dao.viewAllSubtasks(taskId);

		return subtasks;
	}
	
	public void saveSubtasks(Map<Integer, Subtask> map, int taskId)
	{
		dao = new SubtaskDAOMySQLImpl();
		dao.saveSubtasks(map, taskId);
	}
}
