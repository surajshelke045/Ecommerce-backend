package com.ecommerce.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.Exception.ProductException;
import com.ecommerce.Model.Rating;
import com.ecommerce.Model.User;
import com.ecommerce.Request.RatingRequest;

@Service 
public interface RatingService {
	
	public Rating createRating(RatingRequest req,User user)throws ProductException;
	
	public List<Rating> getProductsRating(Long productId);

}
