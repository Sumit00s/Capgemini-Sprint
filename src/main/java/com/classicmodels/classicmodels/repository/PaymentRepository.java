package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entity.Payment;
import com.classicmodels.classicmodels.entity.PaymentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, PaymentId> {

    // Payments by customer number
    List<Payment> findByIdCustomerNumber(Integer customerNumber);
}