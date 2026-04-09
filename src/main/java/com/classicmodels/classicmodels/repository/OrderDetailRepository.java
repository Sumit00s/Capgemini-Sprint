package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entity.OrderDetail;
import com.classicmodels.classicmodels.entity.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {

    // All order details for a given order number
    List<OrderDetail> findByIdOrderNumber(Integer orderNumber);
}