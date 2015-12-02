package com.taskbook.dao;

import java.util.ArrayList;
import com.taskbook.bo.UserProfile;

public interface FriendsDAO {
	public void sendRequest(String userEmail, String recipientEmail);
	
	public ArrayList<UserProfile> viewAllFriends(String userId);
	
	public boolean getFriendshipStatus(String userEmail, String recipientEmail); 
	
	public boolean validateRequest(String userEmail, String senderEmail);
	
	public void acceptRequest(String userEmail, String senderEmail);
}
