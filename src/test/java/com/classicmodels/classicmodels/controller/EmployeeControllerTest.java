package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.dto.EmployeeDTO;
import com.classicmodels.classicmodels.entity.Employee;
import com.classicmodels.classicmodels.service.EmployeeService;
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

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    void getAllEmployees_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        when(employeeService.getAllEmployees()).thenReturn(List.of(new EmployeeDTO()));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
                
        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void getEmployeeById_ShouldReturn200_WhenFound() throws Exception {
        // ARRANGE
        EmployeeDTO dto = new EmployeeDTO();
        dto.setFirstName("John");
        when(employeeService.getEmployeeById(1)).thenReturn(dto);

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
                
        verify(employeeService, times(1)).getEmployeeById(1);
    }

    @Test
    void getEmployeeById_ShouldReturn404_WhenNotFound() throws Exception {
        // ARRANGE
        when(employeeService.getEmployeeById(1)).thenThrow(new ResourceNotFoundException("Not found"));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createEmployee_ShouldReturn201_WhenValid() throws Exception {
        // ARRANGE
        Employee employee = new Employee();
        employee.setEmployeeNumber(1);
        employee.setLastName("Test");
        employee.setFirstName("Test");
        employee.setExtension("x1010");
        employee.setEmail("test@test.com");
        employee.setJobTitle("Test");
        EmployeeDTO dto = new EmployeeDTO();
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(dto);

        // ACT & ASSERT
        mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated());
                
        verify(employeeService, times(1)).createEmployee(any(Employee.class));
    }

    @Test
    void createEmployee_ShouldReturn400_WhenInvalid() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateEmployee_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        Employee employee = new Employee();
        employee.setEmployeeNumber(1);
        employee.setLastName("Test");
        employee.setFirstName("Test");
        employee.setExtension("x1010");
        employee.setEmail("test@test.com");
        employee.setJobTitle("Test");
        EmployeeDTO dto = new EmployeeDTO();
        when(employeeService.updateEmployee(eq(1), any(Employee.class))).thenReturn(dto);

        // ACT & ASSERT
        mockMvc.perform(put("/api/v1/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteEmployee_ShouldReturn200_WhenFound() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(delete("/api/v1/employees/1"))
                .andExpect(status().isOk());
                
        verify(employeeService, times(1)).deleteEmployee(1);
    }

    @Test
    void getEmployeesByJobTitle_ShouldReturn200_WhenValid() throws Exception {
        // ARRANGE
        when(employeeService.getByJobTitle("SalesRep")).thenReturn(List.of(new EmployeeDTO()));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/employees/filter/jobtitle?title=SalesRep"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
