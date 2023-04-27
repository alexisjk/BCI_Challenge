package com.bci.controller;

import com.bci.dto.UserDTO;
import com.bci.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserDTO user) throws Exception {

        UserDTO createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);

    }

    @GetMapping("/login")
    public ResponseEntity<UserDTO> login(@AuthenticationPrincipal UserDetails userDetails) {

        UserDTO userDTO = userService.findByEmail(userDetails.getUsername());
        UserDTO updatedUserDTO = userService.updateJwtToken(userDTO);

        return ResponseEntity.ok(updatedUserDTO);
    }
}
