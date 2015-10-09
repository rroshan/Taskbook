<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.taskbook.dao.TasklistDAO"%>
<%@ page import="com.taskbook.dao.impl.TasklistDAOMySQLImpl"%>
<%@ page import="com.taskbook.bo.Tasklist"%>

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

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<style>
body.padding2 {
	padding-left: 1cm;
}
</style>
</head>

<body class="padding2">

	<h2>Create a new Tasklist</h2>
	<form action="test" method="post">
		Category: <input type="text" name="category" required /> <input
			type="submit" value="OK" class="btn btn-primary" />
	</form>

	<% ArrayList<Tasklist> arrTasklist; TasklistDAO dao = new TasklistDAOMySQLImpl(); arrTasklist = dao.viewAllTasklists(); pageContext.setAttribute("tasklists", arrTasklist); %>

	<div class="col-lg-12">
		<div class="page-header">
			<h1 id="tasklist">Tasklists</h1>
		</div>

		<div class="bs-component">
			<table class="table table-striped table-hover ">
				<tr>
					<th>Category</th>
					<th>Last Modified Date</th>
				</tr>
				<c:forEach items="${tasklists}" var="current">
					<tr class="active">
						<td><a href="update.jsp?tasklistId=${current.tasklistID}">
								<c:out value="${current.taskName}" />
						</a></td>
						<td><c:out value="${current.lastModifiedDate}" /></td>
						<td><a href="testDelete?tasklistId=${current.tasklistID}">Delete</a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<div id="source-button" class="btn btn-primary btn-xs"
				style="display: none;">&lt; &gt;</div>
		</div>
		<!-- /example -->
	</div>

	<script src="js/bootstrap.min.js"></script>
</body>

</html>