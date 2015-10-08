package com.taskbook.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.taskbook.bo.Comment;
import com.taskbook.dao.CommentsDAO;
import com.taskbook.dao.impl.CommentsDAOMySQLImpl;

/**
 * Servlet implementation class TestCommentsServlet
 */
@WebServlet("/TestCommentsServlet")
public class TestCommentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestCommentsServlet() {
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
		
		Date today = new Date();
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		int taskId = Integer.parseInt(request.getParameter("taskId"));
		String commentText = request.getParameter("commentText");

		Comment comment = new Comment();
		comment.setComment(commentText);
		comment.setUserId("ROSH01"); //hardcoded for now
		
		java.sql.Timestamp commentTime = new java.sql.Timestamp(today.getTime());
		
		comment.setCommentTime(commentTime);
		
		CommentsDAO dao = new CommentsDAOMySQLImpl();
		dao.insertComment(comment, taskId);
		
		
		arrComments = dao.viewAllComments(taskId);
		
		String commentsJson = new Gson().toJson(arrComments);
		
		response.getWriter().write(commentsJson);
	}

}
