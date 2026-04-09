package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    // Filter by status
    List<Order> findByStatus(String status);

    // Orders by customer
    List<Order> findByCustomerNumber(Integer customerNumber);
}