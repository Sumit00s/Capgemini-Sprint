package com.classicmodels.classicmodels.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productlines")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductLine {

    @Id
    @Column(name = "productLine")
    private String productLine;

    @Column(name = "textDescription", length = 4000)
    private String textDescription;

    @Column(name = "htmlDescription", columnDefinition = "MEDIUMTEXT")
    private String htmlDescription;
}