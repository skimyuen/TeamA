<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import = "model.Customer,model.Transaction,dao.CustomerDao,java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Transactions</title>
<script type="text/javascript" src="js/disableback.js">
</script> 
</head>
<body>

<table>
	<tr>
		<th>ID</th>
		<th>Details</th>
		<th>Amount</th>
		<th>Date & Time</th>

	</tr>

<% Customer customer = (Customer)session.getAttribute("customer");

List<Transaction> listOfTransactions = CustomerDao.getTransactions(customer.getAccountNumber());

	
		if (listOfTransactions != null) {
			for (Transaction transaction : listOfTransactions) {
				if (transaction.isTransactionType()){
					out.print("<tr style='color:green'>");
				}
				else{
					out.print("<tr style='color:red'>");
				}
				out.print("<td>");
				out.print(transaction.getTransactionId());
				out.print("</td><td>");
				out.print(transaction.getTransactionDetails());
				out.print("</td><td>");
				out.print(transaction.getTransactionAmount());
				out.print("</td><td>");
				out.print(transaction.getTransactionTimestamp());
				out.print("</td>");
				out.print("</tr>");
			}
		}
	%>

</table>

</body>
</html>