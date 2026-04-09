package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.entity.Payment;
import com.classicmodels.classicmodels.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // GET all payments
    // URL: GET http://localhost:8080/api/payments
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    // GET payments by customer
    // URL: GET http://localhost:8080/api/payments/customer/103
    @GetMapping("/customer/{id}")
    public ResponseEntity<List<Payment>> getPaymentsByCustomer(
            @PathVariable Integer id) {
        return ResponseEntity.ok(paymentService.getPaymentsByCustomer(id));
    }

    // CREATE new payment
    // URL: POST http://localhost:8080/api/payments
    @PostMapping
    public ResponseEntity<Payment> createPayment(
            @RequestBody Payment payment) {
        Payment created = paymentService.createPayment(payment);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // DELETE payment by composite key
    // URL: DELETE http://localhost:8080/api/payments/103/HQ336336
    @DeleteMapping("/{cNo}/{checkNo}")
    public ResponseEntity<Map<String, String>> deletePayment(
            @PathVariable Integer cNo,
            @PathVariable String checkNo) {
        paymentService.deletePayment(cNo, checkNo);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Payment deleted successfully for customerNumber: "
                + cNo + " and checkNumber: " + checkNo);
        return ResponseEntity.ok(response);
    }
}