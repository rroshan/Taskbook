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
<script type="text/javascript" src="jquery.js"></script>

<style type="text/css">
	div{
		padding:8px;
	}
</style>
<script type="text/javascript">
$(document).ready(function(){

    var counter = 2;
		
    $("#addButton").click(function () {
				
	if(counter>30){
            alert("Only 30 subtasks allow");
            return false;
	}   
		
	var newTextBoxDiv = $(document.createElement('div'))
	     .attr("id", 'TextBoxDiv' + counter);
                
	newTextBoxDiv.after().html('<input type="checkbox" id="csubtask'+ counter + '" ' + 'name="csubtask" />' +
	      '<input type="text" name="subtask' + counter + 
	      '" id="subtask' + counter + '" value="" /> <select name="status"' + counter + ' form="subtaskform"> <option value="N">Pending</option> <option value="Y">Completed</option></select>');
            
	newTextBoxDiv.appendTo("#TextBoxesGroup");

				
	counter++;
     });

     $("#removeButton").click(function () {
	if(counter==1){
          alert("No more subtasks to remove");
          return false;
       }
	
	var checkedId = $('input[name="csubtask"]:checked').map(function() {
	    return this.id.substr(8);
	}).get();
	
	console.log(checkedId)
	
	for (var i=0; i < checkedId.length; i++) {
		$("#TextBoxDiv" + checkedId[i]).remove();
		counter--;
	}
			
     });
  });
</script>
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
	
	<h2>Subtasks</h2>
	<form action="testSubTask" method="post" id="subtaskform">
	<div id='TextBoxesGroup'>
		<div id="TextBoxDiv1">
			<input type="checkbox" name="csubtask" id="csubtask1" /> <input type='textbox' id='subtask1' name = "subtask1" />
			<select name="status1" form="subtaskform">
				<option value="N">Pending</option>
  				<option value="Y">Completed</option>
			</select>
		</div>
	</div>
	<input type='button' value='Add subtask' id='addButton'>
	<input type='button' value='Remove subtask' id='removeButton'>
	<input type="submit" value="OK" />
	</form> <br>
	
	<a href="update.jsp?tasklistId=${tasklistId}">Go to tasks</a>
</body>
</html>