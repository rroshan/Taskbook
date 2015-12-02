package com.taskbook.service;

import java.util.ArrayList;
import org.antlr.stringtemplate.*;
import org.antlr.stringtemplate.language.*;
import com.taskbook.dao.FriendsDAO;
import com.taskbook.dao.UserProfileDAO;
import com.taskbook.dao.impl.FriendsDAOMySQLImpl;
import com.taskbook.dao.impl.UserProfileDAOMySQLImpl;
import com.taskbook.mail.SendMail;
import com.taskbook.bo.UserProfile;

public class FriendService {

	FriendsDAO friendDAO = new FriendsDAOMySQLImpl();
	UserProfileDAO userProfileDAO = new UserProfileDAOMySQLImpl();

	public int validation(String userEmail, String recipientEmail) {
		if(!userProfileDAO.validUser(recipientEmail)) {
			return 2;
		}

		if(friendDAO.getFriendshipStatus(userEmail, recipientEmail)) {
			return 1;
		}

		return 0;
	}

	public int sendRequest(String userEmail, String recipientEmail)
	{
		//inserting into database
		friendDAO.sendRequest(userEmail, recipientEmail);

		//sending request mail
		String recipientFname = userProfileDAO.viewUserProfile(recipientEmail).getFirstName();
		String senderFname = userProfileDAO.viewUserProfile(userEmail).getFirstName();
		String link = "http://localhost:8080/Taskbook/managefriend";
		String subject = "Friend Request from "+senderFname;

		StringTemplateGroup group =  new StringTemplateGroup("sendRequest", "/Users/roshan/Documents/UTD/Fall 2015/OOAD/Project/Template", DefaultTemplateLexer.class);
		StringTemplate sendRequestTemplate = group.getInstanceOf("send_request");

		link = link + "?sender="+userEmail+"&receiver="+recipientEmail+"&operation=accept";

		sendRequestTemplate.setAttribute("title", "Friend Request");
		sendRequestTemplate.setAttribute("name", recipientFname);
		sendRequestTemplate.setAttribute("requester", senderFname);
		sendRequestTemplate.setAttribute("link", link);

		SendMail sendMail = new SendMail(subject, sendRequestTemplate.toString(), "jakx24@gmail.com", recipientEmail);
		sendMail.send();
		
		return 0;
	}
	
	public int acceptRequest(String userEmail, String senderEmail) {
		friendDAO.acceptRequest(userEmail, senderEmail);
		return 0;
		
	}
	
	public boolean validateRequest(String userEmail, String senderEmail) {
		return friendDAO.validateRequest(userEmail, senderEmail);
	}

	public ArrayList<UserProfile> viewAllFriends(String userId) {

		return friendDAO.viewAllFriends(userId);

	}
}
