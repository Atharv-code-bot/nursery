package com.sakshi.nursery.controller;

import com.sakshi.nursery.dto.*;
import com.sakshi.nursery.model.User;
import com.sakshi.nursery.repository.UserRepository;
import com.sakshi.nursery.service.UserService;
import com.sakshi.nursery.config.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {
        try {
            // Create user and get the saved user entity
            UserDto userDto = userService.createUser(userRequest);

            // Generate JWT token using the saved user's email
            String token = jwtUtil.generateToken(userDto.getEmail());



            // Return AuthResponse with token, user role, user id and include userDto if needed
            AuthResponse authResponse = new AuthResponse(token, userDto.getRole().name(), userDto.getId());


            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("User not found.");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.getUsername());

            User user = userOptional.get();
            return ResponseEntity.ok(new AuthResponse(token, user.getRole().name(), user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid credentials.");
        }
    }
}
