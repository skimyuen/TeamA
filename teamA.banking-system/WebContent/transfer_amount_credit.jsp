<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page
	import="model.Customer,model.CreditCard,dao.CustomerDao,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transfer Funds to Credit Card</title>
<script type="text/javascript" src="js/disableback.js">
</script> 
</head>
<body>
	<form action=CustomerServlet?access=payCreditCard method=post>
		<table>
			<tr>
				<td>Account Balance:</td>
				<td>${customer.accountBalance}</td>
			</tr>
			<tr>
				<td>Credit Card:</td>
				<td><select name=creditCardNumber>
						<%
							Customer customer = (Customer) session.getAttribute("customer");

							 List<CreditCard> listOfCreditCards = CustomerDao.getCreditCards(customer.getAccountNumber());
							if (listOfCreditCards != null) {
								for (CreditCard creditcard : listOfCreditCards) {
									out.print("<option value = " + creditcard.getCreditCardNumber() + ">"
											+ creditcard.getCreditCardNumber() + " " + creditcard.getCreditCardSpending() + "/"
											+ creditcard.getCreditCardLimit() + "</option>");
								}
							} 
						%>
				</select></td>
			</tr>
			<tr>
				<td>Amount:</td>
				<td>$<input type=number step = 0.01 name=amount required></td>
			</tr>
			<tr>
			<td>
			<input type = submit value = Transfer>
			</td>
			</tr>
		</table>
	</form>
</body>
</html>