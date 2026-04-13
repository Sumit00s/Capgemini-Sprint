package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.dto.OrderDTO;
import com.classicmodels.classicmodels.dto.OrderDetailDTO;
import com.classicmodels.classicmodels.dto.OrderTotalDTO;
import com.classicmodels.classicmodels.entity.Order;
import com.classicmodels.classicmodels.entity.OrderDetail;
import com.classicmodels.classicmodels.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody Order order) {
        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable Integer id,
            @Valid @RequestBody Order order) {
        return ResponseEntity.ok(orderService.updateOrder(id, order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Order deleted successfully with id: " + id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@RequestParam String status) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomer(@PathVariable Integer customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<List<OrderDetailDTO>> getOrderDetails(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderDetails(id));
    }

    // PRD 3.5 — Add a product line item to an existing order (with stock check)
    @PostMapping("/{id}/details")
    public ResponseEntity<OrderDetailDTO> addOrderDetail(
            @PathVariable Integer id,
            @Valid @RequestBody OrderDetail orderDetail) {
        return new ResponseEntity<>(orderService.addOrderDetail(id, orderDetail), HttpStatus.CREATED);
    }

    // PRD 3.6 — Get total monetary value breakdown of an order
    @GetMapping("/{id}/total")
    public ResponseEntity<OrderTotalDTO> getOrderTotal(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderTotal(id));
    }
}