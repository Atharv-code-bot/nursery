package com.sakshi.nursery.controller;

import com.sakshi.nursery.dto.RoleUpdateRequest;
import com.sakshi.nursery.dto.UserDto;
import com.sakshi.nursery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    @Autowired
    private UserService userService;

    // ✅ 1. Get all users
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ✅ 2. Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // ✅ 3. Update user role (CUSTOMER ⇄ ADMIN)
    @PutMapping("/{id}/role")
    public ResponseEntity<String> updateUserRole(@PathVariable UUID id, @RequestBody RoleUpdateRequest request) {
        userService.updateUserRole(id, request.getRole());
        return ResponseEntity.ok("User role updated");
    }

    // ✅ 4. Delete any user
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted");
    }
}
