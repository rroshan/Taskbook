package com.taskbook.service;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taskbook.bo.Subtask;
import com.taskbook.dao.SubtaskDAO;
import com.taskbook.dao.impl.SubtaskDAOMySQLImpl;

/**
 * Servlet implementation class TestSubtask
 */
@WebServlet("/TestSubtask")
public class TestSubtaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestSubtaskServlet() {
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
		
		Enumeration<String> e = request.getParameterNames();
		
		//implement array resizing
		HashMap<Integer, Subtask> map = new HashMap<Integer, Subtask>();
		int i;
		Subtask s;
		int count = 1;
		int tasklistId = 0;
		int taskId = 0;
		
		while(e.hasMoreElements()) {
			String param = e.nextElement();
			
			if(param.toLowerCase().contains("subtask")) 
			{
				System.out.println("subtask"+param.substring(7));
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
				System.out.println("Status"+param.substring(6));
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
		
		SubtaskDAO dao = new SubtaskDAOMySQLImpl();
		dao.saveSubtasks(map, taskId);
		
		response.sendRedirect("updatetask.jsp?tasklistId="+tasklistId+"&taskId="+taskId);
	}

}
