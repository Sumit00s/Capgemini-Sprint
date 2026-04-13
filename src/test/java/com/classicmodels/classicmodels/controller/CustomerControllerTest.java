package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.dto.CustomerDTO;
import com.classicmodels.classicmodels.entity.Customer;
import com.classicmodels.classicmodels.service.CustomerService;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    void getAllCustomers_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        when(customerService.getAllCustomers()).thenReturn(List.of(new CustomerDTO()));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
                
        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    void getCustomerById_ShouldReturn200_WhenFound() throws Exception {
        // ARRANGE
        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerName("Test Customer");
        when(customerService.getCustomerById(101)).thenReturn(dto);

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/customers/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Test Customer"));
                
        verify(customerService, times(1)).getCustomerById(101);
    }

    @Test
    void getCustomerById_ShouldReturn404_WhenNotFound() throws Exception {
        // ARRANGE
        when(customerService.getCustomerById(101)).thenThrow(new ResourceNotFoundException("Not found"));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/customers/101"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createCustomer_ShouldReturn201_WhenValid() throws Exception {
        // ARRANGE
        Customer customer = new Customer();
        customer.setCustomerNumber(101);
        customer.setCustomerName("Test");
        customer.setContactLastName("Test");
        customer.setContactFirstName("Test");
        customer.setPhone("1234567890");
        customer.setAddressLine1("Test");
        customer.setCity("Test");
        customer.setCountry("Test");
        CustomerDTO dto = new CustomerDTO();
        when(customerService.createCustomer(any(Customer.class))).thenReturn(dto);

        // ACT & ASSERT
        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated());
                
        verify(customerService, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    void createCustomer_ShouldReturn400_WhenInvalid() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateCustomer_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        Customer customer = new Customer();
        customer.setCustomerNumber(101);
        customer.setCustomerName("Test");
        customer.setContactLastName("Test");
        customer.setContactFirstName("Test");
        customer.setPhone("1234567890");
        customer.setAddressLine1("Test");
        customer.setCity("Test");
        customer.setCountry("Test");
        CustomerDTO dto = new CustomerDTO();
        when(customerService.updateCustomer(eq(101), any(Customer.class))).thenReturn(dto);

        // ACT & ASSERT
        mockMvc.perform(put("/api/v1/customers/101")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCustomer_ShouldReturn200_WhenFound() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(delete("/api/v1/customers/101"))
                .andExpect(status().isOk());
                
        verify(customerService, times(1)).deleteCustomer(101);
    }

    @Test
    void searchByName_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        when(customerService.searchByName("Test")).thenReturn(List.of(new CustomerDTO()));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/customers/search?name=Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
