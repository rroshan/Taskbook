package com.taskbook.service;

import java.util.ArrayList;
import java.util.Date;

import com.taskbook.bo.Comment;
import com.taskbook.dao.CommentsDAO;
import com.taskbook.dao.impl.CommentsDAOMySQLImpl;

public class CommentsService {
	CommentsDAO dao  = new CommentsDAOMySQLImpl();
	
	public void insertComments(int taskId, String userId, String commentText) {
		Date today = new Date();
		Comment comment = new Comment();
		comment.setComment(commentText);
		comment.setUserId(userId);
		
		java.sql.Timestamp commentTime = new java.sql.Timestamp(today.getTime());
		
		comment.setCommentTime(commentTime);
		
		dao.insertComment(comment, taskId);
	}
	
	public void deleteComment(int commentId) {
		dao.deleteComment(commentId);
	}
	
	public ArrayList<Comment> viewAllComments(int taskId) {
		return dao.viewAllComments(taskId);
	}
	
	public int checkPermission(int commentId, String userId) {
		
		return dao.checkPermission(commentId, userId);
	}
}
