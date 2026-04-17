package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.ProductDTO;
import com.classicmodels.classicmodels.entity.Product;
import com.classicmodels.classicmodels.entity.ProductLine;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.mapper.EntityMapper;
import com.classicmodels.classicmodels.repository.ProductLineRepository;
import com.classicmodels.classicmodels.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductLineRepository productLineRepository;
    private final EntityMapper mapper;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(mapper::toProductDTO)
                .toList();
    }

    public ProductDTO getProductByCode(String code) {
        Product product = productRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with code: " + code));
        return mapper.toProductDTO(product);
    }

    public ProductDTO createProduct(Product product) {
        if (productRepository.existsById(product.getProductCode())) {
            throw new RuntimeException(
                    "Product already exists with code: " + product.getProductCode());
        }
        return mapper.toProductDTO(productRepository.save(product));
    }

    public ProductDTO updateProduct(String code, Product updatedProduct) {
        Product existing = productRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with code: " + code));

        existing.setProductName(updatedProduct.getProductName());
        existing.setProductScale(updatedProduct.getProductScale());
        existing.setProductVendor(updatedProduct.getProductVendor());
        existing.setProductDescription(updatedProduct.getProductDescription());
        existing.setQuantityInStock(updatedProduct.getQuantityInStock());
        existing.setBuyPrice(updatedProduct.getBuyPrice());
        existing.setMsrp(updatedProduct.getMsrp());

        if (updatedProduct.getProductLineObj() != null
                && updatedProduct.getProductLineObj().getProductLine() != null) {
            ProductLine productLine = productLineRepository
                    .findById(updatedProduct.getProductLineObj().getProductLine())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Product line not found: "
                                    + updatedProduct.getProductLineObj().getProductLine()));
            existing.setProductLineObj(productLine);
        }

        return mapper.toProductDTO(productRepository.save(existing));
    }

    public void deleteProduct(String code) {
        Product existing = productRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with code: " + code));
        productRepository.delete(existing);
    }

    public List<ProductDTO> getProductsByLine(String line) {
        return productRepository.findByProductLineObj_ProductLine(line)
                .stream()
                .map(mapper::toProductDTO)
                .toList();
    }

    // PRD 3.7 — Get products with stock below threshold
    public List<ProductDTO> getLowStockProducts(Short threshold) {
        return productRepository.findByQuantityInStockLessThan(threshold)
                .stream()
                .map(mapper::toProductDTO)
                .toList();
    }

    public List<ProductDTO> searchByName(String name) {
        return productRepository.findByProductNameContainingIgnoreCase(name)
                .stream()
                .map(mapper::toProductDTO)
                .toList();
    }

    public List<ProductDTO> getByVendor(String vendor) {
        return productRepository.findByProductVendor(vendor)
                .stream()
                .map(mapper::toProductDTO)
                .toList();
    }

    public List<String> getAllVendors() {
        return productRepository.findDistinctProductVendors();
    }
}