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

<title>Tasklist</title>

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
						'tasklist',
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
								$("#tasklists_table").empty();
								
								var $th = $('<tr>').append(
										$('<th>').text("Category"),
										$('<th>').text("Last Modified Date"));
								
								$th.appendTo('#tasklists_table');

								$.each(resp, function(i, tasklist) {
									var $tr = $('<tr>').append(
											$('<td>').append('<a href="task?tasklistId='+tasklist.tasklistID+'">'+tasklist.tasklistName+'</a>'),
											$('<td>').text(tasklist.lastModifiedDate),
											$('<td>').append('<a href="tasklist?tasklistId='+tasklist.tasklistID+'&operation=delete">Delete</a>'));

									$tr.appendTo('#tasklists_table');
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
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">Home</a>
				</div>
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a href="friends.jsp">Friends</a></li>
						<li><a href="sharedtask.jsp">Shared Task</a></li>
						<li><a href="userProfile">Profile</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li><a href="logout">Logout</a></li>
					</ul>
				</div>
			</div>
			</nav>
		</div>
	<h2>Create a new Tasklist</h2>
	<form action="tasklist" method="post">
		Category: <input type="text" name="category" required /> <input
			type="submit" value="OK" class="btn btn-primary" />
	</form>

	<div class="col-lg-12">
		<div class="page-header">
			<h1 id="tasklist">Tasklists</h1>
		</div>

		<div class="bs-component">
			<table class="table table-striped table-hover " id="tasklists_table">
			</table>
			<div id="source-button" class="btn btn-primary btn-xs"
				style="display: none;">&lt; &gt;</div>
		</div>
		<!-- /example -->
	</div>
</body>
</html>