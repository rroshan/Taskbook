package com.taskbook.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.taskbook.bo.MessageBean;
import com.taskbook.bo.Subtask;
import com.taskbook.bo.Task;
import com.taskbook.bo.Tasklist;
import com.taskbook.bo.UserProfile;
import com.taskbook.service.SubtaskService;
import com.taskbook.service.TaskService;
import com.taskbook.service.TasklistService;

/**
 * Servlet implementation class SubtaskServlet
 */
@WebServlet("/SubtaskServlet")
public class SubtaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SubtaskServlet() {
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
		SubtaskService subtaskService = new SubtaskService();
		int taskId, tasklistId;
		String jsonResult;

		HttpSession session = request.getSession();

		UserProfile user = (UserProfile)session.getAttribute("user");

		if(user == null) {
			if(request.getParameterMap().containsKey("operation"))
			{
				if(request.getParameter("operation").equalsIgnoreCase("load_all"))
				{
					response.setContentType("application/json");
					MessageBean msg = new MessageBean();
					msg.setType("login");
					msg.setMessage("Failed");
					jsonResult = new Gson().toJson(msg);
					response.getWriter().write(jsonResult);
				}
				else if(request.getParameter("operation").equalsIgnoreCase("save"))
				{
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
					rd.forward(request, response);
				}
			}
			else
			{
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
				rd.forward(request, response);
			}
		}
		else
		{
			response.setCharacterEncoding("UTF-8");

			if(!request.getParameterMap().containsKey("operation"))
			{
				String id = request.getParameter("taskId");
				taskId = Integer.parseInt(id);

				tasklistId = Integer.parseInt(request.getParameter("tasklistId"));
				
				int result = taskService.checkPermission(tasklistId, taskId, user.getUserId());
				
				if(result == -1) {
					request.setAttribute("message", "You don't have permission to view details about this task");
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/error.jsp");
					rd.forward(request, response);
				}

				Task task = taskService.viewTask(taskId);
				Tasklist tasklist = tasklistService.viewTasklist(tasklistId);

				request.setAttribute("task", task);
				request.setAttribute("date", com.taskbook.util.Timestamp.getDate(task.getDueDate()));
				request.setAttribute("time", com.taskbook.util.Timestamp.getTime(task.getDueDate()));
				request.setAttribute("tasklist", tasklist);

				RequestDispatcher rd = getServletContext().getRequestDispatcher("/subtask.jsp");
				rd.forward(request, response);
			}
			else
			{
				if(request.getParameter("operation").equalsIgnoreCase("load_all"))
				{
					response.setContentType("application/json");
					String id = request.getParameter("taskId");
					taskId = Integer.parseInt(id);
					jsonResult = new Gson().toJson(subtaskService.viewAllSubtasks(taskId));
					response.getWriter().write(jsonResult);
				}
				else if(request.getParameter("operation").equalsIgnoreCase("save"))
				{
					//int result = taskService.checkPermission(tasklistId, taskId, user.getUserId());
					tasklistId = Integer.parseInt(request.getParameter("tasklistId"));
					taskId = Integer.parseInt(request.getParameter("taskId"));
					
					int result = taskService.checkPermission(tasklistId, taskId, user.getUserId());
					
					if(result == 0) {
						Enumeration<String> e = request.getParameterNames();

						HashMap<Integer, Subtask> map = new HashMap<Integer, Subtask>();
						int i;
						Subtask s;
						int count = 1;
						tasklistId = 0;
						taskId = 0;

						while(e.hasMoreElements()) {
							String param = e.nextElement();

							if(param.toLowerCase().contains("subtask") && !param.toLowerCase().contains("csubtask")) 
							{
								i = Integer.parseInt(param.substring(7));
								s = map.get(i);

								if(s == null) {
									s = new Subtask();
									s.setsNo(count);
									map.put(i, s);
									count++;
								}
								s.setDescription(request.getParameter(param));
							} 
							else if(param.toLowerCase().contains("status")) 
							{
								i = Integer.parseInt(param.substring(6));
								s = map.get(i);

								if(s == null) {
									s = new Subtask();
									s.setsNo(count);
									map.put(i, s);
									count++;
								}
								s.setStatus(request.getParameter(param));
							}
							else if(param.equals("tasklistId")) {
								tasklistId = Integer.parseInt(request.getParameter(param));
							}
							else if(param.equals("taskId")) {
								taskId = Integer.parseInt(request.getParameter(param));
							}
						}

						subtaskService.saveSubtasks(map, taskId);

						String id = request.getParameter("taskId");
						taskId = Integer.parseInt(id);

						tasklistId = Integer.parseInt(request.getParameter("tasklistId"));

						Task task = taskService.viewTask(taskId);
						Tasklist tasklist = tasklistService.viewTasklist(tasklistId);

						request.setAttribute("task", task);
						request.setAttribute("date", com.taskbook.util.Timestamp.getDate(task.getDueDate()));
						request.setAttribute("time", com.taskbook.util.Timestamp.getTime(task.getDueDate()));
						request.setAttribute("tasklist", tasklist);

						RequestDispatcher rd = getServletContext().getRequestDispatcher("/subtask.jsp");
						rd.forward(request, response);
					} else if(result == 1) {
						//assigned user saving..not allowed
						request.setAttribute("message", "You only have permission to view and comment on this task");
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/error.jsp");
						rd.forward(request, response);
					}
				}
			}
		}
	}

}
