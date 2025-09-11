package com.trong.blog.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trong.blog.blog.domain.User;
import com.trong.blog.blog.domain.dto.response.user.ResCreateUserDTO;
import com.trong.blog.blog.domain.dto.response.user.ResUserDTO;
import com.trong.blog.blog.service.UserService;
import com.trong.blog.blog.util.annotation.ApiMessage;
import com.trong.blog.blog.util.exception.IdInvalidException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users/{id}")
    @ApiMessage("Get a user")
    public ResponseEntity<ResUserDTO> getUser(@PathVariable("id") Long id) throws IdInvalidException {
        if (this.userService.fetchUserById(id).isEmpty()) {
            throw new IdInvalidException("Id user: " + id + " not found");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.userService.convertToResUserDTO(this.userService.fetchUserById(id).get()));
    }

    @PostMapping("/users")
    @ApiMessage("Create a user")
    public ResponseEntity<ResCreateUserDTO> createUser(@Valid @RequestBody User userJson) throws IdInvalidException {
        if (this.userService.checkExistedEmail(userJson.getEmail())) {
            throw new IdInvalidException(userJson.getEmail() + "was Existed email");
        }
        userJson.setPassword(passwordEncoder.encode(userJson.getPassword()));
        User user = this.userService.saveUser(userJson);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUserDTO(user));
    }

}
