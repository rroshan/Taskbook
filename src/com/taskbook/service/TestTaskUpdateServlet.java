package com.taskbook.service;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taskbook.bo.Task;
import com.taskbook.dao.TaskDAO;
import com.taskbook.dao.impl.TaskDAOMySQLImpl;

/**
 * Servlet implementation class TestTaskUpdateServlet
 */
@WebServlet("/TestTaskUpdateServlet")
public class TestTaskUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestTaskUpdateServlet() {
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

		int taskId = Integer.parseInt(request.getParameter("taskId"));
		int tasklistId = Integer.parseInt(request.getParameter("tasklistId"));
		String title = request.getParameter("title");
		String scope = request.getParameter("scope");
		String status = request.getParameter("status");

		String time = request.getParameter("due_time");
		String date = request.getParameter("due_date");

		java.sql.Timestamp dueDate = com.taskbook.util.Timestamp.getTimeStamp(date, time);

		java.sql.Timestamp lastModifiedDate = new java.sql.Timestamp(today.getTime());

		//preparing the task object
		Task task = new Task();
		task.setLastModifiedDate(lastModifiedDate);
		task.setDueDate(dueDate);
		task.setScope(scope);
		task.setStatus(status);
		task.setTitle(title);
		task.setTaskId(taskId);

		TaskDAO dao = new TaskDAOMySQLImpl();
		dao.updateTask(task, tasklistId);
		
		response.sendRedirect("update.jsp?tasklistId="+tasklistId);
	}

}
