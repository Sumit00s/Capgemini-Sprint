package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.entity.Customer;
import com.classicmodels.classicmodels.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // GET all customers
    // URL: GET http://localhost:8080/api/customers
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // GET one customer by ID
    // URL: GET http://localhost:8080/api/customers/103
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(
            @PathVariable Integer id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    // CREATE new customer
    // URL: POST http://localhost:8080/api/customers
    @PostMapping
    public ResponseEntity<Customer> createCustomer(
            @Valid @RequestBody Customer customer) {
        Customer created = customerService.createCustomer(customer);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // UPDATE customer
    // URL: PUT http://localhost:8080/api/customers/103
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Integer id,
            @Valid @RequestBody Customer customer) {
        Customer updated = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(updated);
    }

    // DELETE customer
    // URL: DELETE http://localhost:8080/api/customers/103
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCustomer(
            @PathVariable Integer id) {
        customerService.deleteCustomer(id);
        Map<String, String> response = new HashMap<>();
        response.put("message",
                "Customer deleted successfully with id: " + id);
        return ResponseEntity.ok(response);
    }

    // SEARCH by name
    // URL: GET http://localhost:8080/api/customers/search?name=mini
    @GetMapping("/search")
    public ResponseEntity<List<Customer>> searchByName(
            @RequestParam String name) {
        List<Customer> customers = customerService.searchByName(name);
        return ResponseEntity.ok(customers);
    }

    // FILTER by country
    // URL: GET http://localhost:8080/api/customers/country?country=USA
    @GetMapping("/country")
    public ResponseEntity<List<Customer>> getByCountry(
            @RequestParam String country) {
        List<Customer> customers = customerService.getByCountry(country);
        return ResponseEntity.ok(customers);
    }

    // FILTER by city
    // URL: GET http://localhost:8080/api/customers/city?city=NYC
    @GetMapping("/city")
    public ResponseEntity<List<Customer>> getByCity(
            @RequestParam String city) {
        List<Customer> customers = customerService.getByCity(city);
        return ResponseEntity.ok(customers);
    }
}