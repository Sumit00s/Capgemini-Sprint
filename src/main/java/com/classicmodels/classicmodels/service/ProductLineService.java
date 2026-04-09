package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entity.ProductLine;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.repository.ProductLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductLineService {

    private final ProductLineRepository productLineRepository;

    // GET all product lines
    public List<ProductLine> getAllProductLines() {
        return productLineRepository.findAll();
    }

    // GET one product line by name
    public ProductLine getProductLineByName(String name) {
        return productLineRepository.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product line not found with name: " + name));
    }
}