package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.entity.Office;
import com.classicmodels.classicmodels.service.OfficeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/offices")
@RequiredArgsConstructor
public class OfficeController {

    private final OfficeService officeService;

    // GET all offices
    // URL: GET http://localhost:8080/api/offices
    @GetMapping
    public ResponseEntity<List<Office>> getAllOffices() {
        return ResponseEntity.ok(officeService.getAllOffices());
    }

    // GET one office by code
    // URL: GET http://localhost:8080/api/offices/1
    @GetMapping("/{code}")
    public ResponseEntity<Office> getOfficeByCode(@PathVariable String code) {
        return ResponseEntity.ok(officeService.getOfficeByCode(code));
    }

    // CREATE new office
    // URL: POST http://localhost:8080/api/offices
    @PostMapping
    public ResponseEntity<Office> createOffice(
            @Valid @RequestBody Office office) {
        Office created = officeService.createOffice(office);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // UPDATE office
    // URL: PUT http://localhost:8080/api/offices/1
    @PutMapping("/{code}")
    public ResponseEntity<Office> updateOffice(
            @PathVariable String code,
            @Valid @RequestBody Office office) {
        return ResponseEntity.ok(officeService.updateOffice(code, office));
    }

    // DELETE office
    // URL: DELETE http://localhost:8080/api/offices/1
    @DeleteMapping("/{code}")
    public ResponseEntity<Map<String, String>> deleteOffice(
            @PathVariable String code) {
        officeService.deleteOffice(code);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Office deleted successfully with code: " + code);
        return ResponseEntity.ok(response);
    }
}