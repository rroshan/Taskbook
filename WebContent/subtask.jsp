<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="Taskbook">
<meta name="author" content="roshan">
<link rel="icon" href="../../favicon.ico">

<style>
body.padding2 {
	padding-left: 1cm;
}
</style>

<title>Subtasks</title>

<script type="text/javascript" src="js/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>

<style type="text/css">
div {
	padding: 8px;
}
</style>

<script type="text/javascript">
	var counter;
	$(document).ready(
		function() {
			$.post(
					'subtask',
					{
						"operation" : "load_all",
						"taskId" : '${task.taskId }',
						"tasklistId" : '${tasklist.tasklistID }'
					},
					function(resp) {
						if(resp.type === "login" && resp.message === "Failed") 
						{
							window.location = "login.jsp";
						}
						else
						{
							$("#subtask_list").empty();
							var $form = $("<form></form>");
							$form.attr({
						        'action' : 'subtask',
						        'method' : 'post',
						        'id'	 : 'subtaskform'
						    });
							
							$form.appendTo('#subtask_list');
							
							var $input = $("<input></input>");
							$input.attr({
								'type'  : 'hidden',
								'name'  : 'taskId',
								'value'    : '${task.taskId }'
							});
							
							$input.appendTo('#subtask_list form');
							
							$input = $("<input></input>");
							$input.attr({
								'type'  : 'hidden',
								'name'  : 'tasklistId',
								'value'    : '${tasklist.tasklistID }'
							});
							
							$input.appendTo('#subtask_list form');
							
							$input = $("<input></input>");
							$input.attr({
								'type'  : 'hidden',
								'name'  : 'operation',
								'value'    : 'save'
							});
							
							$input.appendTo('#subtask_list form');
							
							var $div = $("<div></div>");
							$div.attr({
								'id'  : 'TextBoxesGroup'
							});
							
							$div.appendTo('#subtask_list form');
	
							$.each(resp, function(i, subtask) {
								
								$div = $("<div></div>");
								$div.attr({
									'id'  : 'TextBoxDiv'+(i+1)
								});
								
								$div.appendTo('#subtask_list form #TextBoxesGroup');
								
								$input = $("<input></input>");
								$input.attr({
									'type'  : 'checkbox',
									'value' : 'csubtask'+(i+1),
									'name'  : 'csubtask',
									'id'    : 'csubtask'+(i+1)
								});
								
								$input.appendTo('#subtask_list form #TextBoxDiv'+(i+1));
								
								$input = $("<input></input>");
								$input.attr({
									'type'  : 'textbox',
									'id'    : 'subtask'+(i+1),
									'name'  : 'subtask'+(i+1),
									'class' : 'classsubtask',
									'value'    : subtask.description
								});
								
								$input.prop('required',true);
								$input.appendTo('#subtask_list form #TextBoxDiv'+(i+1));
								
								var $select = $("<select></select>");
								$select.attr({
									'name'  : 'status'+(i+1),
									'form'    : 'subtaskform'
								});
								
								$select.appendTo('#subtask_list form #TextBoxDiv'+(i+1));
								
								var $option = $("<option></option>");
								if(subtask.status === 'N')
								{
									$option.attr({
										'value'  : 'N',
										'selected'    : 'selected'
									});
									
									$option.text('Pending');
									
									$option.appendTo('#subtask_list form #TextBoxDiv'+(i+1)+' select');
									
									$option = $("<option></option>");
									$option.attr({
										'value'  : 'Y'
									});
									
									$option.text('Completed');
									
									$option.appendTo('#subtask_list form #TextBoxDiv'+(i+1)+' select');
								}
								else
								{
									$option.attr({
										'value'  : 'Y',
										'selected'    : 'selected'
									});
									
									$option.text('Completed');
									
									$option.appendTo('#subtask_list form #TextBoxDiv'+(i+1)+' select');
									
									$option = $("<option></option>");
									$option.attr({
										'value'  : 'N'
									});
									
									$option.text('Pending');
									
									$option.appendTo('#subtask_list form #TextBoxDiv'+(i+1)+' select');
								}
							});
	
							$input = $("<input></input>");
							$input.attr({
								'type'  : 'button',
								'id'    : 'addButton',
								'value' : 'Add subtask',
								'class' : 'btn btn-success',
								'onclick' : 'addSubtask()'
							});
							
							$input.appendTo('#subtask_list form');
	
							$input = $("<input></input>");
							$input.attr({
								'type'  : 'button',
								'id'    : 'removeButton',
								'value' : 'Remove subtask',
								'class' : 'btn btn-danger',
								'onclick' : 'removeSubtask()'
							});
							
							$input.appendTo('#subtask_list form');
							
							$input = $("<input></input>");
							$input.attr({
								'type'  : 'submit',
								'class'    : 'btn btn-primary',
								'value' : 'Save'
							});
							
							$input.appendTo('#subtask_list form');
							
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
							
							counter = parseInt(max) + 1;
						}
					}).fail(function() {
				alert("Page load failed!");
			});
			
			loadComments();
		});
	
	function addSubtask() {
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
	}
	
	function removeSubtask() {
		if(counter==1){
	          alert("No more subtasks to remove");
	          return false;
	       }
		
		var checkedId = $('input[name="csubtask"]:checked').map(function() {
		    return this.id.substr(8);
		}).get();
		
		
		for (var i=0; i < checkedId.length; i++) {
			$("#TextBoxDiv" + checkedId[i]).remove();
			counter--;
		}	
	}
	
	function loadComments()
	{
		 //ajax post query
		 $.post('comments', {"taskId":'${task.taskId }', "operation":"load"},
				 function(resp) {
					if(resp.type === "login" && resp.message === "Failed") 
					{
						window.location = "login.jsp";
					}
					else
					{
			 			displayComment(resp);
					}
		 })
		 .fail(function() {
				alert("Failed to insert comment");
		 });
	}
	
	function newComment() {
		 var commentText = $("#commentbox").val();
		 var taskId = $("#commenttaskId").val();
		 
		 if(commentText === "") {
			 alert("Comment cannot be null");
		 }
		 
		 //ajax post query
		 $.post('comments', {"commentText":commentText, "taskId":taskId, "operation":"insert"},
				 function(resp) {
					if(resp.type === "login" && resp.message === "Failed") 
					{
						window.location = "login.jsp";
					}
					else
					{
			 			displayComment(resp);
					}
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
		 $.post('comments', {"commentId":commentId, "taskId":taskId, "operation":"delete"},
				 function(resp) {
					if(resp.type === "login" && resp.message === "Failed") 
					{
						window.location = "login.jsp";
					}
					else
					{
						if(resp.type === "comment_delete" && resp.message === "Permission") 
						{
							alert("You don\'t have persmission to delete this comment'");
						}
						else
						{
			 				displayComment(resp);
						}
					}
		 })
		 .fail(function() {
				alert("Failed to delete comment");
		 });
	}
	
	function displayComment(json) {
		var count = 1;
		$("#comments-group").empty()
		        .append("<h3>Comments</h3>");
		
		$.each(json, function(i, commentObject) {
					var newCommentDiv = $(document.createElement('div')).attr("id", 'CommentDiv' + count);
					newCommentDiv.after().html(commentObject.userId+' <br> <textarea rows="4" cols="50" readonly>'+commentObject.comment+'</textarea> <input type="hidden" name="commentId'+count+'" id="commentId'+count+'" value="'+commentObject.commentId + '" /> <button id="delete'+count+'" name="delete'+count+'" class="deleteCommentButton"  onclick="deleteComment(this)">Delete</button> <br>'+commentObject.commentTime+'<br><br>');
					newCommentDiv.appendTo("#comments-group");
					
					count++;
			    });
		
		$("#commentbox").val('');
	}
	
	function search_for_help() {
		var title = $("#inputName").val();
		var taskId = $("#taskId").val();
		var tasklistId = $("#tasklistId").val();
		//validations
		//public
		//pending
		var status = $("#status").val();
		var scope = $("#scope").val();
		if(status === 'Y' || scope === 'R')
		{
			$("#response_message").empty().append(
					'<div class="alert alert-dismissible alert-danger">'
							+ 'Only Public and Pending tasks can be collaborated' + '</div>');
		}
		else
		{
			window.location = "collaboration?title="+title+"&tasklistId="+tasklistId+"&taskId="+taskId;
		}
	}
</script>


<link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="padding2">
	<div class="bs-component">
		<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> 
					<span class="icon-bar"></span> <span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="tasklist.jsp">Home</a>
			</div>
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="friends.jsp">Friends</a></li>
					<li><a href="sharedtask.jsp">Shared Task</a></li>
					<li><a href="userprofile.jsp">Profile</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="logout">Logout</a></li>
				</ul>
			</div>
		</div>
		</nav>
	</div>
	
	<div class="row">
		<div class="col-lg-6">
			<div class="well bs-component">

				<form action="task" method="post">
					<fieldset>
						<legend>Update Task Details</legend>
						<div class="form-group">
							<label for="inputName" class="col-lg-2 control-label">Name</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="inputName" placeholder="Task Name" value="${task.title }" name="title" required />
							</div>
						</div>

						<div class="form-group">
							<label for="scope" class="col-lg-2 control-label">Scope</label>
							<div class="col-lg-10">
								<select class="form-control" id="scope" value=${task.scope } name="scope">
									<c:choose>
										<c:when test="${task.scope == 'P' }">
											<option value="P" selected="selected">Public</option>
											<option value="R">Private</option>
										</c:when>
										<c:when test="${task.scope == 'R' }">
											<option value="P">Public</option>
											<option value="R" selected="selected">Private</option>
										</c:when>
									</c:choose>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label for="status" class="col-lg-2 control-label">Status</label>
							<div class="col-lg-10">
								<select class="form-control" id="status" value=${task.status } name="status">
								<c:choose>
									<c:when test="${task.status == 'N' }">
										<option value="N" selected="selected">Pending</option>
										<option value="Y">Completed</option>
									</c:when>
									<c:when test="${task.status == 'Y' }">
										<option value="N">Pending</option>
										<option value="Y" selected="selected">Completed</option>
									</c:when>
								</c:choose>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label for="inputDate" class="col-lg-2 control-label">Due Date</label>
							<div class="col-lg-10">
								<input type="date" class="form-control" id="inputDate" placeholder="Due date" value="${date }" name="due_date">
							</div>
						</div>

						<div class="form-group">
							<label for="inputTime" class="col-lg-2 control-label">Due Time</label>
							<div class="col-lg-10">
								<input type="time" class="form-control" id="inputTime" placeholder="Due Time" value="${time }" name="due_time">
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-10">
								<input type="hidden" id="taskId" name="taskId" value="${task.taskId }" />
								<input type="hidden" id="tasklistId" name="tasklistId" value="${tasklist.tasklistID}" name="tasklistId"/>
								<input type="hidden" name="operation" value="update" />
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
	<div id="subtask_list">
	</div>
	
	<br><br><br>
	<button type="button" class="btn btn-success"
		onclick="search_for_help()" id="search" name="search">
		Find Help <span class="glyphicon glyphicon-play"></span>
	</button> <br>
	
	<div class="col-lg-4">
		<div class="bs-component" id="response_message">
		
		</div>
	</div> <br><br><br><br>
	
	<div id='comments-group'>
	</div>
	
	<textarea rows="4" cols="50" name="commentbox" id="commentbox">
	</textarea> <br>
	<input type="hidden" name="commenttaskId" id="commenttaskId" value="${task.taskId }" />
	<button id="commentbutton" class="btn btn-primary" onclick="newComment()">Comment</button>
	<br>
	<br>

	<a href="task?tasklistId=${tasklist.tasklistID}">Go to tasks</a>
</body>
</html>