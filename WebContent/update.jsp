<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.taskbook.dao.TaskbookDAO"%>
<%@ page import="com.taskbook.bo.Tasklist"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Update tasklist</title>
</head>
<body>
	<h1>Update Tasklist</h1>
	<%
		Tasklist tasklist;
		int tasklist_id = Integer.parseInt(request
				.getParameter("tasklistId"));
		TaskbookDAO dao = new TaskbookDAO();
		tasklist = dao.viewTasklist(tasklist_id);

		pageContext.setAttribute("tasklist", tasklist);
	%>

	<c:set var="tasklist" value="${tasklist}" />

	<form action="testUpdate" method="post">
		Category: <input type="text" name="category" value=${tasklist.taskName } /> <br>
		<input type="hidden" name="tasklistId" value=${tasklist.tasklistID } /> 
		<input type="submit" value="OK" />
	</form>
</body>
</html>