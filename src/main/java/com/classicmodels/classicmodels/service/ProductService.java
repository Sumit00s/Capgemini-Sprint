package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entity.Product;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // GET all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // GET one product by code
    public Product getProductByCode(String code) {
        return productRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with code: " + code));
    }

    // CREATE new product
    public Product createProduct(Product product) {
        if (productRepository.existsById(product.getProductCode())) {
            throw new RuntimeException(
                    "Product already exists with code: " + product.getProductCode());
        }
        return productRepository.save(product);
    }

    // UPDATE existing product
    public Product updateProduct(String code, Product updatedProduct) {
        Product existing = getProductByCode(code);

        existing.setProductName(updatedProduct.getProductName());
        existing.setProductLine(updatedProduct.getProductLine());
        existing.setProductScale(updatedProduct.getProductScale());
        existing.setProductVendor(updatedProduct.getProductVendor());
        existing.setProductDescription(updatedProduct.getProductDescription());
        existing.setQuantityInStock(updatedProduct.getQuantityInStock());
        existing.setBuyPrice(updatedProduct.getBuyPrice());
        existing.setMsrp(updatedProduct.getMsrp());

        return productRepository.save(existing);
    }

    // DELETE product
    public void deleteProduct(String code) {
        Product existing = getProductByCode(code);
        productRepository.delete(existing);
    }

    // FILTER by product line
    public List<Product> getProductsByLine(String line) {
        return productRepository.findByProductLine(line);
    }
}