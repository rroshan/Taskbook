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
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link rel="stylesheet" type="text/css" href="cerulean.css">
<script type="text/javascript" src="jquery.js"></script>

<style type="text/css">
	div{
		padding:8px;
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
				newCommentDiv.after().html(commentObject.userId+' <br> <textarea rows="4" cols="50" readonly>'+commentObject.comment+'</textarea> <input type="hidden" name="commentId'+count+'" id="commentId'+count+'" value="'+commentObject.commentId + '" /> <button id="delete'+count+'" name="delete'+count+'" class="deleteCommentButton">Delete</button> <br>'+commentObject.commentTime+'<br><br>');
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
	      '" id="subtask' + counter + '" class="classsubtask" value="" /> <select name="status' + counter + '"' + ' form="subtaskform"> <option value="N">Pending</option> <option value="Y">Completed</option></select>');
            
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
     
     $("#commentbutton").click(function(event) {
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
     });
     
     $('.deleteCommentButton').click(function() {
    	 var buttonId = this.id;
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
     });
  });
</script>
<title>Update Task</title>
</head>
<body>
	<h2>Update Task</h2>
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
	
	
	<form action="testTaskUpdate" method="post">
		Name: <input type="text" name="title" value="${task.title }"required/> <br>
		Scope: 
		<select	name="scope" value="${task.scope }">
			<option value="R">private</option>
			<option value="P">public</option>
		</select> <br>
		Status: 
		<select	name="status" value="${task.status }">
			<option value="N">Pending</option>
			<option value="Y">Completed</option>
		</select> <br>		
		Due Date: <input type="date" name="due_date" value="${date }" required/> <br>
		Due time: <input type="time" name="due_time" value="${time }" required/> <br>
		<input type="hidden" name="taskId" value="${task.taskId }" /> 
		<input type="hidden" name="tasklistId" value="${tasklistId}" /> 
		<input type="submit" value="OK" />
	</form>
	
	<h2>Subtasks</h2>
	<%
		ArrayList<Subtask> arrSubtask;
		SubtaskDAO subtaskDAO = new SubtaskDAOMySQLImpl();
		arrSubtask = subtaskDAO.viewAllSubtasks(taskId);
		
		pageContext.setAttribute("subtasks", arrSubtask);
		
		int countId = 1;
	
	%>
	
	<form action="testSubtask" method="post" id="subtaskform">
		<input type="hidden" name="taskId" value="${task.taskId }" />
		<input type="hidden" name="tasklistId" value="${tasklistId}" />  
		<div id='TextBoxesGroup'>
		
		<%
			Subtask s; 
			Iterator<Subtask> it = arrSubtask.iterator();
			while(it.hasNext())
			{
				s = it.next();
		%>
				<div id="TextBoxDiv<%=countId%>">
					<input type="checkbox" name="csubtask" id="csubtask<%=countId%>" /> <input type='textbox' id='subtask<%=countId%>' name = "subtask<%=countId%>" class="classsubtask" value="<%=s.getDescription()%>"/>
						<select name="status<%=countId%>" form="subtaskform">
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
		<input type='button' value='Add subtask' id='addButton'>
		<input type='button' value='Remove subtask' id='removeButton'>
		<input type="submit" value="OK" />
	</form> <br>
	
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
				<button id="delete<%=commentCount %>" name="delete<%=commentCount%>" class="deleteCommentButton">Delete</button> <br>
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
	<input type="submit" value="Comment" id="commentbutton"/>
	
	<a href="update.jsp?tasklistId=${tasklistId}">Go to tasks</a>
</body>
</html>