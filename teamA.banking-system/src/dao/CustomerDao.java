package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dbConnection.MyConnection;
import model.CreditCard;
import model.Customer;
import model.Loan;
import model.Transaction;

public class CustomerDao{
	
	// method to change password
	public static boolean changePassword(int accountNumber, String newPassword){
		try{
	  	  	Connection con = MyConnection.myConnection();
	  	  	String delete = "UPDATE Customers SET password=? WHERE account_number=?";
	
	        PreparedStatement ps = con.prepareStatement(delete);
	        
	        ps.setString(1, newPassword);
	        ps.setInt(2, accountNumber);
	        ps.executeUpdate();
	        con.close();
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		 return true;
	}

	public static boolean payCreditCard(double amount, long creditCardNumber, int accountNumber, double accountBalance) {
		CreditCard creditCard = CustomerDao.getCreditCard(creditCardNumber);
		// check credit card spending with amount trying to pay
		if (creditCard.getCreditCardSpending() < amount){
			return false;
		}
		// reduces credit card spending
		reduceCreditCardSpending(amount, creditCardNumber);
		// reduce customer account balance
		if(amount<=accountBalance){
			// reduce customer balance
			reduceCustomerBalance(amount, accountNumber);
			// save in transaction with boolean type, transaction details, and accountNumber
			addTransaction(amount, false , "Transfer Money To Credit Card", accountNumber);
			return true;
		}
		return false;
	}
	
	public static int topUpMobile(int accountNumber, double amount, long creditCardNumber){
		// addCreditCardSpending
		boolean added = addCreditCardSpending(amount, creditCardNumber);
		// add transaction
		if(added){
			addTransaction(amount, false , "Top Up Mobile from Credit Card", accountNumber);
			return 2;
		}
		return 0;
	}
	
	public static int topUpMobile(int accountNumber, double amount, double accountBalance){
		if(amount<accountBalance){
			// reduce customer balance
			reduceCustomerBalance(amount, accountNumber);
			// add transaction
			addTransaction(amount, false , "Top Up Mobile from Savings Account", accountNumber);
			return 1;
		}
		return 0;
	}
	
	public static void reduceCreditCardSpending(double amount, long creditCardNumber){
		// remove from credit card using sql statement
		try{
	  	  	Connection con = MyConnection.myConnection();
	  	  	String delete = "UPDATE credit_cards SET credit_card_spending=credit_card_spending-? WHERE credit_card_number =?";
	
	        PreparedStatement ps = con.prepareStatement(delete);
	        
	        ps.setDouble(1, amount);
	        ps.setLong(2, creditCardNumber);
	        ps.executeUpdate();
	        con.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void reduceCustomerBalance(double amount, int accountNumber){
		// reduce customer balance using sql statement
		try{
	  	  	Connection con = MyConnection.myConnection();
	  	  	String delete = "UPDATE customers SET account_balance = account_balance - ? WHERE account_number = ?";
	
	        PreparedStatement ps = con.prepareStatement(delete);
	        
	        ps.setDouble(1, amount);
	        ps.setInt(2, accountNumber);
	        ps.executeUpdate();
	        con.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void addTransaction(double amount, boolean type, String transactionDetails, int accountNumber){
		// insert into transaction table using sql statement
	
		String query = "INSERT INTO Transactions (transaction_details,transaction_amount,transaction_type,account_number) VALUES(?,?,?,?)";
		try {
			Connection con = MyConnection.myConnection();
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, transactionDetails); // transaction_details
			st.setDouble(2, amount); // transaction_amount
			st.setBoolean(3, type);
			st.setInt(4, accountNumber);
			st.executeUpdate();
			// System.out.println("record has been inserted");
			st.close();
			con.close();
		} catch (SQLException | ClassNotFoundException se) {
			se.printStackTrace();
		}
	}
	public static CreditCard getCreditCard(long creditCardNumber){
		CreditCard creditCard = null;
		try{
			Connection con = MyConnection.myConnection();
			Statement st = con.createStatement();
			String query = "Select * from Credit_Cards WHERE credit_card_number='" + creditCardNumber + "';";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
					// check in resultset if account lock
				long ccNum = rs.getLong(1);
				int limit = rs.getInt(2);
				double spending = rs.getDouble(3);
				int points = rs.getInt(4);
				int cvv = rs.getInt(5);
				//accountNumber = rs.getInt(6);
				creditCard = new CreditCard(ccNum, limit, spending, points, cvv);
			}
			 con.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return creditCard;
	}
	
	public static List<CreditCard> getCreditCards(int accountNumber){
		ArrayList<CreditCard> ccList = null;
		try{
			Connection con = MyConnection.myConnection();
			Statement st = con.createStatement();
			String query = "Select * from Credit_Cards WHERE account_number='" + accountNumber + "';";
			ResultSet rs = st.executeQuery(query);
			if(rs!=null){
				ccList = new ArrayList<CreditCard>();
			}
			while (rs.next()) {
					// check in resultset if account lock
				long ccNum = rs.getLong(1);
				int limit = rs.getInt(2);
				double spending = rs.getDouble(3);
				int points = rs.getInt(4);
				int cvv = rs.getInt(5);
				//accountNumber = rs.getInt(6);
				ccList.add(new CreditCard(ccNum, limit, spending, points, cvv));
			}
			 con.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return ccList;
	}
	
	public static boolean addCreditCardSpending(double amount, long creditCardNumber){
		// calculate credit card points 
		int points = (int) amount/10;
		if(ableToAddCreditSpending(amount,creditCardNumber)){
			// insert into credit card add amount and add credit card points to credit card
			try{
		  	  	Connection con = MyConnection.myConnection();
		  	  	String delete = "UPDATE credit_cards SET credit_card_spending = credit_card_spending + ? , credit_card_points = credit_card_points + ? WHERE credit_card_number = ?";
		
		        PreparedStatement ps = con.prepareStatement(delete);
		        
		        ps.setDouble(1, amount);
		        ps.setInt(2, points);
		        ps.setLong(3, creditCardNumber);
		        
		        ps.executeUpdate();
		        con.close();
		        
			} catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public static boolean ableToAddCreditSpending(double amount, long creditCardNumber){
		// check db if (amount + spending) < limit
		try{
			Connection con = MyConnection.myConnection();
			Statement st = con.createStatement();
			String query = "Select credit_card_spending, credit_card_limit from credit_cards where credit_card_number = '" + creditCardNumber + "'";
			ResultSet rs = st.executeQuery(query);
	
			while (rs.next()) {
				// check in 
				double spending = rs.getDouble(1);
				int limit = rs.getInt(2);
				if( spending + amount < limit){
					return true;
				}
			}
			 con.close();
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public static List<Transaction> getTransactions(int accountNumber){
		ArrayList<Transaction> transactionList = null;
		// database query to retrieve list of transactions
		try{
			Connection con = MyConnection.myConnection();
			Statement st = con.createStatement();
			String query = "Select * from Transactions WHERE account_number='" + accountNumber + "'";
			ResultSet rs = st.executeQuery(query);
			if(rs!=null){
				transactionList = new ArrayList<Transaction>();
			}
			while (rs.next()) {
				int transactionId = rs.getInt(1);
				String transactionDetails = rs.getString(2);
				double transactionAmount = rs.getDouble(3);
				boolean transactionType = rs.getBoolean(4);
				Timestamp tranactionTimestamp = rs.getTimestamp(5);
				accountNumber = rs.getInt(6);
				transactionList.add(new Transaction(transactionId, transactionDetails, transactionAmount, transactionType, tranactionTimestamp));
			}
			 con.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return transactionList;
	}
	
	public static boolean createLoan(Loan loan){
		try {
			Connection con = dbConnection.MyConnection.myConnection();
			String query = "INSERT INTO Loan_Applications (loan_amount,interest_rate,loan_application_timestamp,account_number) VALUES(?,?,CURRENT_TIMESTAMP,?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setDouble(1, loan.getLoanAmount());
			st.setDouble(2, loan.getInterestRate());
			st.setInt(3, loan.getAccountNumber());
			st.executeUpdate();
			// System.out.println("record has been inserted");
			st.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} 
		return true;
	}
	
	public static void setLastLogin(Customer c){
		try {
			Connection con = dbConnection.MyConnection.myConnection();
			String query = "Update Customers SET last_login=CURRENT_TIMESTAMP WHERE account_number=?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, c.getAccountNumber());
			st.executeUpdate();
			// System.out.println("record has been inserted");
			st.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
