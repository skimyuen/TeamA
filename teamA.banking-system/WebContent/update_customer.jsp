<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "model.Customer,dao.AdminDao" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Customer</title>
</head>
<body>
<form action = AdminServlet?access=updateCustomer method=post>

<% Customer customerRef = AdminDao.getCustomer(Integer.parseInt(request.getParameter("accountNumber"))); %>
<% if (customerRef==null){ 
	out.print("Invalid Account Number!");
	RequestDispatcher ref = request.getRequestDispatcher("update_customer.html");
	ref.include(request,response);} else { %>
<table>

<tr><td>Account Number: </td><td><%= customerRef.getAccountNumber() %></td></tr>
<tr><td>Username: </td><td><%= customerRef.getUsername() %></td></tr>
<tr><td>Full Name: </td><td><%= customerRef.getFullName() %></td></tr>
<tr><td>Address: </td><td><textarea rows="4" cols="50" name = address required><%=customerRef.getAddress() %></textarea></td></tr>
<tr><td>Telephone: </td><td><input type = number name = telephone value = "<%= customerRef.getTelephoneNumber()%>" required></td></tr>

</table><br>
<input type = hidden name = accountNumber value = <%= customerRef.getAccountNumber() %>>
<input type = submit value = Update>
</form>
<% } %>
</body>
</html>