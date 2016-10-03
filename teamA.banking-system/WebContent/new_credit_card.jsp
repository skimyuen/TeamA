<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="model.Customer,dao.AdminDao"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Credit Card</title>
</head>
<body>
	<!-- To get session object of created customer, if any, 
		1. populate account number textbox
		2. Validate button to retrieve full name of customer in which credit card would be generated for
		3. Admin to select credit card limit if and only if full name of customer has already been retrieved
		4. Upon submission of form 2 with "Generate Card" button, redirect to AdminController. Information would be saved, and credit card generated details would be displayed to admin
	 -->

	<%
		Customer customerRef = (Customer) session.getAttribute("customer");
	%>


	<form action=new_credit_card.jsp>
		<%
			if (customerRef != null && customerRef.getAccountNumber() != 0) {
		%><input
			type=text name=accountNumber
			value=<%=customerRef.getAccountNumber()%> disabled>
		<%
			} else {
		%>
		<input type=number name=accountNumber required>
		<%
			}
		%>
		<input type=submit value=Validate>
	</form>
	<%
		String temp = request.getParameter("accountNumber");
		String fullname = null;
		if (temp !=null){
			fullname = AdminDao.getCustomerFullName(Integer.parseInt(temp));}
		if (fullname != null) {
		out.print("<br>Full Name: " +fullname+"<br><br>");
	%>
	<form action=AdminServlet?access=newCreditCard method=post>
		<select name=creditCardLimit>
			<option>1000</option>
			<option>2000</option>
			<option>5000</option>
		</select> <input type="hidden" name="accountNumber"
			value="${param.accountNumber}"> <input type="submit"
			value="Generate Card">
	</form>
	<%
		}
	%>


</body>
</html>