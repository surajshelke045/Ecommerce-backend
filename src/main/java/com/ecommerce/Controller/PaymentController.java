package com.ecommerce.Controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Exception.OrderException;
import com.ecommerce.Model.Order;
import com.ecommerce.Repository.OrderRepository;
import com.ecommerce.Response.ApiResponse;
import com.ecommerce.Response.PaymentLinkResponse;
import com.ecommerce.Service.OrderService;
import com.ecommerce.Service.UserService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/api")
public class PaymentController {
	
	String apiKey;
	String apiSecret;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
 private UserService userService;
	
	@Autowired
	OrderRepository orderRepository;
	
	@PostMapping("/payments/{orderId}")
	public ResponseEntity<PaymentLinkResponse>createPaymentLink(@PathVariable Long orderId,
	@RequestHeader("Authorization") String jwt)throws OrderException, RazorpayException{
		
		Order order=orderService.findOrderById(orderId); 
		
		try {
			
			RazorpayClient razorpay=new RazorpayClient(apiKey, apiSecret);
			JSONObject pamentLinkedRequest=new JSONObject();
			
			pamentLinkedRequest.put("ampunt", order.getTotalPrice()*100);
			pamentLinkedRequest.put("currency", "INR");
			
			JSONObject customer=new JSONObject();
			customer.put("name", order.getUser().getFirstName());
			customer.put("email", order.getUser().getEmail());
			pamentLinkedRequest.put("customer", customer);
			
			JSONObject notify=new JSONObject();
			notify.put("sms", true);
			notify.put("email", true);
			pamentLinkedRequest.put("notify", notify);
			
			pamentLinkedRequest.put("callback_url", "http://localhost:3000/payment/"+orderId);
			
			pamentLinkedRequest.put("callback_method", "get");
			
			PaymentLink payment=razorpay.paymentLink.create(pamentLinkedRequest);
			
			String paymentLinkId=payment.get("id");
			String paymentLinkUrl=payment.get("short_url");
			
			PaymentLinkResponse res =new PaymentLinkResponse();
			res.setPayment_Link_id(paymentLinkId);
			res.setPayment_link_url(paymentLinkUrl);
			
			return new ResponseEntity<PaymentLinkResponse>(res,HttpStatus.CREATED);
			
			
			
		} catch (Exception e) {
			throw new RazorpayException(e.getMessage());
		}
		
	}
	
	@GetMapping("/payments")
	public ResponseEntity<ApiResponse> redirect(@RequestParam(name = "payment_id") String paymrntId,
		@RequestParam(name = "order_id") Long orderId)throws OrderException, RazorpayException{
		Order order=orderService.findOrderById(orderId);
		RazorpayClient razorpay=new RazorpayClient(apiKey, apiSecret);
		try {
			
			Payment payment=razorpay.payments.fetch(paymrntId);
			
			if(payment.get("status").equals("captured")) {
				
				order.getPaymentDetails().setPaymentId(paymrntId);
				order.getPaymentDetails().setStatus("COMPLETED");
				order.setOrderStatus("PLACED");
				orderRepository.save(order);
			}
			
			ApiResponse res=new ApiResponse();
			res.setMessage("Your order get placed..");
			res.setStatus(true);
			return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
			
		} catch (Exception e) {
			throw new RazorpayException(e.getMessage());
		}
		
	}

}
