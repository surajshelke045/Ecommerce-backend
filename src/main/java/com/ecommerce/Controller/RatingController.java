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
import com.ecommerce.Model.User;
import com.ecommerce.Request.AddItemRequest;
import com.ecommerce.Request.RatingRequest;
import com.ecommerce.Response.ApiResponse;
import com.ecommerce.Service.RatingService;
import com.ecommerce.Service.UserService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RatingService ratingService;
	
	@PostMapping("/create")
	public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req,
			@RequestHeader("Authorization") String jwt) throws UserExcepion,ProductException{
		User user=userService.findUserProfileByJwt(jwt);
	Rating rating=	 ratingService.createRating(req, user); 
	
	return new ResponseEntity<Rating>(rating,HttpStatus.CREATED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Rating>> getProductsRating(@PathVariable Long productId,
			@RequestHeader("Authorization") String jwt) throws UserExcepion,ProductException{
		User user=userService.findUserProfileByJwt(jwt);
	List<Rating> ratings=	 ratingService.getProductsRating(productId);
	
	return new ResponseEntity<>(ratings,HttpStatus.OK);
	}

}
