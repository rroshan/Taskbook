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
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Update tasklist</title>
</head>
<body>
	<h2>Update Tasklist</h2>
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
		Category: 
		<input type="text" name="category" value="${tasklist.taskName }" required/> <br>
		<input type="hidden" name="tasklistId" value="${tasklist.tasklistID }" /> 
		<input type="submit" value="OK" />
	</form>

	<h2>New Task</h2>
	<form action="testTask" method="post">
		Name: <input type="text" name="title" required/> <br>
		Scope: 
		<select	name="scope">
			<option value="R">private</option>
			<option value="P">public</option>
		</select> <br>
		Due Date: <input type="date" name="due_date" required/> <br>
		Due Time: <input type="time" name="due_time"/> <br> 
		<input type="hidden" name="tasklistId" value="${tasklist.tasklistID }" /> 
		<input type="hidden" name="owner" value="${tasklist.owner }" /> 
		<input type="submit" value="OK" />
	</form>
	
		<%
		ArrayList<Task> arrTask;
		TaskDAO taskDAO = new TaskDAOMySQLImpl();
		arrTask = taskDAO.viewAllTasks(tasklist_id);
		
		pageContext.setAttribute("tasks", arrTask);
	%>


	<h2>Tasks</h2>

	<table border="1">
		<tr>
			<th>Task ID</th>
			<th>Title</th>
			<th>Created Date</th>
			<th>Last Modified Date</th>
			<th>Assigned User</th>
			<th>Status</th>
			<th>Scope</th>
			<th>Due Date</th>
		</tr>
		<c:forEach items="${tasks}" var="currentTask">
			<tr>
				<td><c:out value="${currentTask.taskId}" /></td>
				<td><a href="updatetask.jsp?tasklistId=${tasklist.tasklistID }&taskId=${currentTask.taskId}"><c:out value="${currentTask.title}" /></a></td>
				<td><c:out value="${currentTask.createdDate}" /></td>
				<td><c:out value="${currentTask.lastModifiedDate}" /></td>
				<td><c:out value="${currentTask.assignedUser}" /></td>
				<td><c:out value="${currentTask.status}" /></td>
				<td><c:out value="${currentTask.scope}" /></td>
				<td><c:out value="${currentTask.dueDate}" /></td>
				<td><a href="testTaskDelete?tasklistId=${tasklist.tasklistID }&taskId=${currentTask.taskId}">Delete</a></td>
			</tr>
		</c:forEach>
	</table>
	
	<br>
	<a href="test.jsp">Go to Tasklists</a>
</body>
</html>