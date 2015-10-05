<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.taskbook.dao.TaskbookDAO"%>
<%@ page import="com.taskbook.bo.Tasklist"%>

<html>
<head>
<title>DB Test</title>
</head>
<body>

	<h2>Insert new tasklist</h2>
	<form action="test" method="post">
		Category: <input type="text" name="category" /> <br> <input
			type="submit" value="OK" />
	</form>
	<h2>Results</h2>

	<%
		ArrayList<Tasklist> arrTasklist;
		TaskbookDAO dao = new TaskbookDAO();
		arrTasklist = dao.viewTasklist();
		
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
				<td><c:out value="${current.taskName}" /></td>
				<td><c:out value="${current.createdDate}" /></td>
				<td><c:out value="${current.lastModifiedDate}" /></td>
				<td><c:out value="${current.owner}" /></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>