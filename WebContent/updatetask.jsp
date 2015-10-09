<%@page import="com.taskbook.dao.CommentsDAO"%>
<%@page import="com.taskbook.bo.Subtask"%>
<%@page import="com.taskbook.dao.SubtaskDAO"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.taskbook.bo.Task"%>
<%@ page import="com.taskbook.bo.Comment"%>
<%@ page import="com.taskbook.dao.TaskDAO"%>
<%@ page import="com.taskbook.dao.SubtaskDAO"%>
<%@ page import="com.taskbook.dao.CommentsDAO"%>
<%@ page import="com.taskbook.dao.impl.CommentsDAOMySQLImpl"%>
<%@ page import="com.taskbook.dao.impl.*"%>
<%@ page import="com.taskbook.bo.Subtask"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="Tasklist">
<meta name="author" content="OOAD">
<link rel="icon" href="../../favicon.ico">

<style>
body.padding2 {
	padding-left: 1cm;
}
</style>

<title>Taskbook</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<script type="text/javascript" src="jquery.js"></script>

<style type="text/css">
div {
	padding: 8px;
}
</style>
<script type="text/javascript">

function displayComment(json) {
	var count = 1;
	$("#comments-group").empty()
	        .append("<h3>Comments</h3>");
	
	$.each(json, function(i, commentObject) {
				var newCommentDiv = $(document.createElement('div')).attr("id", 'CommentDiv' + count);
				console.log(commentObject.userId);
				console.log(commentObject.comment);
				newCommentDiv.after().html(commentObject.userId+' <br> <textarea rows="4" cols="50" readonly>'+commentObject.comment+'</textarea> <input type="hidden" name="commentId'+count+'" id="commentId'+count+'" value="'+commentObject.commentId + '" /> <button id="delete'+count+'" name="delete'+count+'" class="deleteCommentButton"  onclick="deleteComment(this)">Delete</button> <br>'+commentObject.commentTime+'<br><br>');
				newCommentDiv.appendTo("#comments-group");
				
				count++;
		    });
	
	$("#commentbox").val('');
}
$(document).ready(function(){
	
    var max = -1;
    
	var maxCount =$('.classsubtask').map(function() {
	    return this.id.substr(7);
	}).get();
	
	if(maxCount.length == 0)
	{
		max = 0;	
	}
	else
	{
		for (var i=0; i < maxCount.length; i++) {
			if(maxCount[i] > max) {
				max = maxCount[i];
			}
		}
	}
	
	var counter = parseInt(max) + 1;
	
    $("#addButton").click(function () {
				
	if(counter>30){
            alert("Only 30 subtasks allow");
            return false;
	}   
		
	var newTextBoxDiv = $(document.createElement('div'))
	     .attr("id", 'TextBoxDiv' + counter);
                
	newTextBoxDiv.after().html('<input type="checkbox" id="csubtask'+ counter + '" ' + 'name="csubtask" />' +
	      '<input type="text" name="subtask' + counter + 
	      '" id="subtask' + counter + '" class="classsubtask" value="" required/> <select name="status' + counter + '"' + ' form="subtaskform"> <option value="N">Pending</option> <option value="Y">Completed</option></select>');
            
	newTextBoxDiv.appendTo("#TextBoxesGroup");
				
	counter++;
     });
     $("#removeButton").click(function () {
	if(counter==1){
          alert("No more subtasks to remove");
          return false;
       }
	
	var checkedId = $('input[name="csubtask"]:checked').map(function() {
	    return this.id.substr(8);
	}).get();
	
	console.log(checkedId)
	
	for (var i=0; i < checkedId.length; i++) {
		$("#TextBoxDiv" + checkedId[i]).remove();
		counter--;
	}
			
     });
  });

function newComment() {
	 var commentText = $("#commentbox").val();
	 var taskId = $("#commenttaskId").val();
	 
	 if(commentText === "") {
		 alert("Comment cannot be null");
	 }
	 
	 //ajax post query
	 $.post('testComments', {"commentText":commentText, "taskId":taskId, "operation":"insert"},
			 function(resp) {
		 		displayComment(resp);
	 })
	 .fail(function() {
			alert("Failed to insert comment");
	 });
}

function deleteComment(button) {
	 var buttonId = button.id;
	 var suffix = buttonId.substr(6);
	 var commentId = $("#commentId"+suffix).val();
	 var taskId = $("#commenttaskId").val();
	 
	 //ajax post query
	 $.post('testComments', {"commentId":commentId, "taskId":taskId, "operation":"delete"},
			 function(resp) {
		 		displayComment(resp);
	 })
	 .fail(function() {
			alert("Failed to insert comment");
	 });
}

