package com.classicmodels.classicmodels.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @Column(name = "productCode")
    private String productCode;

    @Column(name = "productName", nullable = false)
    private String productName;

    @Column(name = "productLine", nullable = false)
    private String productLine;

    @Column(name = "productScale", nullable = false)
    private String productScale;

    @Column(name = "productVendor", nullable = false)
    private String productVendor;

    @Column(name = "productDescription", nullable = false, columnDefinition = "TEXT")
    private String productDescription;

    @Column(name = "quantityInStock", nullable = false)
    private Short quantityInStock;

    @Column(name = "buyPrice", nullable = false)
    private BigDecimal buyPrice;

    @Column(name = "MSRP", nullable = false)
    private BigDecimal msrp;
}