<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page
	import="model.Customer,model.CreditCard,dao.CustomerDao,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Topup Mobile</title>
<script type="text/javascript" src="js/disableback.js">
	
</script>
<script>
	function showSaving() {
		document.getElementById("showChoice").innerHTML = "Account Balance: ";
		document.getElementById("showCardsOrBalance").innerHTML = '${customer.accountBalance}';
		document.getElementById("showSubmit").innerHTML = "Topup Amount: <input type = text name = amount><br><br><input type = submit value = Submit>";
	}
	
	<%Customer customer = (Customer) session.getAttribute("customer");
	List<CreditCard> listOfCreditCards = CustomerDao.getCreditCards(customer.getAccountNumber());
	String statement = "";
		if (listOfCreditCards != null) {
			for (CreditCard creditcard : listOfCreditCards) {
				String option ="<option value = " + creditcard.getCreditCardNumber() + ">"
						+ creditcard.getCreditCardNumber() + " " + creditcard.getCreditCardSpending() + "/"
						+ creditcard.getCreditCardLimit() + "</option>";
						statement = statement.concat(option);
			}
		}
		%>
	function showCreditCard() {
		var asd ="<%=statement%>";
	
	
	document.getElementById("showChoice").innerHTML = "Credit Card: ";
	document.getElementById("showCardsOrBalance").innerHTML = "<select name = creditCard>"+asd+"</select>";
	document.getElementById("showSubmit").innerHTML = "Topup Amount: <input type = text name = amount><br><br><input type = submit value = Submit>";
	}
</script>
</head>
<body>
	
	<form name="topup" action=CustomerServlet?access=topUpMobile
		method=post>
		<table>
			<tr>
				<td>Method:</td>
				<td>Savings</td>
				<td><input type=radio name=method value=savings
					onclick='showSaving();'></td>
			</tr>
			<tr>
				<td></td>
				<td>CreditCard</td>
				<td><input type=radio name=method value=creditCard
					onclick='showCreditCard();'></td>
			</tr>
		</table>
		<table>
			<tr>
				<td><label id=showChoice></label></td>
				<td><p id=showCardsOrBalance></p></td>
			</tr>
		</table>
		<p id=showSubmit></p>
	</form>
</body>
</html>