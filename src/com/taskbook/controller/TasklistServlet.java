package com.taskbook.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.taskbook.service.TasklistService;

/**
 * Servlet implementation class TasklistServlet
 */
@WebServlet("/TasklistServlet")
public class TasklistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TasklistServlet() {
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
		String jsonResult;
		int tasklistId;
		String category;
		
		response.setCharacterEncoding("utf-8");
		
		if(request.getParameterMap().containsKey("operation"))
		{
			if(request.getParameter("operation").equalsIgnoreCase("load_all"))
			{
				response.setContentType("application/json");
				jsonResult = new Gson().toJson(tasklistService.viewAllTasklists());
				response.getWriter().write(jsonResult);
			}
			else if(request.getParameter("operation").equalsIgnoreCase("delete"))
			{
				tasklistId = Integer.parseInt(request.getParameter("tasklistId"));
				tasklistService.deleteTasklist(tasklistId);
				
				response.sendRedirect("tasklist.jsp");
			}
			else if(request.getParameter("operation").equalsIgnoreCase("update"))
			{
				tasklistId = Integer.parseInt(request.getParameter("tasklistId"));
				category = request.getParameter("category");
				
				tasklistService.updateTasklist(tasklistId, category);
				
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/tasklist.jsp");
				rd.forward(request, response);
			}
		}
		else
		{
			//creating a new tasklist
			category = request.getParameter("category");
			
			tasklistService.createTasklist(category, "ROSH01");
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/tasklist.jsp");
			rd.forward(request, response);
		}
	}
}
