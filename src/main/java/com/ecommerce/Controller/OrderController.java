package com.ecommerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Exception.OrderException;
import com.ecommerce.Exception.UserExcepion;
import com.ecommerce.Model.Address;
import com.ecommerce.Model.Order;
import com.ecommerce.Model.User;
import com.ecommerce.Service.OrderService;
import com.ecommerce.Service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,
			@RequestHeader("Authorization")String jwt)throws UserExcepion{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		Order order=orderService.createOrder(user, shippingAddress);
		
		System.out.println("order"+order);
		
		return new ResponseEntity<Order>(order,HttpStatus.CREATED);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Order>> usersOrderHistory(@RequestHeader("Authorization")String jwt)throws UserExcepion{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		List<Order> orders=orderService.usersOrderHistory(user.getId());
		
		//System.out.println("order"+order);
		
		return new ResponseEntity<>(orders,HttpStatus.OK);
	}
	
	@GetMapping("/{Id}")
	public ResponseEntity<Order> findOrderById(@PathVariable("Id") Long orderId,
			@RequestHeader("Authorization") String jwt) throws UserExcepion,OrderException{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		Order order=orderService.findOrderById(orderId);
		
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
	
	
}
