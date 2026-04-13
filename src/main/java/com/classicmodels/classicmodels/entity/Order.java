package com.classicmodels.classicmodels.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "customer", "orderDetails" })
@EqualsAndHashCode(of = "orderNumber")
public class Order {

    @Id
    @Column(name = "orderNumber")
    @NotNull(message = "Order number is required")
    @Positive(message = "Order number must be a positive number")
    private Integer orderNumber;

    @Column(name = "orderDate", nullable = false)
    @NotNull(message = "Order date is required")
    @PastOrPresent(message = "Order date cannot be in the future")
    private LocalDate orderDate;

    @Column(name = "requiredDate", nullable = false)
    @NotNull(message = "Required date is required")
    @Future(message = "Required date must be in the future")
    private LocalDate requiredDate;

    @Column(name = "shippedDate")
    private LocalDate shippedDate;

    @Column(name = "status", nullable = false)
    @NotBlank(message = "Status is required")

    @Pattern(regexp = "^(Shipped|Resolved|Cancelled|On Hold|Disputed|In Process)$", message = "Status must be one of: Shipped, Resolved, Cancelled, On Hold, Disputed, In Process")
    private String status;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    // Many orders → one customer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerNumber", nullable = false)
    private Customer customer;

    // One order → many order details
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();
}