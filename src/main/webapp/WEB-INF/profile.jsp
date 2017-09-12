<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <link rel="stylesheet" type="text/css" href="css/style.css"> -->
<title>Java Belt Exam</title>
</head>
<body>
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
    <a href="/users">All Users</a>
    
<h1>Hi ${currentUser.name}!</h1>

<h3>Here is your profile description:</h3>
<p>${currentUser.description}</p>

<h3>Your Professional Network:</h3>
<c:forEach var="friend" items="${friendsList}">
	<p><a href="/users/${friend.id}">${friend.name}</a></p>
</c:forEach>

<h4>Invitations:</h4>
<p>The following people asked you to be their network:</p>
<table>
	<tr>
		<th>Name</th>
		<th>Action</th>
	</tr>
	<c:forEach var="row" items="${invitations}">
		<tr>
			<td><a href="/users/${row.id}">${row.name}</a></td>
			<td><a href="/users/${row.id}/accept">Accept Invite</a> | <a href="/users/${row.id}/ignore">Ignore</a></td>
		</tr>
	</c:forEach>
</table>

</body>
</html>