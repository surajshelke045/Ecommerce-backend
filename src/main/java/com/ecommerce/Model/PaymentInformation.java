package com.ecommerce.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

public class PaymentInformation {
	
	@Column(name="cardholder_Name")
	private String cardholderName;
	
	@Column(name="card_number")
	private String cardNumber;
	
	@Column(name="expiration_date")
	private String expirationDate;
	
	@Column(name="cvv")
	private String cvv;

	public PaymentInformation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCardholderName() {
		return cardholderName;
	}

	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

}
