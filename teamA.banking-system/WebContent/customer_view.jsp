<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import = "model.Customer" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Main Page</title>
<script type="text/javascript" src="js/disableback.js">
</script> 
<link rel="stylesheet" type="text/css" href="css/bank.css">
</head>
<body>
<%-- <% String logout = request.getParameter("logout");
if(Boolean.parseBoolean(logout){
	response.sendRedirect("login.html");
}
%> --%>

	<div id="mySidenav" class="sidenav">
			<a href="customer_view.jsp">Welcome ${customer.username} </a><br>
			<a href="change_password.html" target="iframe_a">Change Password</a><br>
			<a href="transfer_amount_credit.jsp" target="iframe_a">Transfer to Credit Card</a><br>
			<a href="topup_mobile.jsp" target="iframe_a">Topup Mobile</a><br>
			<a href="view_report.jsp" target="iframe_a">View Report</a><br>
			<a href="apply_for_loan.html" target="iframe_a">Loan Application</a><br>
			<a href="CustomerServlet?access=logout">Logout</a><br>
	
	</div>

	<div id="main">
	
	<div id="header"><h1>Optimum Bank</h1></div>
	<%if (Boolean.parseBoolean(request.getParameter("back"))){ %>
	Do not Refresh or click the Back button!
	<%}%>
	
		<iframe src = "welcome_page.jsp" style="height:80vh; width:100%;" name="iframe_a" frameborder="0">
		
		</iframe>
		
	<div id="footer">Copyright © Optimum Bank Team A - Michelle & Skim</div>
	
	</div>

</body>

</html>