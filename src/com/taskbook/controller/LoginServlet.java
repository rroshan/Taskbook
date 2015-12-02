package com.taskbook.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.taskbook.bo.UserProfile;
import com.taskbook.service.LoginService;
import com.taskbook.service.UserProfileService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String userId = request.getParameter("inputEmail");
		String password = request.getParameter("inputPassword");
		
		LoginService loginService = new LoginService();
		UserProfileService userProfilrService = new UserProfileService();
		
		if(loginService.login(userId, password))
		{
			HttpSession session = request.getSession();
			
			UserProfile userProfile = userProfilrService.viewUserProfile(userId) ;
			
			session.setAttribute("user", userProfile);
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/tasklist.jsp");
			rd.forward(request, response);
			
		}
		else
		{
			request.setAttribute("message", "Invalid username or password");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		}
		
	}

}
