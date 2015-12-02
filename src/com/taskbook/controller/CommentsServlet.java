package com.taskbook.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.taskbook.bo.Comment;
import com.taskbook.bo.MessageBean;
import com.taskbook.bo.UserProfile;
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
		String jsonResult;
		CommentsService commentsService = new CommentsService();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String operation = request.getParameter("operation");
		taskId = Integer.parseInt(request.getParameter("taskId"));

		HttpSession session = request.getSession();

		UserProfile user = (UserProfile)session.getAttribute("user");

		if(user == null) {
			response.setContentType("application/json");
			MessageBean msg = new MessageBean();
			msg.setType("login");
			msg.setMessage("Failed");
			jsonResult = new Gson().toJson(msg);
			response.getWriter().write(jsonResult);
		}
		else
		{
			if(operation.equalsIgnoreCase("insert")) {

				String commentText = request.getParameter("commentText");

				commentsService.insertComments(taskId, user.getUserId(), commentText);
				
				arrComments = commentsService.viewAllComments(taskId);
				String commentsJson = new Gson().toJson(arrComments);
				response.getWriter().write(commentsJson);
			}
			else if(operation.equalsIgnoreCase("delete")) {
				//have validation to restrict delete by a different user
				int commentId = Integer.parseInt(request.getParameter("commentId"));
				
				int result = commentsService.checkPermission(commentId, user.getUserId());
				
				if(result == 1) {
					commentsService.deleteComment(commentId);
					arrComments = commentsService.viewAllComments(taskId);

					String commentsJson = new Gson().toJson(arrComments);

					response.getWriter().write(commentsJson);
				} else {
					response.setContentType("application/json");
					MessageBean msg = new MessageBean();
					msg.setType("comment_delete");
					msg.setMessage("Permission");
					jsonResult = new Gson().toJson(msg);
					response.getWriter().write(jsonResult);
				}
			}
			else
			{
				arrComments = commentsService.viewAllComments(taskId);

				String commentsJson = new Gson().toJson(arrComments);

				response.getWriter().write(commentsJson);
			}
		}
	}

}
