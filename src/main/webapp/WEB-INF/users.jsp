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
    <a href="/professional_profile">My Profile</a>

<h2>Users you may want to connect with:</h2>

<table>
	<tr>
		<th>Name</th>
		<th>Action</th>
	</tr>
	<c:forEach var="user" items="${allUsers}">
	<c:if test="${user != currentUser}">
		<c:if test="${!user.getInvites_to_me().contains(currentUser) && !user.getInvites_i_sent().contains(currentUser)}">
			<c:if test="${!user.getFriends_i_added().contains(currentUser)}">
				<tr>
					<td><a href="/users/${user.id}">${user.name}</a></td>
					<td><a href="/users/${user.id}/connect">Connect</a></td>
				</tr>
			</c:if>
		</c:if>
	</c:if>
	</c:forEach>
</table>

</body>
</html>