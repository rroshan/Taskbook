package com.taskbook.service;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taskbook.bo.Tasklist;
import com.taskbook.dao.TaskbookDAO;

/**
 * Servlet implementation class TestUpdateServlet
 */
@WebServlet("/TestUpdateServlet")
public class TestUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestUpdateServlet() {
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
		int tasklist_id = Integer.parseInt(request.getParameter("tasklistId"));
		String category = request.getParameter("category");
		
		Tasklist tasklist = new Tasklist();
		tasklist.setTaskName(category);
		tasklist.setTasklistID(tasklist_id);
		
		dao.updateTasklist(tasklist);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/test.jsp");
		rd.forward(request, response);
	}

}
