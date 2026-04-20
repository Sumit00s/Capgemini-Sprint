package com.classicmodels.classicmodels.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "userauth")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    private Integer customerNumber;

    private Integer employeeNumber;
}
