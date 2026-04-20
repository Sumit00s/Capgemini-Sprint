package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.entity.UserAuth;
import com.classicmodels.classicmodels.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserAuthRepository userAuthRepository;

    /**
     * POST /api/v1/auth/login
     * Body: { "email": "...", "password": "..." }
     * Returns user info on success, 401 on failure.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String email    = credentials.get("email");
        String password = credentials.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Email and password are required."));
        }

        Optional<UserAuth> userOpt = userAuthRepository.findByEmail(email);

        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password."));
        }

        UserAuth user = userOpt.get();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("authId",          user.getAuthId());
        response.put("email",           user.getEmail());
        response.put("role",            user.getRole());
        response.put("customerNumber",  user.getCustomerNumber());
        response.put("employeeNumber",  user.getEmployeeNumber());

        return ResponseEntity.ok(response);
    }
}
