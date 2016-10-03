package control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AdminDao;
import dao.CustomerDao;
import model.CreditCard;
import model.Customer;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	RequestDispatcher ref;
	HttpSession session;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (out == null)
			out = response.getWriter();
		if (session == null)
			session = request.getSession();

		// get parameter access to check function to be accessed
		String access = request.getParameter("access");
		switch (access) {
		case "newCustomer":
			// get parameters to create new customer
			String username = request.getParameter("username");
			String fullName = request.getParameter("fullName");
			String address = request.getParameter("address");
			String telephone = request.getParameter("telephone");
			String accountBalance = request.getParameter("accountBalance");
			// validate if all parameters are received
			if (username != null && fullName != null && address != null && telephone != null
					&& accountBalance != null) {
				// parse account balance to be a double
				double accountBal = Double.parseDouble(accountBalance);
				// create customer object and send to admindao to create in
				// database
				Customer cust = new Customer(username, fullName, telephone, address, accountBal);
				boolean customerCreated = AdminDao.createNewCustomer(cust);
				if (customerCreated) {
					// set customer object as an attribute in session
					session.setAttribute("customer", cust);
					// out.println("Customer created");
					// set to redirect to customer_created.jsp
					ref = request.getRequestDispatcher("customer_created.jsp");
				} else {
					// error occured in creating customer in database
					out.println("Some error occured in creating customer");
					// set to redirect to new_customer.jsp
					ref = request.getRequestDispatcher("new_customer.jsp");
				}
			} else {
				out.println("Ensure that all parameters are entered");
				ref = request.getRequestDispatcher("new_customer.jsp");
			}
			break;
		case "updateCustomer":
			// get parameters to update customer
			String accNum = request.getParameter("accountNumber");
			String tele = request.getParameter("telephone");
			String addr = request.getParameter("address");
			// send to admin dao to update customer in database
			if (accNum != null && tele != null && addr != null) {
				Customer cust = AdminDao.getCustomer(Integer.parseInt(accNum));
				cust.setTelephoneNumber(tele);
				cust.setAddress(addr);
				boolean pass = AdminDao.updateCustomer(cust);
				if (pass) {
					out.println("Update Success!");
					ref = request.getRequestDispatcher("update_customer.html");
				} else {
					out.println("Error occured when updating customer details");
					ref = request.getRequestDispatcher("update_customer.html");
				}
			} else {
				out.println("Please ensure that all fields are filled in");
				ref = request.getRequestDispatcher("update_customer.html");
			}
			break;
		case "unlockAccount":
			// get parameters for accounts to be unlocked
			String[] accountNums = request.getParameterValues("unlock");
			for (String accountNumber : accountNums) {
				// use setAccountUnlock in admin dao
				Boolean unlocked = false;
				if (accountNumber != null)
					unlocked = AdminDao.setAccountUnlock(Integer.parseInt(accountNumber));
				if (unlocked) {
					out.println("Account number " + accountNumber + " has been unlocked");
				} else {
					out.println("Error in unlocking account number " + accountNumber);
				}
			}
			ref = request.getRequestDispatcher("unlock_account.jsp");
			break;
		case "updateLoan":
			// get parameters for updating loans
			String[] loanIds = request.getParameterValues("loanIds");
			String adminUsername = (String) session.getAttribute("admin");
			for (String loanId : loanIds) {
				Boolean pass = false;
				String loanStatus = "";
				if (loanId != null) {
					loanStatus = request.getParameter(loanId);
					pass = AdminDao.updateLoan(Integer.parseInt(loanId), loanStatus, adminUsername);
				}
				if (pass) {
					if(loanStatus.equals("approve")){
						// update customer account with loan amount
						Boolean instantMoney = AdminDao.instantMoney(Integer.parseInt(loanId));
						if (instantMoney) {// success message for instant money
							// transfer update
							out.println("Instant money transfer for loanId " + loanId + " successful");
							CustomerDao.addTransaction(Double.parseDouble(request.getParameter("loanAmount")), true,
									"Loan Approval to Savings", Integer.parseInt(request.getParameter("accountNumber")));
						} else // failure message for instant money transfer update
							out.println("Instant money transfer for loanId " + loanId + " unsuccessful");
					}
					// success message for loan update
					out.println("Update of loan: " + loanId + " passed");
				} else {
					out.println("An error occured while updating loan: " + loanId);
				}
			}
			ref = request.getRequestDispatcher("update_loan.jsp");
			break;
		case "newCreditCard":
			String accountNumber = request.getParameter("accountNumber");
			String limit = request.getParameter("creditCardLimit");
			CreditCard creditCard = null;
			if (accountNumber != null && limit != null) {
				creditCard = AdminDao.createCreditCard(Integer.parseInt(limit), Integer.parseInt(accountNumber));
			}
			if (creditCard != null) {
				out.println("Credit Card has been created successfully");
				out.println("<br><br>Credit Card Number is :" + creditCard.getCreditCardNumber());
				out.println("<br>Credit Card CVV is :" + creditCard.getCreditCardCvv());
			} else {
				out.println("Credit Card not created!");
			}
			ref = request.getRequestDispatcher("");
			break;
		case "logout":
			if (session != null)
				session.invalidate();
			out.print("You have been logged out.");
			ref = request.getRequestDispatcher("login.html");
			break;
		default:
			out.print("invalid access method");
			ref = request.getRequestDispatcher("login.html");
			break;
		}
		ref.include(request, response); // redirect functions according to ref
	}

}
