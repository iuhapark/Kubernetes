package com.example.kubernetes.user.controller;

import com.example.kubernetes.common.component.Messenger;
import com.example.kubernetes.common.component.pagination.PageRequestVo;
import com.example.kubernetes.user.model.User;
import com.example.kubernetes.user.model.UserDto;
import com.example.kubernetes.user.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*") // http://localhost:3000 에서 온 AJAX요청만 받아주겠다는 의미.
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/users")
@Slf4j
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")
})

public class UserController {
    private final UserService service;

    @SuppressWarnings("static-access")
    @PostMapping(path = "/save")
    public ResponseEntity<Messenger> save(@RequestBody UserDto dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> findAll(PageRequestVo vo) {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/detail")
    public ResponseEntity<Optional<UserDto>> findById(@RequestParam Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/modify")
    public ResponseEntity<Messenger> modify(@RequestBody UserDto param) {
        return ResponseEntity.ok(service.modify(param));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Messenger> deleteById(@RequestParam Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Messenger> count() {
        return ResponseEntity.ok(service.count());
    }

    @PostMapping("/search")
    public ResponseEntity<List<UserDto>> findByName(@RequestBody UserDto param) {
//        log.info("UserController search : {}", param);
        return ResponseEntity.ok(service.findByName(param.getName()));
    }

    @PostMapping("/username")
    public ResponseEntity<Optional<User>> findByUsername(@RequestBody UserDto param) {
        return ResponseEntity.ok(service.findByUsername(param.getName()));
    }

    @PostMapping("/email")
    public ResponseEntity<List<UserDto>> findByEmail(@RequestBody UserDto param) {
        return ResponseEntity.ok(service.findByEmail(param.getEmail()));
    }

    @PostMapping("/job")
    public ResponseEntity<List<UserDto>> findByJob(@RequestBody UserDto param) {
        return ResponseEntity.ok(service.findByJob(param.getJob()));
    }


    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsById(@RequestParam Long id) {
        return ResponseEntity.ok(service.existsById(id));
    }

}