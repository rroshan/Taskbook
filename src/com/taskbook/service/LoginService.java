package com.taskbook.service;

import com.taskbook.dao.UserDAO;
import com.taskbook.dao.impl.UserDAOMySQLImpl;

public class LoginService {
	UserDAO userDAO = new UserDAOMySQLImpl();
	
	public boolean login(String userId, String password) {
		return userDAO.login(userId, password);
	}
}
