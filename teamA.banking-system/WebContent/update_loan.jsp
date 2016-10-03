<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="model.Loan,dao.AdminDao,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Loan Reviews</title>
</head>
<body>
	<form action=AdminServlet?access=updateLoan method=post>
		<table>
			<tr>
				<th>Loan ID</th>
				<th>Applicant Name</th>
				<th>Application Date</th>
				<th>Loan Amount</th>
				<th>Interest Rate</th>
				<th>Approve</th>
				<th>Reject</th>
				<th>Pending</th>

			</tr>

			<%
				List<Loan> listOfPendingLoans = AdminDao.getPendingLoans();
				if (listOfPendingLoans != null) {
					for (Loan loan : listOfPendingLoans) {
						out.print("<input type = hidden name = accountNumber value=" + loan.getAccountNumber() + ">");
						int loanid = loan.getLoanId();
						out.print("<tr>");
						out.print("<td>");
						out.print(loanid);
						out.print("<input type = hidden name = loanIds value=" + loanid + ">");
						out.print("</td><td>");
						out.print(loan.getFullName());
						out.print("</td><td>");
						out.print(loan.getLoanApplicationTimestamp());
						out.print("</td><td>");
						out.print(loan.getLoanAmount());
						out.print("<input type = hidden name = loanAmount value=" + loan.getLoanAmount() + ">");
						out.print("</td><td>");
						out.print(loan.getInterestRate());
						out.print("</td><td>");
						out.print("<input type = radio name =" + loanid + " value=approve required");
						out.print("</td><td>");
						out.print("<input type = radio name =" + loanid + " value=reject required");
						out.print("</td><td>");
						out.print("<input type = radio name =" + loanid + " value=pending checked required");
						out.print("</td>");
						out.print("</tr>");
					}
				}
			%>

		</table>
		<input type=submit value=Submit>
	</form>
</body>
</html>