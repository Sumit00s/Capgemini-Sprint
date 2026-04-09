package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entity.Order;
import com.classicmodels.classicmodels.entity.OrderDetail;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.repository.OrderDetailRepository;
import com.classicmodels.classicmodels.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    // GET all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // GET one order by ID
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with id: " + id));
    }

    // CREATE new order
    public Order createOrder(Order order) {
        if (orderRepository.existsById(order.getOrderNumber())) {
            throw new RuntimeException(
                    "Order already exists with number: " + order.getOrderNumber());
        }
        return orderRepository.save(order);
    }

    // UPDATE existing order
    public Order updateOrder(Integer id, Order updatedOrder) {
        Order existing = getOrderById(id);

        existing.setOrderDate(updatedOrder.getOrderDate());
        existing.setRequiredDate(updatedOrder.getRequiredDate());
        existing.setShippedDate(updatedOrder.getShippedDate());
        existing.setStatus(updatedOrder.getStatus());
        existing.setComments(updatedOrder.getComments());
        existing.setCustomerNumber(updatedOrder.getCustomerNumber());

        return orderRepository.save(existing);
    }

    // DELETE order
    public void deleteOrder(Integer id) {
        Order existing = getOrderById(id);
        orderRepository.delete(existing);
    }

    // FILTER by status
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    // GET orders by customer
    public List<Order> getOrdersByCustomer(Integer customerId) {
        return orderRepository.findByCustomerNumber(customerId);
    }

    // GET all details for an order
    public List<OrderDetail> getOrderDetails(Integer orderId) {
        // Verify the order exists first
        getOrderById(orderId);
        return orderDetailRepository.findByIdOrderNumber(orderId);
    }
}