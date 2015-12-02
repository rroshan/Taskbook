package com.taskbook.dao;

import java.util.ArrayList;

import com.taskbook.bo.Comment;

public interface CommentsDAO {
	public void insertComment(Comment comment, int taskId);
	
	public ArrayList<Comment> viewAllComments(int taskId);
	
	public void deleteComment(int commentId);
	
	public int checkPermission(int commentId, String userId);
}
