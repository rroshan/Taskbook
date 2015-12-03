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

	public void updateUserProfile(UserProfile userProfile) {
		userProfileDAO.updateUserProfile(userProfile);
	}

	public UserProfile viewUserProfile(String userId) {

		userProfile = new UserProfile();
		userProfile = userProfileDAO.viewUserProfile(userId);

		return userProfile;
	}
}
