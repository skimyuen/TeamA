package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dbConnection.MyConnection;
import model.*;

public class AdminDao {
	private static int accountId = 127999999;
	private static int pwd = 12345;
	private static long creditCardNumber = 3501872475324552L;
	private static int cvv = 123;

	private static int generateAccId() {
		accountId++;
		return accountId;
	}

	public static String generatePassword() {
		//pwd++;
		return String.valueOf(pwd);
	}

	public static boolean createNewCustomer(Customer c){
		do{
			c.setAccountNumber(generateAccId());
			}while(checkIfExists(c.getAccountNumber()));
		c.setPassword(generatePassword());
		if(!checkIfExists(c.getAccountNumber())){
			if(createCustomer(c)){
				System.out.println(c.getAccountNumber());
				return true;}
		}
		return false;
	}

	public static boolean checkIfExists(int accountNumber) {
		if (getCustomer(accountNumber) == null)
			return false;
		return true;
	}

	public static String getCustomerFullName(int accountNumber) {
		Customer c = getCustomer(accountNumber);
		if (c != null)
			return c.getFullName();
		return null;
	}

	public static Customer getCustomer(int accountNumber) {
		Customer customer = null;
		try {
			Connection con = MyConnection.myConnection();
			Statement st = con.createStatement();
			String query = "Select * from Customers where account_number ='" + accountNumber + "'";
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				// check in resultset if account lock
				int account_number = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				String full_name = rs.getString(4);
				String telephone_number = rs.getString(5);
				String address = rs.getString(6);
				double account_balance = rs.getDouble(7); // not sure if this
															// will get through
				Timestamp last_login = rs.getTimestamp(9);
				boolean lock = rs.getBoolean(10);
				boolean request_unlock = rs.getBoolean(11);
				customer = new Customer(username, password, full_name, telephone_number, address, account_balance, lock,
						request_unlock, last_login, account_number);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customer;
	}

	private static boolean createCustomer(Customer c) {
		// sql to insert customer into database
		String query = "INSERT INTO Customers (account_number,username,password,full_name,telephone_number,address,account_balance,isLocked,request_unlock) VALUES(?,?,?,?,?,?,?,?,?)";
		try {
			Connection con = MyConnection.myConnection();
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, c.getAccountNumber()); // transaction_details
			st.setString(2, c.getUsername()); // transaction_amount
			st.setString(3, c.getPassword());
			st.setString(4, c.getFullName());
			st.setString(5, c.getTelephoneNumber()); // transaction_details
			st.setString(6, c.getAddress()); // transaction_amount
			st.setDouble(7, c.getAccountBalance());
			st.setBoolean(8, false);
			st.setBoolean(9, false); // transaction_details

			st.executeUpdate();
			// System.out.println("record has been inserted");
			st.close();
			con.close();
		} catch (SQLException | ClassNotFoundException se) {
			se.printStackTrace();
			return false;
		}
		return true;
	}