</script>
</head>
<body class="padding2">
	<%
		Task task;
		
		int taskId = Integer.parseInt(request
				.getParameter("taskId"));
		int tasklistId = Integer.parseInt(request
				.getParameter("tasklistId"));
		TaskDAO dao = new TaskDAOMySQLImpl();
		task = dao.viewTask(taskId);

		pageContext.setAttribute("task", task);
		pageContext.setAttribute("tasklistId", tasklistId);
		pageContext.setAttribute("date", com.taskbook.util.Timestamp.getDate(task.getDueDate()));
		pageContext.setAttribute("time", com.taskbook.util.Timestamp.getTime(task.getDueDate()));
	%>

	<c:set var="tasklistId" value="${tasklistId}" />

	<div class="row">
		<div class="col-lg-6">
			<div class="well bs-component">

				<form action="testTaskUpdate" method="post">
					<fieldset>
						<legend>Update Task Details</legend>
						<div class="form-group">
							<label for="inputName" class="col-lg-2 control-label">Name</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="inputName" placeholder="Task Name" value="${task.title }" name="title" required>
							</div>
						</div>

						<div class="form-group">
							<label for="scope" class="col-lg-2 control-label">Scope</label>
							<div class="col-lg-10">
								<select class="form-control" id="scope" value=${task.scope } name="scope">
									<option value="R">Private</option>
									<option value="P">Public</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label for="status" class="col-lg-2 control-label">Status</label>
							<div class="col-lg-10">
								<select class="form-control" id="status" value=${task.status } name="status">
									<option value="N">Pending</option>
									<option value="Y">Completed</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label for="inputDate" class="col-lg-2 control-label">Date</label>
							<div class="col-lg-10">
								<input type="date" class="form-control" id="inputDate" placeholder="Due date" value="${date }" name="due_date">
							</div>
						</div>

						<div class="form-group">
							<label for="inputTime" class="col-lg-2 control-label">Time</label>
							<div class="col-lg-10">
								<input type="time" class="form-control" id="inputTime" placeholder="Due Time" value="${time }" name="due_time">
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-10">
								<input type="hidden" name="taskId" value="${task.taskId }" name="taskId" />
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-10">
								<input type="hidden" name="tasklistId" value="${tasklistId}" name="tasklistId"/>
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-10 col-lg-offset-2">
								<button type="reset" class="btn btn-default">Cancel</button>
								<button type="submit" class="btn btn-primary">Update</button>
							</div>
						</div>
					</fieldset>
				</form>

				<div id="source-button" class="btn btn-primary btn-xs"
					style="display: none;">&lt; &gt;</div>
			</div>
		</div>
	</div>

	<br>
	<br>

	<h2>Subtasks</h2>
	<%
		ArrayList<Subtask> arrSubtask;
		SubtaskDAO subtaskDAO = new SubtaskDAOMySQLImpl();
		arrSubtask = subtaskDAO.viewAllSubtasks(taskId);
		
		pageContext.setAttribute("subtasks", arrSubtask);
		
		int countId = 1;
	
	%>

	<form action="testSubtask" method="post" id="subtaskform">
		<input type="hidden" name="taskId" value="${task.taskId }" name="taskId"/> 
		<input type="hidden" name="tasklistId" value="${tasklistId}" name="tasklistId"/>
		<div id='TextBoxesGroup'>

			<%
			Subtask s; 
			Iterator<Subtask> it = arrSubtask.iterator();
			while(it.hasNext())
			{
				s = it.next();
		%>
			<div id="TextBoxDiv<%=countId%>">
				<input type="checkbox" name="csubtask" id="csubtask<%=countId%>" />
				<input type='textbox' id='subtask<%=countId%>'
					name="subtask<%=countId%>" class="classsubtask"
					value="<%=s.getDescription()%>" required/> <select
					name="status<%=countId%>" form="subtaskform">
					<%
							if(s.getStatus().equalsIgnoreCase("N"))
							{
						%>
					<option value="N" selected="selected">Pending</option>
					<option value="Y">Completed</option>
					<%
							}
							else
							{
  						%>
					<option value="N">Pending</option>
					<option value="Y" selected="selected">Completed</option>
					<%
							}
	  					%>
				</select>
			</div>

			<%
				countId++;
			}
		%>
		</div>
		<input type='button' value='Add subtask' id='addButton'
			class='btn btn-success'> <input type='button'
			value='Remove subtask' id='removeButton' class='btn btn-danger'>
		<input type="submit" value="Save" class='btn btn-primary' />
	</form>
	<br>


		<%
		ArrayList<Comment> arrComments;
		CommentsDAO commentsDAO = new CommentsDAOMySQLImpl();
		arrComments = commentsDAO.viewAllComments(taskId);
		
		pageContext.setAttribute("comments", arrComments);
	%>
	
	<div id='comments-group'>
	<h3>Comments</h3>
		<%
			Comment comment; 
			int commentCount = 1;
			Iterator<Comment> commentsIterator = arrComments.iterator();
			while(commentsIterator.hasNext())
			{
				comment = commentsIterator.next();
		%>
				<div id="CommentDiv<%=commentCount%>">
				<%=comment.getUserId() %> <br>
				<textarea rows="4" cols="50" readonly><%=comment.getComment() %></textarea>
				<input type="hidden" name="commentId<%=commentCount %>" id="commentId<%=commentCount %>" value="<%=comment.getCommentId() %>" />  
				<button id="delete<%=commentCount %>" name="delete<%=commentCount%>" class="deleteCommentButton" onclick="deleteComment(this)">Delete</button> <br>
				<%=comment.getCommentTime() %> <br><br>
				</div>
		<%
				commentCount++;
			}
		%>
	
	</div>
	
	<textarea rows="4" cols="50" name="commentbox" id="commentbox">
	</textarea> <br>
	<input type="hidden" name="commenttaskId" id="commenttaskId" value="${task.taskId }" />
	<button id="commentbutton" class="btn btn-primary" onclick="newComment()">Comment</button>
	<br>
	<br>

	<a href="update.jsp?tasklistId=${tasklistId}">Go to tasks</a>
</body>
</html>