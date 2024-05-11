package com.example.kubernetes.user.controller;

import com.example.kubernetes.common.component.Messenger;
import com.example.kubernetes.user.model.UserDto;
import com.example.kubernetes.user.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")
})
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/auth")
@Slf4j

public class AuthController {
    private final UserService service;

    @PostMapping(path = "/login")
    public ResponseEntity<Messenger> login(@RequestBody UserDto dto) throws SQLException {
        Messenger messenger = service.login(dto);
        return ResponseEntity.ok(messenger);
    }

    @GetMapping("/existsUsername")
    public ResponseEntity<Boolean> existsByUsername(@RequestParam("username") String username) {
        log.info("Parameter information of existsUsername: " + username);
        Boolean flag = service.existsUsername(username);
        log.info("existsUsername : " + username);
        return ResponseEntity.ok(flag);
    }
}
