<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 

	<head>
		<title>My First Application</title>
	</head>
	<body>
	<table>
		<c:forEach var="user" items="${users}">
       <!-- create an html table row -->
       <tr>
       <!-- create cells of row -->
       <td>${user.display_name}, </td>
       <td>${user.city.name}</td>
       <!-- close row -->
       </tr>
       <!-- close the loop -->
   </c:forEach>
   </table>
	</body>
</html>