<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
	<table>
		<tr>
			<th>Id</th>
			<th>Name</th>
		</tr>
		<c:forEach items='${list}' var="item">
			<tr>
				<td><c:out value='${item.id}' /></td>
				<td><c:out value='${item.name}' /></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>