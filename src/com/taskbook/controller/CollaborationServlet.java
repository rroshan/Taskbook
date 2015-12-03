package com.taskbook.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.taskbook.bo.CollaborationBO;
import com.taskbook.bo.MessageBean;
import com.taskbook.bo.UserProfile;
import com.taskbook.service.CollaborationService;

/**
 * Servlet implementation class CollaborationServlet
 */
@WebServlet("/CollaborationServlet")
public class CollaborationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CollaborationServlet() {
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
		HttpSession session = request.getSession();

		UserProfile user = (UserProfile)session.getAttribute("user");
		
		MessageBean msg;
		String jsonResult;

		if(user == null) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		}
		else
		{
			if(!request.getParameterMap().containsKey("operation"))
			{
				String title = request.getParameter("title");
				int taskId = Integer.parseInt(request.getParameter("taskId"));
				int tasklistId = Integer.parseInt(request.getParameter("tasklistId"));
				
				CollaborationService collaborationService = new CollaborationService();
				List<CollaborationBO> collaboratorsList = collaborationService.getCollaboratorsList(user.getUserId(), title);
				
				request.setAttribute("collaboratorsList", collaboratorsList);
				request.setAttribute("taskId", taskId);
				request.setAttribute("tasklistId", tasklistId);
				
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/collaboration.jsp");
				rd.forward(request, response);
			}
			else
			{
				CollaborationService collaborationService = new CollaborationService();
				String operation = request.getParameter("operation");
				if(operation.equalsIgnoreCase("send_help_request")) {
					String helperId = request.getParameter("helperEmail");
					int tasklistId = Integer.parseInt(request.getParameter("tasklistId"));
					int taskId = Integer.parseInt(request.getParameter("taskId"));
					
					int result = collaborationService.sendHelpRequest(user.getUserId(), helperId, taskId, tasklistId);
					
					if(result == 1)
					{
						response.setContentType("application/json");
						msg = new MessageBean();
						msg.setType("request_mail");
						msg.setMessage("Success");
						jsonResult = new Gson().toJson(msg);
						response.getWriter().write(jsonResult);
					}
					else
					{
						response.setContentType("application/json");
						msg = new MessageBean();
						msg.setType("request_mail");
						msg.setMessage("Fail");
						jsonResult = new Gson().toJson(msg);
						response.getWriter().write(jsonResult);
					}
				}
				else if(operation.equalsIgnoreCase("accept_help_request")) {
					String helperId = request.getParameter("receiver");
					int taskId = Integer.parseInt(request.getParameter("taskId"));
					
					if(user.getUserId().equalsIgnoreCase(helperId)) {
						collaborationService.acceptHelpRequest(helperId, taskId);
						
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/sharedtask.jsp");
						rd.forward(request, response);
					}
					else
					{
						request.setAttribute("message", "This help request is not for you");
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/error.jsp");
						rd.forward(request, response);
					}
				}
				else if(operation.equalsIgnoreCase("load_all")) {
					response.setContentType("application/json");
					jsonResult = new Gson().toJson(collaborationService.viewAllCollaboratingTasks(user.getUserId()));
					response.getWriter().write(jsonResult);
				}
			}
		}
	}

}
