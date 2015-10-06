package com.taskbook.dao;

import java.util.ArrayList;
import com.taskbook.bo.Subtask;

public interface SubtaskDAO {
	public ArrayList<Subtask> viewAllSubtasks(int taskId);
	
	public void saveSubtasks(Subtask[] subtasks);
}
