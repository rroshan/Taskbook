<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.taskbook.bo.Task"%>
<%@ page import="com.taskbook.dao.TaskDAO"%>
<%@ page import="com.taskbook.dao.impl.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
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
	
	<a href="update.jsp?tasklistId=${tasklistId}">Go to tasks</a>
</body>
</html>