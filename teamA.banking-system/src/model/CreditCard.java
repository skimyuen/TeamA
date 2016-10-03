package model;

public class CreditCard {
	private long creditCardNumber;
	private int creditCardLimit;
	private double creditCardSpending;
	private int creditCardPoints;
	private int creditCardCvv;
	public CreditCard(long creditCardNumber, int creditCardLimit, int creditCardCvv) {
		super();
		this.creditCardNumber = creditCardNumber;
		this.creditCardLimit = creditCardLimit;
		this.creditCardSpending = 0;
		this.creditCardPoints = 0;
		this.creditCardCvv = creditCardCvv;
	}
	public CreditCard(long creditCardNumber, int creditCardLimit, double creditCardSpending, int creditCardPoints,
			int creditCardCvv) {
		super();
		this.creditCardNumber = creditCardNumber;
		this.creditCardLimit = creditCardLimit;
		this.creditCardSpending = creditCardSpending;
		this.creditCardPoints = creditCardPoints;
		this.creditCardCvv = creditCardCvv;
	}
	public long getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(long creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public int getCreditCardLimit() {
		return creditCardLimit;
	}
	public void setCreditCardLimit(int creditCardLimit) {
		this.creditCardLimit = creditCardLimit;
	}
	public double getCreditCardSpending() {
		return creditCardSpending;
	}
	public void setCreditCardSpending(double creditCardSpending) {
		this.creditCardSpending = creditCardSpending;
	}
	public int getCreditCardPoints() {
		return creditCardPoints;
	}
	public void setCreditCardPoints(int creditCardPoints) {
		this.creditCardPoints = creditCardPoints;
	}
	public int getCreditCardCvv() {
		return creditCardCvv;
	}
	public void setCreditCardCvv(int creditCardCvv) {
		this.creditCardCvv = creditCardCvv;
	}
	@Override
	public String toString() {
		return "CreditCard [creditCardNumber=" + creditCardNumber + ", creditCardLimit=" + creditCardLimit
				+ ", creditCardSpending=" + creditCardSpending + ", creditCardPoints=" + creditCardPoints
				+ ", creditCardCvv=" + creditCardCvv + "]";
	}
	
}
