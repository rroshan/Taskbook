package com.taskbook.service;
//testservlet - for test commit
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taskbook.bo.Tasklist;
import com.taskbook.dao.*;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestServlet() {
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
		
		TaskbookDAO dao = new TaskbookDAO();
		
		Tasklist taskList = new Tasklist();
		String category = request.getParameter("category");
		
		Date dt = new Date();
		
		taskList.setOwner("ROSH01"); //hardcoded for now. Need to change based on user login
		taskList.setTaskName(category);
		taskList.setCreatedDate(new Timestamp(dt.getTime()));
		taskList.setLastModifiedDate(new Timestamp(dt.getTime()));
		
		dao.insertTasklist(taskList);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/test.jsp");
		rd.forward(request, response);
	}

}
