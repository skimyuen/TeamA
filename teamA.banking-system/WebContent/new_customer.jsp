<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<title>New Customer</title>
<script type="text/javascript" src="js/disableback.js">
</script>
</head>
<body>
	<form action=AdminServlet?access=newCustomer method = post>
		<table>
			<tr>
				<td>Username:</td>
				<td><input type="text" name="username" required></td>
			</tr>
			<tr>
				<td>Full Name:</td>
				<td><input type="text" name="fullName" required></td>
			</tr>
			<tr>
				<td>Address:</td>
				<td><textarea rows="4" cols="30" name="address" required></textarea></td>
			</tr>
			<tr>
				<td>Telephone:</td>
				<td><input type="number" name="telephone" required></td>
			</tr>
			<tr>
				<td>Account Balance:</td>
				<td><input type="number" name="accountBalance" required></td>
			</tr>
			<tr>
				<td><input type="submit" value=Submit></td>
			</tr>
		</table>
	</form>
</body>
</html>