package com.ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.Model.Order;
import com.ecommerce.Model.OrderItem;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
