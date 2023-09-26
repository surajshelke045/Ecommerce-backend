package com.ecommerce.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.Exception.ProductException;
import com.ecommerce.Model.Product;
import com.ecommerce.Model.Review;
import com.ecommerce.Model.User;
import com.ecommerce.Repository.ReviewRepository;
import com.ecommerce.Request.ReviewRequest;

@Service
public class ReviewServiceImpl implements ReviewService{
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ReviewRepository reviewRepository;

	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {

		Product product=productService.findProductById(req.getProductId());
		
		Review review=new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReview(req.getReview());
		review.setCreatdAt(LocalDateTime.now());
		
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getAllReview(Long productId) {
		
		return reviewRepository.getAllProductsReview(productId);
	}

}
