package com.ecommerce.Service;

import java.util.List;

import com.ecommerce.Exception.ProductException;
import com.ecommerce.Model.Review;
import com.ecommerce.Model.User;
import com.ecommerce.Request.ReviewRequest;

public interface ReviewService {
	
	public Review createReview(ReviewRequest req,User user)throws ProductException;
	
	public List<Review> getAllReview(Long productId); 

	
}
