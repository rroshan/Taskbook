package com.taskbook.service;

import com.taskbook.bo.Task;
import com.taskbook.bo.Tasklist;
import com.taskbook.bo.UserProfile;
import com.taskbook.dao.UserProfileDAO;
import com.taskbook.dao.impl.UserProfileDAOMySQLImpl;
import com.taskbook.util.Constants;

public class UserProfileService {

	UserProfileDAO userProfileDAO = new UserProfileDAOMySQLImpl();
	TasklistService tasklistService = new TasklistService();
	TaskService taskService = new TaskService();

	UserProfile userProfile;

	public void addUserProfile(String userId, String firstName, String lastName, String phoneNumber, String address) {

		userProfile = new UserProfile();

		userProfile.setUserId(userId);
		userProfile.setFirstName(firstName);
		userProfile.setLastName(lastName);
		userProfile.setPhoneNumber(phoneNumber);
		userProfile.setAddress(address);
		userProfile.setKarmaPointsTotal(0);
		userProfile.setKarmaPointsBlocked(0); //initially no blocked points

		userProfileDAO.addUserProfile(userProfile);

	}

	public void updateUserProfile(String userId, String firstName, String lastName, String phoneNumber, String address) {

		userProfile = new UserProfile();

		userProfile.setUserId(userId);
		userProfile.setFirstName(firstName);
		userProfile.setLastName(lastName);
		userProfile.setPhoneNumber(phoneNumber);
		userProfile.setAddress(address);

		userProfileDAO.updateUserProfile(userProfile);
	}

	public UserProfile viewUserProfile(String userId) {

		userProfile = new UserProfile();
		userProfile = userProfileDAO.viewUserProfile(userId);

		return userProfile;
	}

	public void updateKarmaBalance(int tasklistId, int taskId) {

		userProfile = new UserProfile();
		Tasklist tasklist = tasklistService.viewTasklist(tasklistId);
		String owner = tasklist.getOwner();

		Task task = taskService.viewTask(taskId);
		String assignedUser = task.getAssignedUser();
		String status = task.getStatus();

		if (!owner.equalsIgnoreCase(assignedUser) && status.equalsIgnoreCase(Constants.COMPLETED)) {
			int karmaBalance;

			userProfile = viewUserProfile(owner);
			userProfile.setUserId(owner);
			userProfile.updateKarmaBalance(-10);
			userProfileDAO.updateUserProfile(userProfile);

			userProfile = viewUserProfile(assignedUser);
			userProfile.setUserId(assignedUser);
			userProfile.updateKarmaBalance(10);
			userProfileDAO.updateUserProfile(userProfile);
		}
	}
}
