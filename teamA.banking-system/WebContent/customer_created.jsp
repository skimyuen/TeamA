<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Created</title>
<script type="text/javascript" src="js/disableback.js">
</script>
</head>
<body>
<h1>Account Details:</h1><br>
Account Number: ${customer.accountNumber}<br>
Account Balance: ${customer.accountBalance}<br>
<br>

Username: ${customer.username}<br>
Password: ${customer.password}<br>
<br>
Full Name: ${customer.fullName}<br>
Telephone Number:${customer.telephoneNumber}<br>
Address: ${customer.address}<br>
<br>


Note: Online Account Created! <br>
Please inform Service Representative if there is any error.<br><br>

<form action = new_credit_card.jsp><input type=submit value="New Credit Card?"></form>

</body>
</html>