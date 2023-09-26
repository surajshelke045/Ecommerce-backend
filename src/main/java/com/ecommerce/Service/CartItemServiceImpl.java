package com.ecommerce.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.Exception.CartItemException;
import com.ecommerce.Exception.UserExcepion;
import com.ecommerce.Model.Cart;
import com.ecommerce.Model.CartItem;
import com.ecommerce.Model.Product;
import com.ecommerce.Model.User;
import com.ecommerce.Repository.CartItemRepository;
import com.ecommerce.Repository.CartRepository;

@Service
public class CartItemServiceImpl implements CartItemService{
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartRepository cartRepository;

	@Override
	public CartItem createCartItem(CartItem cartItem) {
		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
		
		CartItem createdCartItem=cartItemRepository.save(cartItem);
		return createdCartItem;
	}

	@Override
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserExcepion {
		CartItem item=findCartItemById(id);
		User user=userService.findUserById(item.getUserId());
		
		if(user.getId().equals(userId))
		{
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity()*item.getProduct().getPrice());
			item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());
		}
		
		return cartItemRepository.save(item);
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
		CartItem cartItem=cartItemRepository.isCartItemExist(cart, product, size, userId);
		
		return cartItem;
	}

	@Override
	public CartItem removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserExcepion {
	CartItem cartItem=findCartItemById(cartItemId);
	User user=userService.findUserById(cartItem.getUserId());
	
	User reqUser=userService.findUserById(userId);
	
	if(user.getId().equals(reqUser.getId())){
		cartItemRepository.deleteById(cartItemId);
		return cartItem;
	}
	else {
		throw new UserExcepion("You can't remove this Item");
	}

	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {
		Optional<CartItem> opt=cartItemRepository.findById(cartItemId);
		
		if(opt.isPresent())
		{
			return opt.get();
		}
		throw new CartItemException("CardItem not found with id : "+cartItemId);
	}

}
