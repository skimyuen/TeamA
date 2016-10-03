package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CustomerDao;
import model.Customer;
import model.Loan;

/**
 * Servlet implementation class CustomerServlet
 */
@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	RequestDispatcher ref;
	HttpSession session;
	Customer customer;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (out == null)
			out = response.getWriter();
		if (session == null)
			session = request.getSession();
		if (customer == null)
			customer = (Customer) session.getAttribute("customer");

		// get parameter access to check function to be accessed
		String access = request.getParameter("access");
		switch (access) {
		case "changePassword":
			// get paramter password
			String password = request.getParameter("password");
			String retypePassword = request.getParameter("retypePassword");
			if (password != null && customer != null && password.equals(retypePassword)) {
				// use customer dao method to set new password
				CustomerDao.changePassword(customer.getAccountNumber(), password);
				// change password in session customer
				customer.setPassword(password);
				if (customer.getLastLogin() == null) {
					// change in database
					CustomerDao.setLastLogin(customer);
					// change in object
					customer.setLastLogin(new Timestamp(new java.util.Date().getTime()));
				}
				out.println("Password Changed Successfully.");
			} else {
				out.print("Password Not Changed.(Maybe Password different?/" + "Did you enter any password?)");
			}
			break;
		case "payCreditCard":
			// get parameters
			String ccNum = request.getParameter("creditCardNumber");
			String amt = request.getParameter("amount");
			if (ccNum != null && amt != null && customer != null) {
				// use customer dao method to pay credit card
				if (CustomerDao.payCreditCard(Double.parseDouble(amt), Long.parseLong(ccNum),
						customer.getAccountNumber(), customer.getAccountBalance())) {
					// update account balance in customer
					customer.setAccountBalance(customer.getAccountBalance() - Double.parseDouble(amt));
					out.println("Credit Card Paid Successfully.");
				}

			} else {
				out.print("Credit Card Not Paid.(Maybe Savings not enough?/" + "Maybe Spending not so much?)");
			}
			break;
		case "topUpMobile":
			// get parameters
			String topupAmount = request.getParameter("amount");
			String method = request.getParameter("method");
			String creditCardNumber = request.getParameter("creditCard");
			int counter = 0;
			if (topupAmount != null && method != null && creditCardNumber != null) {
				// use customer dao method to top up mobile - using credit card
				counter = CustomerDao.topUpMobile(customer.getAccountNumber(), Double.parseDouble(topupAmount),
						Long.parseLong(creditCardNumber));

			} else if (topupAmount != null && method != null) {
				// user customer dao method to top up mobile - using savings
				// account
				counter = CustomerDao.topUpMobile(customer.getAccountNumber(), Double.parseDouble(topupAmount),
						customer.getAccountBalance());
				// update account balance in customer
				customer.setAccountBalance(customer.getAccountBalance() - Double.parseDouble(topupAmount));
			}

			if (counter == 2) {
				out.print("Topup from credit card:" + creditCardNumber + " successful!");
			} else if (counter == 1) {
				out.print("Topup from savings account successful!");
			} else {
				out.print("Topup not successful.(Maybe no money in account?/Maybe not enough credit?)");
			}
			break;
		case "newLoan":
			// get parameters
			String LoanAmount = request.getParameter("amount");
			String rate = request.getParameter("rate");
			if (LoanAmount != null && rate != null && customer != null) {
				// use customer dao method to create new loan
				Loan l = new Loan(customer.getFullName(), customer.getAccountNumber(), 0,
						Double.parseDouble(LoanAmount), Double.parseDouble(rate));
				CustomerDao.createLoan(l);
				out.print("Loan Application successful.");
			} else {
				out.print("Your Loan Application is rejected before application.");
			}
			break;
		case "logout":
			session = request.getSession(false);
			if (session != null)
				session.invalidate();
			// out.println("You successfully logged out.");
			// ref = request.getRequestDispatcher("login.html");
			// ref.include(request, response);
			response.sendRedirect("login.html");
			break;
		}

	}
}
