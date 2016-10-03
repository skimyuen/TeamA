<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Main Page</title>
<link rel="stylesheet" type="text/css" href="css/bank.css">
</head>
<body>

	<div id="mySidenav" class="sidenav">
		<a href="admin_view.jsp">Welcome</a><br> 
		<a href="new_customer.jsp" target="iframe_a">Create Customer</a><br>
		<a href="update_customer.html" target="iframe_a">Update Customer</a><br>
		<a href="unlock_account.jsp" target="iframe_a">Review Locked Accounts</a><br> 
		<a href="update_loan.jsp" target="iframe_a">Review Loans</a><br> 
		<a href="AdminServlet?access=logout">Logout</a><br>

	</div>

	<div id="main">

	<div id="header"><h1>Optimum Bank</h1></div>
	
		<iframe src="" style="height:80vh; width:100%;" name="iframe_a" frameborder="0"></iframe>
		
	<div id="footer">Copyright © Optimum Bank Team A - Michelle & Skim</div>
	
	</div>

</body>
</html>