package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    // Search by name (case insensitive)
    List<Customer> findByCustomerNameContainingIgnoreCase(String customerName);

    // Filter by country
    List<Customer> findByCountry(String country);

    // Filter by city
    List<Customer> findByCity(String city);

    // Find by sales rep employee number
    List<Customer> findBySalesRepEmployeeNumber(Integer salesRepEmployeeNumber);
}