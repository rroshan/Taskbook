<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/javascript" src="js/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>User Profile</title>
<link href="css/userprofile.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<div class="container">
      <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad" >
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title">${userProfile.firstName } ${userProfile.lastName }</h3>
            </div>
            <div class="panel-body">
              <div class="row">
                <div class=" col-md-9 col-lg-9 "> 
                  <table class="table table-user-information">
                    <tbody>
                      <tr>
                        <td>Phone Number:</td>
                        <td>${userProfile.phoneNumber }</td>
                      </tr>
                      <tr>
                        <td>Address:</td>
                        <td>${userProfile.address }</td>
                      </tr>
                      <tr>
                        <td>Karma Points Total:</td>
                        <td>${userProfile.karmaPointsTotal }</td>
                      </tr>
                      <tr>
                        <td>Karma Points Blocked:</td>
                        <td>${userProfile.karmaPointsBlocked }</td>
                      </tr>
                        <tr>
                        <td>Karma Points Balance:</td>
                        <fmt:parseNumber var="i" type="number" value="${userProfile.karmaPointsTotal }" />
                        <fmt:parseNumber var="j" type="number" value="${userProfile.karmaPointsBlocked }" />
                        <td><c:out value="${i-j}" /></td>
                      </tr>
                      <tr>
                        <td>Email:</td>
                        <td><a href="mailto:info@support.com">${userProfile.userId }</a></td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>            
          </div>
        </div>
      </div>
    </div>
</body>
</html>