package com.ecommerce.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name ="user_id",nullable = false)
	private User user;
	
	@OneToMany(mappedBy ="cart",cascade = CascadeType.ALL,orphanRemoval = true)
	@Column(name = "cart_items")
	private Set<CartItem> cartItems=new HashSet<>();
	
	@Column(name = "total_price")
	private double totalPrice;
	
	@Column(name = "total_item")
	private Integer totalItem;
	
	private Integer discount;;
	
	private Integer totalDiscountedPrice;
	




}
