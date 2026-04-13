package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.ProductDTO;
import com.classicmodels.classicmodels.entity.Product;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.mapper.EntityMapper;
import com.classicmodels.classicmodels.repository.ProductLineRepository;
import com.classicmodels.classicmodels.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    
    @Mock
    private ProductLineRepository productLineRepository;

    @Mock
    private EntityMapper mapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void getAllProducts_ShouldReturnList_WhenProductsExist() {
        // ARRANGE
        Product product = new Product();
        ProductDTO dto = new ProductDTO();
        when(productRepository.findAll()).thenReturn(List.of(product));
        when(mapper.toProductDTO(product)).thenReturn(dto);

        // ACT
        List<ProductDTO> result = productService.getAllProducts();

        // ASSERT
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getAllProducts_ShouldReturnEmptyList_WhenNoProductsExist() {
        // ARRANGE
        when(productRepository.findAll()).thenReturn(List.of());

        // ACT
        List<ProductDTO> result = productService.getAllProducts();

        // ASSERT
        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductByCode_ShouldReturnProduct_WhenFound() {
        // ARRANGE
        Product product = new Product();
        ProductDTO dto = new ProductDTO();
        when(productRepository.findById("P1")).thenReturn(Optional.of(product));
        when(mapper.toProductDTO(product)).thenReturn(dto);

        // ACT
        ProductDTO result = productService.getProductByCode("P1");

        // ASSERT
        assertNotNull(result);
        verify(productRepository, times(1)).findById("P1");
    }

    @Test
    void getProductByCode_ShouldThrowException_WhenNotFound() {
        // ARRANGE
        when(productRepository.findById("P1")).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductByCode("P1"));
        verify(productRepository, times(1)).findById("P1");
    }

    @Test
    void createProduct_ShouldSaveAndReturnProduct_WhenValid() {
        // ARRANGE
        Product product = new Product();
        product.setProductCode("P1");
        ProductDTO dto = new ProductDTO();
        when(productRepository.existsById("P1")).thenReturn(false);
        when(productRepository.save(product)).thenReturn(product);
        when(mapper.toProductDTO(product)).thenReturn(dto);

        // ACT
        ProductDTO result = productService.createProduct(product);

        // ASSERT
        assertNotNull(result);
        verify(productRepository, times(1)).save(product);
    }
    
    @Test
    void updateProduct_ShouldUpdateAndReturnProduct_WhenFound() {
        // ARRANGE
        Product existing = new Product();
        Product updated = new Product();
        ProductDTO dto = new ProductDTO();
        when(productRepository.findById("P1")).thenReturn(Optional.of(existing));
        when(productRepository.save(existing)).thenReturn(existing);
        when(mapper.toProductDTO(existing)).thenReturn(dto);

        // ACT
        ProductDTO result = productService.updateProduct("P1", updated);

        // ASSERT
        assertNotNull(result);
        verify(productRepository, times(1)).save(existing);
    }

    @Test
    void updateProduct_ShouldThrowException_WhenMissing() {
        // ARRANGE
        Product updated = new Product();
        when(productRepository.findById("P1")).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct("P1", updated));
    }

    @Test
    void deleteProduct_ShouldDeleteSuccessfully_WhenFound() {
        // ARRANGE
        Product product = new Product();
        when(productRepository.findById("P1")).thenReturn(Optional.of(product));

        // ACT
        productService.deleteProduct("P1");

        // ASSERT
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void deleteProduct_ShouldThrowException_WhenMissing() {
        // ARRANGE
        when(productRepository.findById("P1")).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct("P1"));
    }

    @Test
    void getProductsByLine_ShouldReturnList_WhenProductsExist() {
        // ARRANGE
        Product product = new Product();
        ProductDTO dto = new ProductDTO();
        when(productRepository.findByProductLineObj_ProductLine("Ships")).thenReturn(List.of(product));
        when(mapper.toProductDTO(product)).thenReturn(dto);

        // ACT
        List<ProductDTO> result = productService.getProductsByLine("Ships");

        // ASSERT
        assertFalse(result.isEmpty());
        verify(productRepository, times(1)).findByProductLineObj_ProductLine("Ships");
    }

    @Test
    void getLowStockProducts_ShouldReturnList_WhenProductsExist() {
        // ARRANGE
        Product product = new Product();
        ProductDTO dto = new ProductDTO();
        when(productRepository.findByQuantityInStockLessThan((short) 100)).thenReturn(List.of(product));
        when(mapper.toProductDTO(product)).thenReturn(dto);

        // ACT
        List<ProductDTO> result = productService.getLowStockProducts((short) 100);

        // ASSERT
        assertFalse(result.isEmpty());
        verify(productRepository, times(1)).findByQuantityInStockLessThan((short) 100);
    }
}