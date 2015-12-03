package com.taskbook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import com.taskbook.bo.Comment;
import com.taskbook.dao.CommentsDAO;
import com.taskbook.dao.ConnectionFactory;

public class CommentsDAOMySQLImpl implements CommentsDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet set;

	@Override
	public void insertComment(Comment comment, int taskId) {
		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "insert into comments values (?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, taskId);
			pstmt.setInt(2, comment.getCommentId());
			pstmt.setString(3, comment.getUserId());
			pstmt.setTimestamp(4, comment.getCommentTime());
			pstmt.setString(5, comment.getComment());

			pstmt.executeUpdate();
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}
	}

	@Override
	public ArrayList<Comment> viewAllComments(int taskId) {
		ArrayList<Comment> arrComment = new ArrayList<Comment>();
		Comment comment;

		conn = ConnectionFactory.getConnection();

		try {
			String sql = "select * from comments where task_id=? order by date_time desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, taskId);

			set = pstmt.executeQuery();

			while(set.next()){
				//Retrieve by column name
				int commentId = set.getInt("comment_id");
				String userId = set.getString("user_id");
				Timestamp commentDate = set.getTimestamp("date_time");
				String comm = set.getString("comment");

				comment = new Comment();
				comment.setCommentId(commentId);
				comment.setUserId(userId);
				comment.setCommentTime(commentDate);
				comment.setComment(comm);


				arrComment.add(comment);
			}
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		return arrComment;
	}

	@Override
	public void deleteComment(int commentId) {
		// TODO Auto-generated method stub
		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();
		try {
			String sql = "delete from comments where comment_id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, commentId);

			pstmt.executeUpdate();


		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}
	}

	@Override
	public int checkPermission(int commentId, String userId) {
		// TODO Auto-generated method stub
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "select user_id from comments where comment_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, commentId);

			set = pstmt.executeQuery();

			set.next();
				//Retrieve by column name
			String commentOwner = set.getString("user_id");
			
			if(userId.equalsIgnoreCase(commentOwner)) {
				return 1;
			}
			
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		return 0;
	}

}
