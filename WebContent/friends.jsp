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

<title>Add Friend on Taskbook</title>

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
					'managefriend',
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
							$("#friends_table").empty();
							
							var $th = $('<tr>').append(
									$('<th>').text("First Name"),
									$('<th>').text("Last Name"),
									$('<th>').text("Phone"),
									$('<th>').text("Address"));
							
							$th.appendTo('#friends_table');
	
							$.each(resp, function(i, friend) {
								
								var $tr = $('<tr>').append(
										$('<td>').text(friend.firstName),
										$('<td>').text(friend.lastName),
										$('<td>').text(friend.phoneNumber),
										$('<td>').text(friend.address));
	
								$tr.appendTo('#friends_table');
							});
						}
					}).fail(function() {
				alert("Page load failed!");
			});
			
			$("#add_friend_form").submit(
					function(e) {
						e.preventDefault();
						var self = this;

						var recipient_email = $("#recipient_email").val();

						$.ajax({
							type : "POST",
							url : "managefriend",
							data : {
								recipient_email : recipient_email,
								operation : "validation"
							},
							cache : false
						}).done(
								function(resp) {
									if(resp.type === "login" && resp.message === "Failed") 
									{
										window.location = "login.jsp";
									}
									else
									{
										if (resp.type === "Fail") {
											$("#response_message").empty().append(
													'<div class="alert alert-dismissible alert-danger">'
															+ resp.message + '</div>');
										} else if(resp.type == "Notification") {
											$("#response_message").empty().append(
													'<div class="alert alert-dismissible alert-info">'
															+ resp.message + '</div>');
										} else if (resp.type === "Success") {
											self.submit();
										}
									}
								}).fail(function() {
							alert('error');
						});
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
					<a class="navbar-brand" href="tasklist.jsp">Home</a>
				</div>
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a href="#">Friends</a></li>
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

	<h2>Add a New Friend</h2>
	<form action=managefriend id="add_friend_form" method="post">
		Email ID: <input type="text" id="recipient_email" name="recipient_email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" required />
			<button type="submit" class="btn btn-primary">Send Request</button>
	</form> <br> <br>


	<div class="col-lg-4">
		<div class="bs-component" id="response_message">
			<c:if test="${not empty message}">
				<div class="alert alert-dismissible alert-success">${message }
				</div>
			</c:if>
		</div>
	</div>


	<div class="col-lg-12">
		<div class="page-header">
			<h1 id="tasks">Friends</h1>
		</div>

		<div class="bs-component">
			<table class="table table-striped table-hover " id="friends_table">

			</table>
			<div id="source-button" class="btn btn-primary btn-xs"
				style="display: none;">&lt; &gt;</div>
		</div>
		<!-- /example -->
	</div>
	
	<div class="col-lg-4">
		<div class="bs-component" id="accept_msg">
			<c:if test="${not empty err_message}">
				<div class="alert alert-dismissible alert-danger">${err_message }
				</div>
			</c:if>
		</div>
	</div>


</body>
</html>