package com.test.controller;

import com.test.entity.User;
import com.test.model.SearchUserRequest;
import com.test.model.UpdateUserRequest;
import com.test.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
