package com.classicmodels.classicmodels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentId implements Serializable {

    @Column(name = "customerNumber")
    private Integer customerNumber;

    @Column(name = "checkNumber")
    private String checkNumber;
}