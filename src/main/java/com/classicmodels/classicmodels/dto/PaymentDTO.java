package com.classicmodels.classicmodels.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentDTO {
    
    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be valid")
    private Integer customerNumber;
    
    private String customerName;
    
    @NotBlank(message = "Check number is required")
    @Size(max = 50, message = "Check number cannot exceed 50 characters")
    private String checkNumber;
    
    @NotNull(message = "Payment date is required")
    @PastOrPresent(message = "Payment date cannot be in the future")
    private LocalDate paymentDate;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    @Digits(integer = 10, fraction = 2, message = "Amount must be a valid currency value")
    private BigDecimal amount;
}