package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.entity.Product;
import com.classicmodels.classicmodels.entity.ProductLine;
import com.classicmodels.classicmodels.service.ProductLineService;
import com.classicmodels.classicmodels.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductLineService productLineService;

    // ─── PRODUCT ENDPOINTS ───────────────────────────────────────────────────

    // GET all products
    // URL: GET http://localhost:8080/api/products
    @GetMapping("/api/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // GET one product by code
    // URL: GET http://localhost:8080/api/products/S10_1678
    @GetMapping("/api/products/{code}")
    public ResponseEntity<Product> getProductByCode(@PathVariable String code) {
        return ResponseEntity.ok(productService.getProductByCode(code));
    }

    // CREATE new product
    // URL: POST http://localhost:8080/api/products
    @PostMapping("/api/products")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product created = productService.createProduct(product);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // UPDATE product
    // URL: PUT http://localhost:8080/api/products/S10_1678
    @PutMapping("/api/products/{code}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable String code,
            @Valid @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(code, product));
    }

    // DELETE product
    // URL: DELETE http://localhost:8080/api/products/S10_1678
    @DeleteMapping("/api/products/{code}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable String code) {
        productService.deleteProduct(code);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Product deleted successfully with code: " + code);
        return ResponseEntity.ok(response);
    }

    // FILTER by product line
    // URL: GET http://localhost:8080/api/products/search?line=Classic Cars
    @GetMapping("/api/products/search")
    public ResponseEntity<List<Product>> getProductsByLine(@RequestParam String line) {
        return ResponseEntity.ok(productService.getProductsByLine(line));
    }

    // ─── PRODUCT LINE ENDPOINTS ───────────────────────────────────────────────

    // GET all product lines
    // URL: GET http://localhost:8080/api/productlines
    @GetMapping("/api/productlines")
    public ResponseEntity<List<ProductLine>> getAllProductLines() {
        return ResponseEntity.ok(productLineService.getAllProductLines());
    }

    // GET one product line by name
    // URL: GET http://localhost:8080/api/productlines/Classic Cars
    @GetMapping("/api/productlines/{name}")
    public ResponseEntity<ProductLine> getProductLineByName(@PathVariable String name) {
        return ResponseEntity.ok(productLineService.getProductLineByName(name));
    }
}