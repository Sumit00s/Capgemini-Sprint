package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.dto.OfficeDTO;
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
@RequestMapping("/api/v1/offices")
@RequiredArgsConstructor
public class OfficeController {

    private final OfficeService officeService;

    @GetMapping
    public ResponseEntity<List<OfficeDTO>> getAllOffices() {
        return ResponseEntity.ok(officeService.getAllOffices());
    }

    @GetMapping("/{code}")
    public ResponseEntity<OfficeDTO> getOfficeByCode(@PathVariable String code) {
        return ResponseEntity.ok(officeService.getOfficeByCode(code));
    }

    @PostMapping
    public ResponseEntity<OfficeDTO> createOffice(@Valid @RequestBody Office office) {
        return new ResponseEntity<>(officeService.createOffice(office), HttpStatus.CREATED);
    }

    @PutMapping("/{code}")
    public ResponseEntity<OfficeDTO> updateOffice(
            @PathVariable String code,
            @Valid @RequestBody Office office) {
        return ResponseEntity.ok(officeService.updateOffice(code, office));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Map<String, String>> deleteOffice(@PathVariable String code) {
        officeService.deleteOffice(code);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Office deleted successfully with code: " + code);
        return ResponseEntity.ok(response);
    }
}