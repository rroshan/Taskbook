package com.taskbook.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.taskbook.bo.Tasklist;
import com.taskbook.service.TaskService;
import com.taskbook.service.TasklistService;

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
		
		response.setCharacterEncoding("UTF-8");
		
		if(!request.getParameterMap().containsKey("operation"))
		{
			if(request.getParameterMap().containsKey("title"))
			{
				//create new task
				tasklistId = Integer.parseInt(request.getParameter("tasklistId"));
				String taskTitle = request.getParameter("title");
				String owner = "ROSH01"; //get from session
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
			
				Tasklist tasklist = tasklistService.viewTasklist(tasklistId);
				request.setAttribute("tasklist", tasklist);
				
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/task.jsp");
				rd.forward(request, response);
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
				String owner = "ROSH01"; //get from session
				String scope = request.getParameter("scope");
				String date = request.getParameter("due_date");
				String time = request.getParameter("due_time");
				String status = request.getParameter("status");
				
				taskService.updateTask(tasklistId, taskId, taskTitle, scope, date, time, status);
				
				Tasklist tasklist = tasklistService.viewTasklist(tasklistId);
				request.setAttribute("tasklist", tasklist);
				
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/task.jsp");
				rd.forward(request, response);	
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
