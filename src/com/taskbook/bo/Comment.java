package com.taskbook.bo;

import java.sql.Timestamp;

public class Comment {
	String userId;
	Timestamp commentTime;
	String comment;
	int commentId;
	
	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Timestamp getCommentTime() {
		return commentTime;
	}
	
	public void setCommentTime(Timestamp commentTime) {
		this.commentTime = commentTime;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
}
