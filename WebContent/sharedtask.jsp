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

<title>Shared Task</title>

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
					'collaboration',
					{
						"operation" : "load_all"
					},
					function(resp) {
						if(resp.type === "login" && resp.message === "Failed") 
						{
							window.location = "login.jsp";
						} 
						else 
						{
							$("#shared_tasks_table").empty();
							
							var $th = $('<tr>').append(
									$('<th>').text("Title"),
									$('<th>').text("Last Modified Date"),
									$('<th>').text("Owner"),
									$('<th>').text("Assigned User"),
									$('<th>').text("Status"),
									$('<th>').text("Due Date"));
							
							$th.appendTo('#shared_tasks_table');
	
							$.each(resp, function(i, task) {
								var status, scope;
								if(task.status === 'C')
								{
									status = 'Completed';
								}
								else
								{
									status = 'Pending';
								}
								
								var $tr = $('<tr>').append(
										$('<td>').append('<a href="subtask?tasklistId='+task.tasklistId+'&taskId='+ task.taskId + '">'+task.title+'</a>'),
										$('<td>').text(task.lastModifiedDate),
										$('<td>').text(task.owner),
										$('<td>').text(task.assignedUser),
										$('<td>').text(status),
										$('<td>').text(task.dueDate));
	
								$tr.appendTo('#shared_tasks_table');
							});
						}
					}).fail(function() {
				alert("Page load failed!");
			});
		});
</script>

<link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
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
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="logout">Logout</a></li>
				</ul>
			</div>
		</div>
		</nav>
	</div>
	
	<div class="col-lg-12">
		<div class="page-header">
			<h1 id="tasks">Shared Tasks</h1>
		</div>

		<div class="bs-component">
			<table class="table table-striped table-hover " id="shared_tasks_table">
				
			</table>
			<div id="source-button" class="btn btn-primary btn-xs"
				style="display: none;">&lt; &gt;</div>
		</div>
		<!-- /example -->
	</div>
	
</body>
</html>