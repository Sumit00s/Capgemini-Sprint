package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OfficeRepository extends JpaRepository<Office, String> {

    // Filter by country
    List<Office> findByCountry(String country);

    // Filter by city
    List<Office> findByCity(String city);
}