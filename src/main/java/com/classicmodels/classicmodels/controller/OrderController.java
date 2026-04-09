package com.classicmodels.classicmodels.controller;

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
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // GET all orders
    // URL: GET http://localhost:8080/api/orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // GET one order by ID
    // URL: GET http://localhost:8080/api/orders/10100
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // CREATE new order
    // URL: POST http://localhost:8080/api/orders
    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        Order created = orderService.createOrder(order);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // UPDATE order
    // URL: PUT http://localhost:8080/api/orders/10100
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable Integer id,
            @Valid @RequestBody Order order) {
        return ResponseEntity.ok(orderService.updateOrder(id, order));
    }

    // DELETE order
    // URL: DELETE http://localhost:8080/api/orders/10100
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Order deleted successfully with id: " + id);
        return ResponseEntity.ok(response);
    }

    // FILTER by status
    // URL: GET http://localhost:8080/api/orders/search?status=Shipped
    @GetMapping("/search")
    public ResponseEntity<List<Order>> getOrdersByStatus(@RequestParam String status) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }

    // GET orders by customer
    // URL: GET http://localhost:8080/api/orders/customer/103
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomer(@PathVariable Integer customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
    }

    // GET all items in an order
    // URL: GET http://localhost:8080/api/orders/10100/details
    @GetMapping("/{id}/details")
    public ResponseEntity<List<OrderDetail>> getOrderDetails(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderDetails(id));
    }
}