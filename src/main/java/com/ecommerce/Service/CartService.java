package com.ecommerce.Service;

import com.ecommerce.Exception.ProductException;
import com.ecommerce.Model.Cart;
import com.ecommerce.Model.User;
import com.ecommerce.Request.AddItemRequest;

public interface CartService {
	
	public Cart createCart(User user);
	
	public String addCartItem(Long userId,AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(Long userId);
	

}
