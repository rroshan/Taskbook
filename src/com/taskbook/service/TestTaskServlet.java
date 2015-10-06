package com.taskbook.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.taskbook.bo.Task;
import com.taskbook.dao.TaskDAO;
import com.taskbook.dao.impl.TaskDAOMySQLImpl;

/**
 * Servlet implementation class TestTaskServlet
 */
@WebServlet("/TestTaskServlet")
public class TestTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestTaskServlet() {
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
		Date today = new Date();
		
		int tasklistId = Integer.parseInt(request.getParameter("tasklistId"));
		String taskTitle = request.getParameter("title");
		String owner = request.getParameter("owner");
		String status = "N";
		String assignedUser = owner;
		String scope = request.getParameter("scope");
		String date = request.getParameter("due_date");
		String time = request.getParameter("due_time");
		

		
		java.sql.Timestamp dueDate = com.taskbook.util.Timestamp.getTimeStamp(date, time);
		
		java.sql.Timestamp createdDate = new java.sql.Timestamp(today.getTime());
		
		//preparing the task object
		Task task = new Task();
		task.setAssignedUser(assignedUser);
		task.setCreatedDate(createdDate);
		task.setLastModifiedDate(createdDate);
		task.setDueDate(dueDate);
		task.setScope(scope);
		task.setStatus(status);
		task.setTitle(taskTitle);
		
		TaskDAO dao = new TaskDAOMySQLImpl();
		dao.insertTasklist(task, tasklistId);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/update.jsp");
		rd.forward(request, response);
	}

}
