<html>
<body>
	<h2>Pobierz dane z API</h2>
	<form id="form" action="/CinemaShowtimeWeb/hi" method="post">
		<select id="id" name="endpoint" required>
			<option value="SHOWTIMES">SHOWTIMES</option>
			<option value="CINEMAS">CINEMAS</option>
			<option value="MOVEIS">MOVEIS</option>
			<option value="CITIES">CITIES</option>
		</select>
		<input type="submit" value="Submit">
	</form>
</body>
</html>
