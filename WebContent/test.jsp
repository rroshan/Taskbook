<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.taskbook.dao.TasklistDAO"%>
<%@ page import="com.taskbook.dao.impl.TasklistDAOMySQLImpl"%>
<%@ page import="com.taskbook.bo.Tasklist"%>

<html>
<head>
<title>DB Test</title>
</head>
<body>

	<h2>Insert new tasklist</h2>
	<form action="test" method="post">
		Category: <input type="text" name="category" required/> <br> 
		<input type="submit" value="OK" />
	</form>
	<h2>Tasklists</h2>

	<%
		ArrayList<Tasklist> arrTasklist;
		TasklistDAO dao = new TasklistDAOMySQLImpl();
		arrTasklist = dao.viewAllTasklists();
		
		pageContext.setAttribute("tasklists", arrTasklist);
	%>

	<table border="1">
		<tr>
			<th>Tasklist ID</th>
			<th>Category</th>
			<th>Created Date</th>
			<th>Last Modified Date</th>
			<th>Owner</th>
		</tr>
		<c:forEach items="${tasklists}" var="current">
			<tr>
				<td><c:out value="${current.tasklistID}" /></td>
				<td><a href="update.jsp?tasklistId=${current.tasklistID}"><c:out value="${current.taskName}" /></a></td>
				<td><c:out value="${current.createdDate}" /></td>
				<td><c:out value="${current.lastModifiedDate}" /></td>
				<td><c:out value="${current.owner}" /></td>
				<td><a href="testDelete?tasklistId=${current.tasklistID}">Delete</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>