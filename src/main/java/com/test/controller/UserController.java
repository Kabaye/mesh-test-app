package com.test.controller;

import com.test.entity.User;
import com.test.model.SearchUserRequest;
import com.test.model.UpdateUserRequest;
import com.test.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "Authorization")
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public List<User> findAll() {
        return userService.getAllUsers();
    }

    @PatchMapping("/{id}")
    public User update(@PathVariable("id") Long id, @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUser(id, updateUserRequest);
    }

    @GetMapping
    public Page<User> search(SearchUserRequest searchUserRequest) {
        return userService.search(searchUserRequest);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferTo(@RequestParam("emailTo") String emailTo,
                                        @RequestParam("amount") BigDecimal amount) {
        userService.transferMoney(emailTo, amount);
        return ResponseEntity.ok(Map.of("transfered", true));
    }
}
