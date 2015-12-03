<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.ArrayList"%>
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

<title>Tasks</title>

<style>
body.padding2 {
	padding-top: 1cm;
	padding-left: 0.5cm;
}
</style>

<script type="text/javascript" src="js/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>

<script type="text/javascript">
	$(document).ready(
		function() {
			$.post(
					'task',
					{
						"operation" : "load_all",
						"tasklistId" : '${tasklist.tasklistID }'
					},
					function(resp) {
						if(resp.type === "login" && resp.message === "Failed") 
						{
							window.location = "login.jsp";
						} 
						else 
						{
							$("#tasks_table").empty();
							
							var $th = $('<tr>').append(
									$('<th>').text("Title"),
									$('<th>').text("Last Modified Date"),
									$('<th>').text("Assigned User"),
									$('<th>').text("Status"),
									$('<th>').text("Scope"),
									$('<th>').text("Due Date"));
							
							$th.appendTo('#tasks_table');
	
							$.each(resp, function(i, task) {
								var status, scope;
								if(task.status === 'Y')
								{
									status = 'Completed';
								}
								else
								{
									status = 'Pending';
								}
								
								if(task.scope === 'R')
								{
									scope = 'Private';
								}
								else
								{
									scope = 'Public';
								}
								
								var $tr = $('<tr>').append(
										$('<td>').append('<a href="subtask?tasklistId=${tasklist.tasklistID }&taskId='+ task.taskId + '">'+task.title+'</a>'),
										$('<td>').text(task.lastModifiedDate),
										$('<td>').text(task.assignedUser),
										$('<td>').text(status),
										$('<td>').text(scope),
										$('<td>').text(task.dueDate),
										$('<td>').append('<a href="task?tasklistId=${tasklist.tasklistID }&taskId=' + task.taskId + '&operation=delete">Delete</a>'));
	
								$tr.appendTo('#tasks_table');
							});
						}
					}).fail(function() {
				alert("Page load failed!");
			});
		});
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
	<h2>Update Tasklist Name</h2>
	
	<form action="tasklist" method="post">
		Category: <input type="text" name="category" id="category" value="${tasklist.tasklistName }" required />
		<input type="hidden" name="tasklistId" id="tasklistId" value="${tasklist.tasklistID }"/>
		<input type="hidden" name="operation" id="operation" value="update"/> 
		<input type="submit" value="OK" class="btn btn-primary" />
	</form>
	<br>
	<br>
	
	<div class="row">
		<div class="col-lg-6">
			<div class="well bs-component">
				<form class="form-horizontal" action="task" method="post">
					<fieldset>

						<legend>New Task</legend>

						<div class="form-group">
							<label for="inputName" class="col-lg-2 control-label">Name</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="inputName" name="title" placeholder="Task Name" required>
							</div>
						</div>

						<div class="form-group">
							<label for="scope" class="col-lg-2 control-label">Scope</label>
							<div class="col-lg-10">
								<select class="form-control" id="scope" name="scope">
									<option value="R">Private</option>
									<option value="P">Public</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label for="inputDate" class="col-lg-2 control-label">Due Date</label>
							<div class="col-lg-10">
								<input type="date" class="form-control" id="inputDate" placeholder="Due date" name="due_date">
							</div>
						</div>

						<div class="form-group">
							<label for="inputTime" class="col-lg-2 control-label">Due Time</label>
							<div class="col-lg-10">
								<input type="time" class="form-control" id="inputTime" placeholder="Due Time" name="due_time">
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-10">
								<input type="hidden" name="tasklistId" value="${tasklist.tasklistID }" id="tasklistId" />
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-lg-10 col-lg-offset-2">
								<button type="reset" class="btn btn-default">Cancel</button>
								<button type="submit" class="btn btn-primary">Create</button>
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
	
	<div class="col-lg-12">
		<div class="page-header">
			<h1 id="tasks">Tasks</h1>
		</div>

		<div class="bs-component">
			<table class="table table-striped table-hover " id="tasks_table">
				
			</table>
			<div id="source-button" class="btn btn-primary btn-xs"
				style="display: none;">&lt; &gt;</div>
		</div>
		<!-- /example -->
	</div>

	<br>
	<a href="tasklist.jsp">Go to Tasklists</a>
	
</body>
</html>