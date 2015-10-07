package com.taskbook.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taskbook.dao.TaskDAO;
import com.taskbook.dao.impl.TaskDAOMySQLImpl;

/**
 * Servlet implementation class TestTaskDeleteServlet
 */
@WebServlet("/TestTaskDeleteServlet")
public class TestTaskDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestTaskDeleteServlet() {
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
		
		int taskId = Integer.parseInt(request.getParameter("taskId"));
		int tasklistId = Integer.parseInt(request.getParameter("tasklistId"));
		
		TaskDAO dao = new TaskDAOMySQLImpl();
		dao.deleteTask(taskId);
		
		/*RequestDispatcher rd = getServletContext().getRequestDispatcher("/update.jsp?tasklistId="+tasklistId);
		rd.forward(request, response);*/
		
		response.sendRedirect("update.jsp?tasklistId="+tasklistId);
	}
}
