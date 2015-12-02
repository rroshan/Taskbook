package com.taskbook.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.taskbook.bo.MessageBean;
import com.taskbook.service.FriendService;

/**
 * Servlet implementation class FriendServlet
 */
@WebServlet("/FriendServlet")
public class FriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FriendServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String jsonResult;
		FriendService friendService = new FriendService();
		int result;
		String message;
		MessageBean msg = new MessageBean();
		
		response.setCharacterEncoding("utf-8");

		if(request.getParameterMap().containsKey("operation")) {
			String operation = request.getParameter("operation");
			
			if(operation.equalsIgnoreCase("validation")) {
				response.setContentType("application/json");
				
				String recipientEmail = request.getParameter("recipient_email");
				
				result = friendService.validation("r_roshan@hotmail.com", recipientEmail); //need to get user from session
				
				if(result == 1) {
					msg.setType("Fail");
					msg.setMessage("Already friends with this user");
				} else if(result == 2) {
					msg.setType("Notification");
					msg.setMessage("Sent a signup mail this user since he is not a taskbook user");
				} else if(result == 0) {
					msg.setType("Success");
				}
				
				message = new Gson().toJson(msg);
				response.getWriter().write(message);
				
			} else if(operation.equalsIgnoreCase("load_all")) {
				//getting all the logged in user friends
				response.setContentType("application/json");
				jsonResult = new Gson().toJson(friendService.viewAllFriends("r_roshan@hotmail.com")); //logged in user should replace r_roshan@hotmail.com
				response.getWriter().write(jsonResult);
			} else if(operation.equalsIgnoreCase("accept")) {
				
				String sender = request.getParameter("sender");
				String receiver = request.getParameter("receiver");
				
				//if(friendService.validateRequest(userEmail, senderEmail))
				if(friendService.validateRequest(receiver, sender)) { //validate if the receiver is the logged in user 
					friendService.acceptRequest(receiver, sender);
				} else {
					request.setAttribute("err_message", "Invalid or expired request");
				}
				
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/friends.jsp");
				rd.forward(request, response);
			}
		}
		else
		{	
			String recipientEmail = request.getParameter("recipient_email");

			result = friendService.sendRequest("r_roshan@hotmail.com", recipientEmail); //logged in user should replace r_roshan@hotmail.com
			
			if(result == 0) {
				request.setAttribute("message", "Friend request successfully sent to "+recipientEmail);
			}
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/friends.jsp");
			rd.forward(request, response);
		}
		
	}

}
