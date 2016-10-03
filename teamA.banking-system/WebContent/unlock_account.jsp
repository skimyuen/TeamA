<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import = "model.Customer,dao.AdminDao,java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Unlock Account</title>
</head>
<body>
<form action = AdminServlet?access=unlockAccount method=post>
<table>
<tr>
<th>Account Number</th>
<th>Full Name</th>
<th>Address</th>
<th>Telephone Number</th>
<th>Unlock?</th>

</tr>

<% List<Customer> listOfLockedAccounts = AdminDao.getLockedAccounts();
	if (listOfLockedAccounts!=null){
	for( Customer customer : listOfLockedAccounts){
		out.print("<tr>");
		out.print("<td>");
		out.print(customer.getAccountNumber());
		out.print("</td><td>");
		out.print(customer.getFullName());
		out.print("</td><td>");
		out.print(customer.getAddress());
		out.print("</td><td>");
		out.print(customer.getTelephoneNumber());
		out.print("</td><td>");
		out.print("<input type = checkbox name = unlock value="+customer.getAccountNumber()+">");
		out.print("</td>");
		out.print("</tr>");
	}}
%>
</table>
<input type = submit value = Submit>
</form>
</body>
</html>