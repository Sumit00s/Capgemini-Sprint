package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.dto.ProductLineDTO;
import com.classicmodels.classicmodels.entity.ProductLine;
import com.classicmodels.classicmodels.service.ProductLineService;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductLineController.class)
public class ProductLineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductLineService productLineService;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    void getAllProductLines_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        when(productLineService.getAllProductLines()).thenReturn(List.of(new ProductLineDTO()));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/productlines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
                
        verify(productLineService, times(1)).getAllProductLines();
    }

    @Test
    void getProductLineByName_ShouldReturn200_WhenFound() throws Exception {
        // ARRANGE
        ProductLineDTO dto = new ProductLineDTO();
        dto.setProductLine("Classic Cars");
        when(productLineService.getProductLineByName("Classic Cars")).thenReturn(dto);

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/productlines/Classic Cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productLine").value("Classic Cars"));
                
        verify(productLineService, times(1)).getProductLineByName("Classic Cars");
    }

    @Test
    void getProductLineByName_ShouldReturn404_WhenNotFound() throws Exception {
        // ARRANGE
        when(productLineService.getProductLineByName("Unknown"))
                .thenThrow(new ResourceNotFoundException("Not found"));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/productlines/Unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createProductLine_ShouldReturn201_WhenValid() throws Exception {
        // ARRANGE
        ProductLine productLine = new ProductLine();
        productLine.setProductLine("Classic Cars");
        ProductLineDTO dto = new ProductLineDTO();
        when(productLineService.createProductLine(any(ProductLine.class))).thenReturn(dto);

        // ACT & ASSERT
        mockMvc.perform(post("/api/v1/productlines")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productLine)))
                .andExpect(status().isCreated());
                
        verify(productLineService, times(1)).createProductLine(any(ProductLine.class));
    }

    @Test
    void createProductLine_ShouldReturn400_WhenInvalid() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(post("/api/v1/productlines")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProductLine_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        ProductLine productLine = new ProductLine();
        productLine.setProductLine("Classic Cars");
        ProductLineDTO dto = new ProductLineDTO();
        when(productLineService.updateProductLine(eq("Classic Cars"), any(ProductLine.class))).thenReturn(dto);

        // ACT & ASSERT
        mockMvc.perform(put("/api/v1/productlines/Classic Cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productLine)))
                .andExpect(status().isOk());
                
        verify(productLineService, times(1)).updateProductLine(eq("Classic Cars"), any(ProductLine.class));
    }

    @Test
    void deleteProductLine_ShouldReturn200_WhenFound() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(delete("/api/v1/productlines/Classic Cars"))
                .andExpect(status().isOk());
                
        verify(productLineService, times(1)).deleteProductLine("Classic Cars");
    }
}