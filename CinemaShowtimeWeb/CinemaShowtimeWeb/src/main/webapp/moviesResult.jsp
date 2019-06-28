<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="js/script.js" type="text/javascript"></script>
</head>
<body>
	<table id="table">
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Info</th>
		</tr>
		<c:forEach items='${list}' var="item">
			<tr>
				<td><c:out value='${item.id}' /></td>
				<td><c:out value='${item.name}' /></td>
				<td><img src='${item.poster}'></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>