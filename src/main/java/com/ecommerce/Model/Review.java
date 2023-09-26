package com.ecommerce.Model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	private String review;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	
	public Review(Long id, User user, String review, Product product, LocalDateTime creatdAt) {
		super();
		this.id = id;
		this.user = user;
		this.review = review;
		this.product = product;
		this.creatdAt = creatdAt;
	}


	public Review() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getReview() {
		return review;
	}


	public void setReview(String review) {
		this.review = review;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}


	public LocalDateTime getCreatdAt() {
		return creatdAt;
	}


	public void setCreatdAt(LocalDateTime creatdAt) {
		this.creatdAt = creatdAt;
	}


	private LocalDateTime creatdAt; 
	
}
