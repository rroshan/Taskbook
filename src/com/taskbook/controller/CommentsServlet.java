package com.taskbook.controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.taskbook.bo.Comment;
import com.taskbook.service.CommentsService;

/**
 * Servlet implementation class CommentsServlet
 */
@WebServlet("/CommentsServlet")
public class CommentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentsServlet() {
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
		ArrayList<Comment> arrComments;
		int taskId;
		CommentsService commentsService = new CommentsService();
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String operation = request.getParameter("operation");
		taskId = Integer.parseInt(request.getParameter("taskId"));
		
		if(operation.equalsIgnoreCase("insert")) {
			
			String commentText = request.getParameter("commentText");

			commentsService.insertComments(taskId, "ROSH01", commentText);

		}
		else if(operation.equalsIgnoreCase("delete")) {
			int commentId = Integer.parseInt(request.getParameter("commentId"));
			
			commentsService.deleteComment(commentId);
		}
		
		arrComments = commentsService.viewAllComments(taskId);
		
		String commentsJson = new Gson().toJson(arrComments);
		
		response.getWriter().write(commentsJson);
	}

}
