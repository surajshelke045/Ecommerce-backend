package com.ecommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Exception.ProductException;
import com.ecommerce.Exception.UserExcepion;
import com.ecommerce.Model.Cart;
import com.ecommerce.Model.User;
import com.ecommerce.Request.AddItemRequest;
import com.ecommerce.Response.ApiResponse;
import com.ecommerce.Service.CartService;
import com.ecommerce.Service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartConroller {
	
	@Autowired
 private	CartService cartService; 
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public ResponseEntity<Cart>findUserCart(@RequestHeader("Authorization") String jwt) throws UserExcepion{
		User user=userService.findUserProfileByJwt(jwt);
		Cart cart=cartService.findUserCart(user.getId());
		
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@PutMapping("/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,
			@RequestHeader("Authorization")String jwt)throws UserExcepion,ProductException{
		User user=userService.findUserProfileByJwt(jwt);
		cartService.addCartItem(user.getId(), req);
		
		ApiResponse res=new ApiResponse();
		res.setMessage("Item Added to Cart");
		res.setStatus(true);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

}