	// function to update customer according to customer object parsed in
	public static boolean updateCustomer(Customer c) {
		try {
			Connection con = MyConnection.myConnection();
			String delete = "UPDATE Customers SET telephone_number=?,address=? WHERE account_number =?";

			PreparedStatement ps = con.prepareStatement(delete);

			ps.setString(1, c.getTelephoneNumber());
			ps.setString(2, c.getAddress());
			ps.setInt(3, c.getAccountNumber());
			ps.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// function to delete customer with accountId
	public static boolean deleteCustomer(int accountNumber) {
		// to build in future - what to do if customers have existing loans,
		// money in account?
		return false;
	}

	// function to create credit card if it doesn't already exist on database
	public static CreditCard createCreditCard(int limit, int accountNumber) {
		CreditCard creditCard = null;
		if (checkIfExists(accountNumber)) {
			
			// database query to create credit card for credit card object cc
			String query = "INSERT INTO Credit_Cards VALUES(?,?,?,?,?,?)";
			try {
				Connection con = MyConnection.myConnection();
				PreparedStatement st = con.prepareStatement(query);
				long CreditCardNumber = generateCreditCardNumber();
				int CreditCardCVV = generateCvv();
				st.setLong(1, generateCreditCardNumber()); // transaction_details
				st.setInt(2, limit); // transaction_amount
				st.setDouble(3, 0);
				st.setInt(4, 0);
				st.setInt(5, generateCvv()); // transaction_details
				st.setInt(6, accountNumber); // transaction_amount
				st.executeUpdate();
				// System.out.println("record has been inserted");
				st.close();
				con.close();
				creditCard = new CreditCard(CreditCardNumber,limit,0,0,CreditCardCVV);
				
			} catch (SQLException | ClassNotFoundException se) {
				se.printStackTrace();
			}
		}
		return creditCard;
	}

	// function to check if credit card exists on database
	public static boolean checkIfExists(CreditCard cc) {
		// retrieve credit card. if null, return false, else return true
		CreditCard temp = getCreditCard(cc.getCreditCardNumber());
		if (temp == null)
			return false;
		return true;
	}

	public static CreditCard getCreditCard(long creditCardNumber) {
		// database query to check if entry with credit card number already
		// exists
		CreditCard creditCard = null;
		try {
			Connection con = MyConnection.myConnection();
			Statement st = con.createStatement();
			String query = "Select * from Credit_Cards where credit_card_number ='" + creditCardNumber + "'";
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				// check in resultset if account lock
				creditCardNumber = rs.getLong(1);
				int creditCardLimit = rs.getInt(2);
				double creditCardSpending = rs.getDouble(3);
				int creditCardPoints = rs.getInt(4);
				int cvv = rs.getInt(5);
				// int accountNumber = rs.getInt(6);
				creditCard = new CreditCard(creditCardNumber, creditCardLimit, creditCardSpending, creditCardPoints,
						cvv);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return creditCard;
	}

	// function to set account as unlocked
	public static boolean setAccountUnlock(int accountNumber) {
		resetRequestUnlock(accountNumber);
		// update Customer lock = true
		try {
			Connection con = MyConnection.myConnection();
			String delete = "UPDATE Customers SET isLocked=? WHERE account_number =?";
			PreparedStatement ps = con.prepareStatement(delete);
			ps.setBoolean(1, false);
			ps.setInt(2, accountNumber);
			ps.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void resetRequestUnlock(int accountNumber) {
		// sql statement to update Customer table request_unlock to true
		try {
			Connection con = MyConnection.myConnection();
			String delete = "UPDATE Customers SET request_unlock=? WHERE account_number =?";

			PreparedStatement ps = con.prepareStatement(delete);

			ps.setBoolean(1, false);
			ps.setInt(2, accountNumber);
			ps.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// handle exception
	}

	public static List<Loan> getPendingLoans() {
		// database query to retrieve loans with status = 'Pending'
		ArrayList<Loan> loanList = null;
		try {
			Connection con = MyConnection.myConnection();
			Statement st = con.createStatement();
			String query = "SELECT customers.full_name, Loan_Applications.account_number, Loan_Applications.loan_id, Loan_Applications.loan_amount, Loan_Applications.interest_rate, Loan_Applications.loan_status, Loan_Applications.loan_application_timestamp FROM Customers JOIN Loan_Applications ON Customers.account_number = Loan_Applications.account_number WHERE Loan_Applications.loan_status = 'Pending';";
			ResultSet rs = st.executeQuery(query);
			if (rs != null)
				loanList = new ArrayList<Loan>();
			while (rs.next()) {
				// check in resultset if account lock
				String fullName = rs.getString(1);
				int accountNumber = rs.getInt(2);
				int loanId = rs.getInt(3);
				double loanAmount = rs.getDouble(4);
				double interestRate = rs.getDouble(5);
				String loanStatus = rs.getString(6);
				Timestamp loanApplicationTimestamp = rs.getTimestamp(7);
				loanList.add(new Loan(fullName, accountNumber, loanId, loanAmount, interestRate, loanStatus,
						loanApplicationTimestamp));
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loanList;
	}

	public static boolean updateLoan(int loanId, String loanStatus, String adminUsername) {
		// sql statement to update loan status
		try {
			Connection con = MyConnection.myConnection();
			String delete = "UPDATE Loan_Applications SET loan_status=?,admin_username=?,loan_reviewed_timestamp=? WHERE loan_id=?";

			PreparedStatement ps = con.prepareStatement(delete);

			ps.setString(1, loanStatus);
			ps.setString(2, adminUsername);
			ps.setTimestamp(3, new Timestamp(new java.util.Date().getTime()));
			ps.setInt(4, loanId);
			ps.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean instantMoney(int loanId) {
		// put instant money to customer account upon approval of loan
		try {
			Connection con = MyConnection.myConnection();
			String delete = "UPDATE Customers SET C.account_balance = C.account_balance+L.loan_amount FROM Customers AS C INNER JOIN Loan_Applications AS L ON C.account_number = L.account_number WHERE L.loan_id = ?";

			PreparedStatement ps = con.prepareStatement(delete);

			ps.setInt(1, loanId);
			ps.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static List<Customer> getLockedAccounts() {
		ArrayList<Customer> custList = null;
		try {
			Connection con = MyConnection.myConnection();
			Statement st = con.createStatement();
			String query = "Select * from Customers where isLocked=true;";
			ResultSet rs = st.executeQuery(query);
			if (rs != null) {
				custList = new ArrayList<Customer>();
			}
			while (rs.next()) {
				// store variables and create object
				int account_number = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				String full_name = rs.getString(4);
				String telephone_number = rs.getString(5);
				String address = rs.getString(6);
				double account_balance = rs.getDouble(7); // not sure if this
															// will get through
				// int login_count = rs.getInt(8);
				Timestamp last_login = rs.getTimestamp(9);
				boolean lock = rs.getBoolean(10);
				boolean request_unlock = rs.getBoolean(11);
				custList.add(new Customer(username, password, full_name, telephone_number, address, account_balance,
						lock, request_unlock, last_login, account_number));

			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return custList;
	}

	public static long generateCreditCardNumber() {
		creditCardNumber += Math.random() * 145905;
		DecimalFormat df = new DecimalFormat("0000000000000000");
		creditCardNumber = Long.parseLong(df.format(creditCardNumber));
		return creditCardNumber;
	}

	public static int generateCvv() {
		cvv += Math.random() * 913;
		DecimalFormat df = new DecimalFormat("000");
		cvv = Integer.parseInt(df.format(cvv));
		return cvv;
	}

}
