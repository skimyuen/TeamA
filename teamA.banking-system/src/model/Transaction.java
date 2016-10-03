package model;

import java.sql.Timestamp;

public class Transaction {
	//private String customer_name; // or store username
	private int transactionId;
	private String transactionDetails;
	private double transactionAmount;
	private boolean transactionType;
	private Timestamp transactionTimestamp;
	
	public Transaction(int transactionId, String transactionDetails, double transactionAmount, boolean transactionType,
			Timestamp transactionTimestamp) {
		super();
		this.transactionId = transactionId;
		this.transactionDetails = transactionDetails;
		this.transactionAmount = transactionAmount;
		this.transactionType = transactionType;
		this.transactionTimestamp = transactionTimestamp;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransactionDetails() {
		return transactionDetails;
	}
	public void setTransactionDetails(String transactionDetails) {
		this.transactionDetails = transactionDetails;
	}
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public boolean isTransactionType() {
		return transactionType;
	}
	public void setTransactionType(boolean transactionType) {
		this.transactionType = transactionType;
	}
	public Timestamp getTransactionTimestamp() {
		return transactionTimestamp;
	}
	public void setTransactionTimestamp(Timestamp transactionTimestamp) {
		this.transactionTimestamp = transactionTimestamp;
	}
	
	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", transactionDetails=" + transactionDetails
				+ ", transactionAmount=" + transactionAmount + ", transactionType=" + transactionType
				+ ", transactionTimestamp=" + transactionTimestamp + "]";
	}	
	
}
