package com.ecommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Exception.CartItemException;
import com.ecommerce.Exception.UserExcepion;
import com.ecommerce.Model.CartItem;
import com.ecommerce.Model.User;
import com.ecommerce.Response.ApiResponse;
import com.ecommerce.Service.CartItemService;
import com.ecommerce.Service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private UserService userService;
	
	@DeleteMapping("/{cartItemId}")
	public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId,
			@RequestHeader("Authorization") String jwt) throws UserExcepion,CartItemException{
		
		User user=userService.findUserProfileByJwt(jwt);
		cartItemService.removeCartItem(user.getId(), cartItemId);
		
		ApiResponse res=new ApiResponse();
		res.setMessage("Item Deleted Successfully");
		res.setStatus(true);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@PutMapping("/{cartItemId}")
	public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem cartItem,@PathVariable Long cartItemId,
			@RequestHeader("Authorization") String jwt) throws UserExcepion,CartItemException{
		
		User user=userService.findUserProfileByJwt(jwt);
	  CartItem updateCartItem=cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
		
		return new ResponseEntity<>(updateCartItem,HttpStatus.OK);
	}

}
