package model;

import java.sql.Timestamp;

public class Customer {
	private String username;
	private String password;
	private String fullName;
	private String telephoneNumber; 
	private String address;
	private double accountBalance;
	private boolean lock;
	private boolean requestUnlock;
	private Timestamp lastLogin;
	private int accountNumber;
	
	public Customer(String username, String fullName, String telephoneNumber, String address,
			double accountBalance) {
		super();
		this.username = username;
		this.password = "";
		this.fullName = fullName;
		this.telephoneNumber = telephoneNumber;
		this.address = address;
		this.accountBalance = accountBalance;
		this.lock = false;
		this.requestUnlock = false;
		this.lastLogin = null;
		this.accountNumber = 0;
	}
	
	public Customer(String username, String password, String fullName, String telephoneNumber, String address,
			double accountBalance, boolean lock, boolean requestUnlock, Timestamp lastLogin, int accountNumber) {
		super();
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.telephoneNumber = telephoneNumber;
		this.address = address;
		this.accountBalance = accountBalance;
		this.lock = lock;
		this.requestUnlock = requestUnlock;
		this.lastLogin = lastLogin;
		this.accountNumber = accountNumber;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public boolean isLock() {
		return lock;
	}
	public void setLock(boolean lock) {
		this.lock = lock;
	}
	public boolean isRequestUnlock() {
		return requestUnlock;
	}
	public void setRequestUnlock(boolean requestUnlock) {
		this.requestUnlock = requestUnlock;
	}
	public Timestamp getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Override
	public String toString() {
		return "Customer [username=" + username + ", password=" + password + ", fullName=" + fullName
				+ ", telephoneNumber=" + telephoneNumber + ", address=" + address + ", accountBalance=" + accountBalance
				+ ", lock=" + lock + ", requestUnlock=" + requestUnlock + ", lastLogin=" + lastLogin
				+ ", accountNumber=" + accountNumber + "]";
	}
	
}
