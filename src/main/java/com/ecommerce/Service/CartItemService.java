package com.ecommerce.Service;

import com.ecommerce.Exception.CartItemException;
import com.ecommerce.Exception.UserExcepion;
import com.ecommerce.Model.Cart;
import com.ecommerce.Model.CartItem;
import com.ecommerce.Model.Product;

public interface CartItemService {
	
	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId,Long id,CartItem cartItem)throws CartItemException,UserExcepion;
	
	public CartItem isCartItemExist(Cart cart,Product product,String size,Long userId);
	
	public CartItem removeCartItem(Long userId,Long cartItemId)throws CartItemException,UserExcepion;
	
	public CartItem findCartItemById(Long cartItemId)throws CartItemException;

}
