package com.taskbook.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.taskbook.bo.MessageBean;
import com.taskbook.bo.Task;
import com.taskbook.bo.Tasklist;
import com.taskbook.bo.UserProfile;
import com.taskbook.service.TaskService;
import com.taskbook.service.TasklistService;
import com.taskbook.service.UserProfileService;

/**
 * Servlet implementation class TaskServlet
 */
@WebServlet("/TaskServlet")
public class TaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TaskServlet() {
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
		TasklistService tasklistService = new TasklistService();
		TaskService taskService = new TaskService();
		String jsonResult;
		int tasklistId;

		HttpSession session = request.getSession();

		UserProfile user = (UserProfile)session.getAttribute("user");

		if(user == null) {
			response.setContentType("application/json");
			MessageBean msg = new MessageBean();
			msg.setType("login");
			msg.setMessage("Failed");
			jsonResult = new Gson().toJson(msg);
			response.getWriter().write(jsonResult);
		}
		else
		{
			response.setCharacterEncoding("UTF-8");

			if(!request.getParameterMap().containsKey("operation"))
			{
				if(request.getParameterMap().containsKey("title"))
				{
					//create new task
					tasklistId = Integer.parseInt(request.getParameter("tasklistId"));
					String taskTitle = request.getParameter("title");
					String owner = user.getUserId();
					String scope = request.getParameter("scope");
					String date = request.getParameter("due_date");
					String time = request.getParameter("due_time");

					taskService.createTask(tasklistId, taskTitle, owner, scope, date, time);

					Tasklist tasklist = tasklistService.viewTasklist(tasklistId);
					request.setAttribute("tasklist", tasklist);

					RequestDispatcher rd = getServletContext().getRequestDispatcher("/task.jsp");
					rd.forward(request, response);
				}
				else
				{
					String id = request.getParameter("tasklistId");
					tasklistId = Integer.parseInt(id);

					int result = tasklistService.checkPermission(tasklistId, user.getUserId());

					if(result == 1) {
						Tasklist tasklist = tasklistService.viewTasklist(tasklistId);
						request.setAttribute("tasklist", tasklist);

						RequestDispatcher rd = getServletContext().getRequestDispatcher("/task.jsp");
						rd.forward(request, response);
					} else {
						request.setAttribute("message", "You don't have permission to view this tasklist");
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/error.jsp");
						rd.forward(request, response);
					}
				}
			}
			else
			{
				if(request.getParameter("operation").equalsIgnoreCase("load_all"))
				{
					response.setContentType("application/json");
					String id = request.getParameter("tasklistId");
					tasklistId = Integer.parseInt(id);
					jsonResult = new Gson().toJson(taskService.viewAllTasks(tasklistId));
					response.getWriter().write(jsonResult);
				}
				else if(request.getParameter("operation").equalsIgnoreCase("update"))
				{
					int taskId = Integer.parseInt(request.getParameter("taskId"));
					tasklistId = Integer.parseInt(request.getParameter("tasklistId"));

					String taskTitle = request.getParameter("title");
					String scope = request.getParameter("scope");
					String date = request.getParameter("due_date");
					
					String time = request.getParameter("due_time");
					String status = request.getParameter("status");

					//vailidate if this user has the permission to update the task

					int result = taskService.checkPermission(tasklistId, taskId, user.getUserId());
					if(result == 1) {
						request.setAttribute("message", "You only have permission to view and comment on this task");
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/error.jsp");
						rd.forward(request, response);
					}
					else if(result == 0)
					{
						Date today = new Date();
						java.sql.Timestamp dueDate = com.taskbook.util.Timestamp.getTimeStamp(date, time);
						java.sql.Timestamp today_ts = new java.sql.Timestamp(today.getTime());

						Task task = taskService.viewTask(taskId);

						String prevStatus = task.getStatus();
						String newStatus = status;

						task.setTaskId(taskId);
						task.setLastModifiedDate(today_ts);
						task.setDueDate(dueDate);
						task.setScope(scope);
						task.setStatus(status);
						task.setTitle(taskTitle);

						taskService.updateTask(taskId, task);

						Tasklist tasklist = tasklistService.viewTasklist(tasklistId);

						
						//check if deducting
						//also have to add points to the helper
						if(!tasklist.getOwner().equalsIgnoreCase(task.getAssignedUser()))
						{
							UserProfile assignedUserProfile;
							System.out.println("Inside Different");
							if(!prevStatus.equalsIgnoreCase(newStatus) && newStatus.equalsIgnoreCase("Y")) {
								System.out.println("Inside Completed Karma Points");
								user.setKarmaPointsBlocked(user.getKarmaPointsBlocked() - 10);
								user.setKarmaPointsTotal(user.getKarmaPointsTotal() - 10);
								UserProfileService userProfileService = new UserProfileService();
								userProfileService.updateUserProfile(user);
								
								assignedUserProfile = userProfileService.viewUserProfile(task.getAssignedUser());
								assignedUserProfile.setKarmaPointsTotal(assignedUserProfile.getKarmaPointsTotal() + 10);
								userProfileService.updateUserProfile(assignedUserProfile);
								
							} else if(!prevStatus.equalsIgnoreCase(newStatus) && newStatus.equalsIgnoreCase("N")) {
								System.out.println("Inside Completed Karma Points");
								user.setKarmaPointsBlocked(user.getKarmaPointsBlocked() + 10);
								user.setKarmaPointsTotal(user.getKarmaPointsTotal() + 10);
								UserProfileService userProfileService = new UserProfileService();
								userProfileService.updateUserProfile(user);
								
								assignedUserProfile = userProfileService.viewUserProfile(task.getAssignedUser());
								assignedUserProfile.setKarmaPointsTotal(assignedUserProfile.getKarmaPointsTotal() - 10);
								userProfileService.updateUserProfile(assignedUserProfile);
							}
						}

						request.setAttribute("tasklist", tasklist);

						RequestDispatcher rd = getServletContext().getRequestDispatcher("/task.jsp");
						rd.forward(request, response);	
					}
				}
				else if(request.getParameter("operation").equalsIgnoreCase("delete"))
				{
					int taskId = Integer.parseInt(request.getParameter("taskId"));
					tasklistId = Integer.parseInt(request.getParameter("tasklistId"));

					taskService.deleteTask(taskId, tasklistId);

					Tasklist tasklist = tasklistService.viewTasklist(tasklistId);
					request.setAttribute("tasklist", tasklist);

					RequestDispatcher rd = getServletContext().getRequestDispatcher("/task.jsp");
					rd.forward(request, response);	
				}
			}
		}
	}
}
