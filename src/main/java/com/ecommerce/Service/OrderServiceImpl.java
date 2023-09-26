package com.ecommerce.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.Exception.OrderException;
import com.ecommerce.Model.Address;
import com.ecommerce.Model.Cart;
import com.ecommerce.Model.CartItem;
import com.ecommerce.Model.Order;
import com.ecommerce.Model.OrderItem;
import com.ecommerce.Model.User;
import com.ecommerce.Repository.AddressRepository;
import com.ecommerce.Repository.CartRepository;
import com.ecommerce.Repository.OrderItemRepository;
import com.ecommerce.Repository.OrderRepository;
import com.ecommerce.Repository.ProductRepository;
import com.ecommerce.Repository.UserRepository;

@Service
public class OrderServiceImpl implements OrderService {

@Autowired
private OrderRepository orderRepository;

@Autowired
private UserRepository userRepository;

@Autowired
private AddressRepository addressRepository;

@Autowired
private CartRepository cartRepository;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private ProductService productService;
	

	@Override
	public Order createOrder(User user, Address shippingAddress) {
		
		shippingAddress.setUser(user);
		Address address=addressRepository.save(shippingAddress);
		user.getAddress().add(address);
		userRepository.save(user);
		
		Cart cart=cartService.findUserCart(user.getId());
		List<OrderItem> orderItems=new ArrayList<>();
		
		for(CartItem item:cart.getCartItems())
		{
			OrderItem orderItem=new OrderItem();
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());
			
			OrderItem createdOrderItem=orderItemRepository.save(orderItem);
			orderItems.add(createdOrderItem);
		}
	
		Order createdOrder=new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setDeliveryDate(LocalDateTime.now());
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		createdOrder.setDiscount(cart.getDiscount());
		createdOrder.setTotalItem(cart.getTotalItem());
		createdOrder.setShippingAddress(address);
		createdOrder.setOrderStatus("PENDING");
		createdOrder.getPaymentDetails().setStatus("PENDING");
		
		createdOrder.setCreatedAt(LocalDateTime.now());
		Order savedOrder=orderRepository.save(createdOrder);
		
		for(OrderItem item:orderItems)
		{
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}
		
		return savedOrder;
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
	Optional<Order> opt = orderRepository.findById(orderId);
	
	if(opt.isPresent()) {
		return opt.get();
	}
	throw new OrderException("Order not Exist with id : "+orderId);
	}

	@Override
	public List<Order> usersOrderHistory(Long userId) {
		List<Order> orders=orderRepository.getUsersOrders(userId);
		return orders;
	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");
		return order;
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");
		return orderRepository.save(order);
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		return orderRepository.save(order);
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("DELIVERED");
		return orderRepository.save(order);
	}

	@Override
	public Order cancledOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("CANCELLED");
		return orderRepository.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
		
		return orderRepository.findAll();
	}

	@Override
	public Order deleteOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
	    orderRepository.deleteById(orderId);
		
		return order;
	}

}
