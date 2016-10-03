<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import = "model.Customer" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="js/disableback.js">
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
</head>
<body>
Hello 
		<% Customer customer = (Customer)session.getAttribute("customer");
		out.print(customer.getFullName()+"<br>");
		if (customer.getLastLogin()==null) {%>
		<%@ include file = "change_password.html" %>
		<% } else { %>
			Your Last Login: ${customer.lastLogin}
		<% } %>
		
</body>
</html>