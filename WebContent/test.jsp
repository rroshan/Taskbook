<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>DB Test</title>
</head>
<body>

	<h2>Results</h2>

	<%
	pageContext.setAttribute("tasklists", request.getAttribute("tasklistArray"));
	%>

	<table>
		<th>Tasklist ID</th>
		<th>Category</th>
		<th>Owner</th>
		<c:forEach items="${tasklists}" var="current">
			<tr>
				<td><c:out value="${current.tasklistID}" />
				<td>
				<td><c:out value="${current.taskName}" />
				<td>
				<td><c:out value="${current.owner}" />
				<td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>