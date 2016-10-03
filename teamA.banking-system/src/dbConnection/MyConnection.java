package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
	
	public static Connection myConnection() throws ClassNotFoundException, SQLException{
		
		// database url
		String connectionURL = "jdbc:mysql://localhost:3306/Bank";
		
		// database credentials
		String uname = "root";
		String pwd = "optsql@2016";
		
		try {
			// register JDBC driver
			Class.forName("com.mysql.jdbc.Driver"); //creates instance of driver class
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// open a connection
		return DriverManager.getConnection(connectionURL, uname, pwd);
	}

}
