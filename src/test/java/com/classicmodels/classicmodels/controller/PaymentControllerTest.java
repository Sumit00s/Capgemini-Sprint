package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.dto.PaymentDTO;
import com.classicmodels.classicmodels.entity.Payment;
import com.classicmodels.classicmodels.service.PaymentService;
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

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentService paymentService;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    void getAllPayments_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        when(paymentService.getAllPayments()).thenReturn(List.of(new PaymentDTO()));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
                
        verify(paymentService, times(1)).getAllPayments();
    }

    @Test
    void getPaymentsByCustomer_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        when(paymentService.getPaymentsByCustomer(101)).thenReturn(List.of(new PaymentDTO()));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/payments/customer/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void createPayment_ShouldReturn201_WhenValid() throws Exception {
        // ARRANGE
        Payment payment = new Payment();
        com.classicmodels.classicmodels.entity.PaymentId pid = new com.classicmodels.classicmodels.entity.PaymentId();
        pid.setCustomerNumber(101);
        pid.setCheckNumber("CHK123");
        payment.setId(pid);
        payment.setPaymentDate(java.time.LocalDate.now());
        payment.setAmount(new java.math.BigDecimal("100.00"));
        PaymentDTO dto = new PaymentDTO();
        when(paymentService.createPayment(any(Payment.class))).thenReturn(dto);

        // ACT & ASSERT
        mockMvc.perform(post("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isCreated());
                
        verify(paymentService, times(1)).createPayment(any(Payment.class));
    }

    @Test
    void createPayment_ShouldReturn400_WhenInvalid() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(post("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updatePayment_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        Payment payment = new Payment();
        com.classicmodels.classicmodels.entity.PaymentId pid = new com.classicmodels.classicmodels.entity.PaymentId();
        pid.setCustomerNumber(101);
        pid.setCheckNumber("CHK123");
        payment.setId(pid);
        payment.setPaymentDate(java.time.LocalDate.now());
        payment.setAmount(new java.math.BigDecimal("100.00"));
        PaymentDTO dto = new PaymentDTO();
        when(paymentService.updatePayment(eq(101), eq("CHK123"), any(Payment.class))).thenReturn(dto);

        // ACT & ASSERT
        mockMvc.perform(put("/api/v1/payments/101/CHK123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isOk());
    }

    @Test
    void deletePayment_ShouldReturn200_WhenFound() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(delete("/api/v1/payments/101/CHK123"))
                .andExpect(status().isOk());
                
        verify(paymentService, times(1)).deletePayment(101, "CHK123");
    }
}