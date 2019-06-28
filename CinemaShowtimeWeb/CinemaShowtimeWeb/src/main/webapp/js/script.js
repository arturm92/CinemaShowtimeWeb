$(document).ready(function() {
	$("tr").click(function() {
		$(this).addClass('selected').siblings().removeClass('selected');
		var value = $(this).find('td:first').html();
		alert(value);
	});
});