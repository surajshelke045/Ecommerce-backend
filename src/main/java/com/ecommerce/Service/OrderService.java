package com.ecommerce.Service;

import java.util.List;

import com.ecommerce.Exception.OrderException;
import com.ecommerce.Model.Address;
import com.ecommerce.Model.Order;
import com.ecommerce.Model.User;

public interface OrderService {
	
	public Order createOrder(User user, Address shippingAddress);
	
	public Order findOrderById(Long orderId) throws OrderException;
	
	public List<Order> usersOrderHistory(Long userId);
	
	public Order placedOrder(Long orderId)throws OrderException;
	
	public Order confirmedOrder(Long orderId)throws OrderException;
	
	public Order shippedOrder(Long orderId)throws OrderException;
	
	public Order deliveredOrder(Long orderId)throws OrderException;
	
	public Order cancledOrder(Long orderId)throws OrderException;
	
	public List<Order> getAllOrders();
	
	public Order deleteOrder(Long orderId)throws OrderException;
	


}
