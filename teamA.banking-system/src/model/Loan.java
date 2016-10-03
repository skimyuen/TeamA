package model;

import java.sql.Timestamp;

public class Loan {
	private String fullName;
	private int accountNumber;
	private int loanId;
	private double loanAmount;
	private double interestRate;
	private String loanStatus;
	private String adminUsername;
	private Timestamp loanApplicationTimestamp;
	private Timestamp loanReviewTimestamp;
	public Loan(String fullName, int accountNumber, int loanId, double loanAmount, double interestRate) {
		super();
		this.fullName = fullName;
		this.accountNumber = accountNumber;
		this.loanId = loanId;
		this.loanAmount = loanAmount;
		this.interestRate = interestRate;
		this.loanStatus = "Pending";
		this.adminUsername = null;
		this.loanApplicationTimestamp = null;
		this.loanReviewTimestamp = null;
	}
	public Loan(String fullName, int accountNumber, int loanId, double loanAmount, double interestRate, String loanStatus, Timestamp loanApplicationTimestamp) {
		super();
		this.fullName = fullName;
		this.accountNumber = accountNumber;
		this.loanId = loanId;
		this.loanAmount = loanAmount;
		this.interestRate = interestRate;
		this.loanStatus = loanStatus;
		this.adminUsername = null;
		this.loanApplicationTimestamp = loanApplicationTimestamp;
		this.loanReviewTimestamp = null;
	}
	public Loan(String fullName, int accountNumber, int loanId, double loanAmount, double interestRate,
			String loanStatus, String adminUsername, Timestamp loanApplicationTimestamp,
			Timestamp loanReviewTimestamp) {
		super();
		this.fullName = fullName;
		this.accountNumber = accountNumber;
		this.loanId = loanId;
		this.loanAmount = loanAmount;
		this.interestRate = interestRate;
		this.loanStatus = loanStatus;
		this.adminUsername = adminUsername;
		this.loanApplicationTimestamp = loanApplicationTimestamp;
		this.loanReviewTimestamp = loanReviewTimestamp;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public int getLoanId() {
		return loanId;
	}
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	public double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getAdminUsername() {
		return adminUsername;
	}
	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}
	public Timestamp getLoanApplicationTimestamp() {
		return loanApplicationTimestamp;
	}
	public void setLoanApplicationTimestamp(Timestamp loanApplicationTimestamp) {
		this.loanApplicationTimestamp = loanApplicationTimestamp;
	}
	public Timestamp getLoanReviewTimestamp() {
		return loanReviewTimestamp;
	}
	public void setLoanReviewTimestamp(Timestamp loanReviewTimestamp) {
		this.loanReviewTimestamp = loanReviewTimestamp;
	}
	@Override
	public String toString() {
		return "Loan [fullName=" + fullName + ", accountNumber=" + accountNumber + ", loanId=" + loanId
				+ ", loanAmount=" + loanAmount + ", interestRate=" + interestRate + ", loanStatus=" + loanStatus
				+ ", adminUsername=" + adminUsername + ", loanApplicationTimestamp=" + loanApplicationTimestamp
				+ ", loanReviewTimestamp=" + loanReviewTimestamp + "]";
	}
	
}
