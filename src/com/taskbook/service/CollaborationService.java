package com.taskbook.service;

import java.util.List;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

import com.taskbook.bo.CollaboratingTaskBO;
import com.taskbook.bo.CollaborationBO;
import com.taskbook.bo.Task;
import com.taskbook.bo.UserProfile;
import com.taskbook.dao.CollaborationDAO;
import com.taskbook.dao.impl.CollaborationDAOMySQLImpl;
import com.taskbook.mail.SendMail;

public class CollaborationService {
	
	CollaborationDAO collaboratioDAO = new CollaborationDAOMySQLImpl();
	UserProfileService userProfileService = new UserProfileService();
	TaskService taskService = new TaskService();
	
	public List<CollaborationBO> getCollaboratorsList(String userId, String title) {
		
		return collaboratioDAO.getCollaboratorsList(userId, title);
	}
	
	public int sendHelpRequest(String requestorId, String helperId, int taskId, int tasklistId) {
		
		if(userProfileService.viewUserProfile(requestorId).getKarmaBalance() < 10) {
			return 0;
		}
		
		String recipientFname = userProfileService.viewUserProfile(helperId).getFirstName();
		String senderFname = userProfileService.viewUserProfile(requestorId).getFirstName();
		String link = "http://localhost:8080/Taskbook/collaboration";
		String subject = "Request for help from "+senderFname;

		StringTemplateGroup group =  new StringTemplateGroup("sendHelpRequest", "/Users/roshan/Documents/UTD/Fall 2015/OOAD/Project/Template", DefaultTemplateLexer.class);
		StringTemplate sendRequestTemplate = group.getInstanceOf("send_help_request");

		link = link + "?sender="+requestorId+"&receiver="+helperId+"&tasklistId="+tasklistId+"&taskId="+taskId+"&operation=accept_help_request";
		
		Task task = taskService.viewTask(taskId);

		sendRequestTemplate.setAttribute("title", "Help Request");
		sendRequestTemplate.setAttribute("name", recipientFname);
		sendRequestTemplate.setAttribute("requester", senderFname);
		sendRequestTemplate.setAttribute("task", task.getTitle());
		sendRequestTemplate.setAttribute("link", link);

		SendMail sendMail = new SendMail(subject, sendRequestTemplate.toString(), "jakx24@gmail.com", helperId);
		sendMail.send();
		
		return 1;
	}
	
	public void acceptHelpRequest(String helperId, String requestor, int taskId) {
		Task task = taskService.viewTask(taskId);
		
		task.setAssignedUser(helperId);
		
		taskService.updateTask(taskId, task);
		
		UserProfile requestorProfile = userProfileService.viewUserProfile(requestor);
		requestorProfile.setKarmaPointsBlocked(requestorProfile.getKarmaPointsBlocked() + 10);
		userProfileService.updateUserProfile(requestorProfile);
		
	}
	
	public List<CollaboratingTaskBO> viewAllCollaboratingTasks(String userId) {
		return collaboratioDAO.viewAllCollaboratingTasks(userId);
	}
}
