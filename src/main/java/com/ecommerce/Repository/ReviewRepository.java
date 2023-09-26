package com.ecommerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.Model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
	
	@Query("SELECT r From Review r Where r.product.id=:productId")
	public List<Review> getAllProductsReview(@Param("productId") Long productId);

}
