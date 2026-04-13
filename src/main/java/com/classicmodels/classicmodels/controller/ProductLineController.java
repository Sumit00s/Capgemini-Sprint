package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.dto.ProductLineDTO;
import com.classicmodels.classicmodels.entity.ProductLine;
import com.classicmodels.classicmodels.service.ProductLineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/productlines")
@RequiredArgsConstructor
public class ProductLineController {

    private final ProductLineService productLineService;

    @GetMapping
    public ResponseEntity<List<ProductLineDTO>> getAllProductLines() {
        return ResponseEntity.ok(productLineService.getAllProductLines());
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductLineDTO> getProductLineByName(@PathVariable String name) {
        return ResponseEntity.ok(productLineService.getProductLineByName(name));
    }

    @PostMapping
    public ResponseEntity<ProductLineDTO> createProductLine(
            @Valid @RequestBody ProductLine productLine) {
        return new ResponseEntity<>(
                productLineService.createProductLine(productLine), HttpStatus.CREATED);
    }

    @PutMapping("/{name}")
    public ResponseEntity<ProductLineDTO> updateProductLine(
            @PathVariable String name,
            @Valid @RequestBody ProductLine productLine) {
        return ResponseEntity.ok(productLineService.updateProductLine(name, productLine));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Map<String, String>> deleteProductLine(@PathVariable String name) {
        productLineService.deleteProductLine(name);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Product line deleted successfully with name: " + name);
        return ResponseEntity.ok(response);
    }
}