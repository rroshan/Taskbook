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

<title>Collaboration</title>

<script type="text/javascript" src="js/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>

<style type="text/css">
div {
	padding: 8px;
}
</style>

<script type="text/javascript">
	function sendRequest(button) {
		var helperEmail = $(button).closest('tr').attr('id');
		var taskId = $("#taskId").val();
		var tasklistId = $("#tasklistId").val();
		
		$.post('collaboration', {"helperEmail":helperEmail, "tasklistId": tasklistId, "taskId":taskId, "operation":"send_help_request"},
				 function(resp) {
					if(resp.type === "login" && resp.message === "Failed") 
					{
						window.location = "login.jsp";
					}
					else
					{
						if(resp.type === 'request_mail' && resp.message === "Success")
						{
							$("#response_message").empty().append(
									'<div class="alert alert-dismissible alert-success">'
											+ 'Request mail successfully sent' + '</div>');
						}
						else if(resp.type === 'request_mail' && resp.message === "Fail")
						{
							$("#response_message").empty().append(
									'<div class="alert alert-dismissible alert-danger">'
											+ 'Insufficient Karma Balance' + '</div>');
						}
					}
		 })
		 .fail(function() {
				alert("Failed to delete comment");
		 });
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
	<div class="col-lg-12">
		<div class="page-header">
			<h1 id="tasks">Friends Doing similar tasks</h1>
		</div>

		<div class="bs-component">
			<input type=hidden id="taskId" name=taskId value="${taskId }">
			<input type=hidden id="tasklistId" name=tasklistId value="${tasklistId }">
			<table class="table table-striped table-hover " id="resultTable">
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Task Title</th>
					<th>Due Date</th>
					<th>Phone</th>
					<th>Address</th>
					<th>Send Request</th>
				</tr>
				<c:forEach items="${collaboratorsList}" var="currentCollaborator">
					<tr class="active" id="${currentCollaborator.userId}">
						<td class="fname"><c:out value="${currentCollaborator.fName}" /></td>
						<td class="lname"><c:out value="${currentCollaborator.lName}" /></td>
						<td class="taskTitle"><c:out value="${currentCollaborator.taskTitle}" /></td>
						<td class="dueDate"><c:out value="${currentCollaborator.dueDate}" /></td>
						<td><c:out value="${currentCollaborator.phone}" /></td>
						<td><c:out value="${currentCollaborator.address}" /></td>
						<td><button onclick="sendRequest(this)" id="button_${currentCollaborator.userId}" class='btn btn-success'>Send Request</button>
					</tr>
				</c:forEach>
			</table>
			<div id="source-button" class="btn btn-primary btn-xs"
				style="display: none;">&lt; &gt;</div>
		</div>
		
		<div class="col-lg-4">
			<div class="bs-component" id="response_message">
		
			</div>
		</div>
	</div>
</body>
</html>