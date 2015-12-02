package com.taskbook.dao;

import com.taskbook.bo.UserProfile;

public interface UserProfileDAO {

	public void addUserProfile(UserProfile userProfile);

	public void updateUserProfile(UserProfile userProfile);

	public UserProfile viewUserProfile(String userId);
	
	public boolean validUser(String userId);
	
}
