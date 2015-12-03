package com.taskbook.dao;

import java.util.List;

import com.taskbook.bo.CollaboratingTaskBO;
import com.taskbook.bo.CollaborationBO;

public interface CollaborationDAO {
	public List<CollaborationBO> getCollaboratorsList(String userId, String title);
	
	public List<CollaboratingTaskBO> viewAllCollaboratingTasks(String userId);
}
