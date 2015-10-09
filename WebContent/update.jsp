<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.taskbook.dao.TasklistDAO"%>
<%@ page import="com.taskbook.dao.impl.*"%>
<%@ page import="com.taskbook.bo.Tasklist"%>
<%@ page import="com.taskbook.dao.TaskDAO"%>
<%@ page import="com.taskbook.bo.Task"%>
<%@ page import="java.util.ArrayList"%>

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

<title>Taskbook</title>

<style>
body.padding2 {
	padding-left: 1cm;
}
</style>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="padding2">
	<h2>Update Tasklist Name</h2>
	<%
		Tasklist tasklist;
		int tasklist_id = Integer.parseInt(request
				.getParameter("tasklistId"));
		TasklistDAO dao = new TasklistDAOMySQLImpl();
		tasklist = dao.viewTasklist(tasklist_id);

		pageContext.setAttribute("tasklist", tasklist);
	%>

	<c:set var="tasklist" value="${tasklist}" />

	<form action="testUpdate" method="post">
		Category: <input type="text" name="category"
			value="${tasklist.taskName }" required /> <input type="hidden"
			name="tasklistId" value="${tasklist.tasklistID }" /> <input
			type="submit" value="OK" class="btn btn-primary" />
	</form>
	<br>
	<br>


	<div class="row">
		<div class="col-lg-6">
			<div class="well bs-component">
				<form class="form-horizontal" action="testTask" method="post">
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
							<label for="inputDate" class="col-lg-2 control-label">Date</label>
							<div class="col-lg-10">
								<input type="date" class="form-control" id="inputDate" placeholder="Due date" name="due_date">
							</div>
						</div>

						<div class="form-group">
							<label for="inputTime" class="col-lg-2 control-label">Time</label>
							<div class="col-lg-10">
								<input type="time" class="form-control" id="inputTime" placeholder="Due Time" name="due_time">
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-10 col-lg-offset-2">
								<button type="reset" class="btn btn-default">Cancel</button>
								<button type="submit" class="btn btn-primary">Create</button>
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-10">
								<input type="hidden" name="tasklistId" value="${tasklist.tasklistID }" name="tasklistId"/>
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-10">
								<input type="hidden" name="owner" value="${tasklist.owner }" name="owner"/>
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

	<%
		ArrayList<Task> arrTask;
		TaskDAO taskDAO = new TaskDAOMySQLImpl();
		arrTask = taskDAO.viewAllTasks(tasklist_id);
		
		pageContext.setAttribute("tasks", arrTask);
		%>

	<div class="col-lg-12">
		<div class="page-header">
			<h1 id="tasks">Tasks</h1>
		</div>

		<div class="bs-component">
			<table class="table table-striped table-hover ">
				<tr>
					<th>Title</th>
					<th>Last Modified Date</th>
					<th>Assigned User</th>
					<th>Status</th>
					<th>Scope</th>
					<th>Due Date</th>
				</tr>
				<c:forEach items="${tasks}" var="currentTask">
					<tr class="active">
						<td><a
							href="updatetask.jsp?tasklistId=${tasklist.tasklistID }&taskId=${currentTask.taskId}"><c:out
									value="${currentTask.title}" /></a></td>
						<td><c:out value="${currentTask.lastModifiedDate}" /></td>
						<td><c:out value="${currentTask.assignedUser}" /></td>
						<c:if test="${currentTask.status == 'N'}">
							<td><c:out value="Pending" /></td>
						</c:if>
						<c:if test="${currentTask.status == 'Y'}">
							<td><c:out value="Completed" /></td>
						</c:if>

						<c:if test="${currentTask.scope == 'R'}">
							<td><c:out value="Private" /></td>
						</c:if>
						<c:if test="${currentTask.scope == 'P'}">
							<td><c:out value="Public" /></td>
						</c:if>
						<td><c:out value="${currentTask.dueDate}" /></td>
						<td><a
							href="testTaskDelete?tasklistId=${tasklist.tasklistID }&taskId=${currentTask.taskId}">Delete</a></td>
					</tr>
				</c:forEach>
			</table>
			<div id="source-button" class="btn btn-primary btn-xs"
				style="display: none;">&lt; &gt;</div>
		</div>
		<!-- /example -->
	</div>

	<br>
	<a href="test.jsp">Go to Tasklists</a>
</body>
</html>