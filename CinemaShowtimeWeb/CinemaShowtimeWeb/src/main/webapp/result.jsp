<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:c="http://java.sun.com/jsp/jstl/core">
<head>
<h:link rel="stylesheet" type="text/css" href="css/style.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
<script src="js/script.js" type="text/javascript"></script>
</head>
<body>
	<h:panelGrid id="panel" columns="4">
		<table id="table">
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
		<!-- <h:inputText id="selectedRecord" value='' label="SelectedRecord"/> -->
	</h:panelGrid>
</body>
</html>