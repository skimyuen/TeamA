package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import dbConnection.MyConnection;
import model.Customer;

public class UserDao {
	private Customer customer = null;
	static Connection con; 
	// to authenticate login - connect to database to check for credentials
	
	public int login(String username, String password){
		// database query	
		int login_count = -1;
		try{
			con = MyConnection.myConnection();
			Statement st = con.createStatement();
			String query = "Select * from Admins where admin_username='" + username + "' and password='" + password + "'";
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				// assign each data value
				return 11; // admin login
			}
			
			query = "Select * from Customers where username='" + username +"'";
			rs = st.executeQuery(query);
	
			while (rs.next()) {
				// check in resultset if account lock
				int account_number = rs.getInt(1);
				username = rs.getString(2);
				String dbPassword = rs.getString(3);
				String full_name = rs.getString(4);
				String telephone_number = rs.getString(5);
				String address = rs.getString(6);
				double account_balance = rs.getDouble(7); // not sure if this will get through
				login_count = rs.getInt(8);
				Timestamp last_login = rs.getTimestamp(9);
				boolean lock = rs.getBoolean(10);
				boolean request_unlock = rs.getBoolean(11);
				
				if(lock==true) // account locked though login successful
					return 0;
				
				if(password.equals(dbPassword)){ // password correct
					// create & set customer object 
					customer = new Customer(username, password, full_name, telephone_number, address, account_balance, lock, request_unlock, last_login, account_number);
					// set last_login & reset counter on database
					resetCounter(username);
				
					return 10; 
				}
				if(login_count>0){
					login_count--;
				}
				if(login_count==0){
					accountLock(username);
				} 
			}
			 con.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		// did not login 

		return login_count; // generic invalid login
	}
	
	//
	public int checkRepeatLogin(String username){
		try {
			con = MyConnection.myConnection();
			Statement st = con.createStatement();
			String query = "Select login_count from Customers where username = '" + username + "'";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()){
				int count = rs.getInt(1);
				count--;
				return count;
				// and insert into login_counter in Customer // call updateCounter
			}
			 con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// if username does not exist
		return -1;
	}

	public void accountLock(String username){
		// reset counter = 3 // call resetCounter
		resetCounter(username);
		// update Customer lock = true
		try{
	  	  	con = MyConnection.myConnection();
	  	  	String delete = "UPDATE Customers SET isLocked=? WHERE username =?";
	        PreparedStatement ps = con.prepareStatement(delete);
	        ps.setBoolean(1, true);
	        ps.setString(2, username);
	        ps.executeUpdate();
	        con.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void resetCounter(String username){
		// count = 3; 
		updateCounter(username,3);
	}
	
	public void updateCounter(String username, int count){
		//-> sql statement here	
		try{
	  	  	con = MyConnection.myConnection();
	  	  	String delete = "UPDATE Customers SET login_count=? WHERE username =?";
	
	        PreparedStatement ps = con.prepareStatement(delete);
	        
	        ps.setInt(1, count);
	        ps.setString(2, username);
	        ps.executeUpdate();
	        con.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void requestUnlock(String username){
		// sql statement to update Customer table request_unlock to true
		try{
	  	  	con = MyConnection.myConnection();
	  	  	String delete = "UPDATE Customers SET request_unlock=? WHERE username =?";
	
	        PreparedStatement ps = con.prepareStatement(delete);
	        
	        ps.setBoolean(1, true);
	        ps.setString(2, username);
	        ps.executeUpdate();
	        con.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		// handle exception
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer cust) {
		customer = cust;
	}
	
	
}
