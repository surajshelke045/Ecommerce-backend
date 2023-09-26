package com.ecommerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Exception.ProductException;
import com.ecommerce.Exception.UserExcepion;
import com.ecommerce.Model.Rating;
import com.ecommerce.Model.Review;
import com.ecommerce.Model.User;
import com.ecommerce.Request.AddItemRequest;
import com.ecommerce.Request.RatingRequest;
import com.ecommerce.Request.ReviewRequest;
import com.ecommerce.Response.ApiResponse;
import com.ecommerce.Service.RatingService;
import com.ecommerce.Service.ReviewService;
import com.ecommerce.Service.UserService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReviewService reviewService;
	
	@PostMapping("/create")
	public ResponseEntity<Review> createReview(@RequestBody ReviewRequest req,
			@RequestHeader("Authorization") String jwt) throws UserExcepion,ProductException{
		User user=userService.findUserProfileByJwt(jwt);
	Review review=reviewService.createReview(req, user);
	
	return new ResponseEntity<>(review,HttpStatus.CREATED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>> getProductsRating(@PathVariable Long productId,
			@RequestHeader("Authorization") String jwt) throws UserExcepion,ProductException{
		
	List<Review> reviews=	reviewService.getAllReview(productId);
	
	return new ResponseEntity<>(reviews,HttpStatus.ACCEPTED);
	}

}