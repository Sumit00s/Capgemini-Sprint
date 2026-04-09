package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entity.Customer;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    // GET all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // GET one customer by ID
    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with id: " + id));
    }

    // CREATE new customer
    public Customer createCustomer(Customer customer) {
        // Check if customerNumber already exists
        if (customerRepository.existsById(customer.getCustomerNumber())) {
            throw new RuntimeException(
                    "Customer already exists with number: "
                            + customer.getCustomerNumber());
        }
        return customerRepository.save(customer);
    }

    // UPDATE existing customer
    public Customer updateCustomer(Integer id, Customer updatedCustomer) {
        // First check customer exists
        Customer existing = getCustomerById(id);

        // Update only the fields
        existing.setCustomerName(updatedCustomer.getCustomerName());
        existing.setContactFirstName(updatedCustomer.getContactFirstName());
        existing.setContactLastName(updatedCustomer.getContactLastName());
        existing.setPhone(updatedCustomer.getPhone());
        existing.setAddressLine1(updatedCustomer.getAddressLine1());
        existing.setAddressLine2(updatedCustomer.getAddressLine2());
        existing.setCity(updatedCustomer.getCity());
        existing.setState(updatedCustomer.getState());
        existing.setPostalCode(updatedCustomer.getPostalCode());
        existing.setCountry(updatedCustomer.getCountry());
        existing.setSalesRepEmployeeNumber(
                updatedCustomer.getSalesRepEmployeeNumber());
        existing.setCreditLimit(updatedCustomer.getCreditLimit());

        return customerRepository.save(existing);
    }

    // DELETE customer
    public void deleteCustomer(Integer id) {
        Customer existing = getCustomerById(id);
        customerRepository.delete(existing);
    }

    // SEARCH by name
    public List<Customer> searchByName(String name) {
        return customerRepository
                .findByCustomerNameContainingIgnoreCase(name);
    }

    // FILTER by country
    public List<Customer> getByCountry(String country) {
        return customerRepository.findByCountry(country);
    }

    // FILTER by city
    public List<Customer> getByCity(String city) {
        return customerRepository.findByCity(city);
    }
}