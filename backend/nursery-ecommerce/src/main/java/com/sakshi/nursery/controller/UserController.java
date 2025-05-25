package com.sakshi.nursery.controller;

import com.sakshi.nursery.config.AuthUtil;
import com.sakshi.nursery.dto.PasswordChangeRequest;
import com.sakshi.nursery.dto.UpdateUserRequest;
import com.sakshi.nursery.dto.UserDto;
import com.sakshi.nursery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthUtil authUtil;

    // ✅ 1. Get current user profile
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    // ✅ 2. Update profile (name, email, address, phoneNumber)
    @PutMapping("/update")
    public ResponseEntity<UserDto> updateProfile(@RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateUser(request));
    }

    // ✅ 3. Change password
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest request) {
        userService.changePassword(request);
        return ResponseEntity.ok("Password changed successfully");
    }

    // ✅ 4. Delete own account
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCurrentUser() {
        userService.deleteCurrentUser();
        return ResponseEntity.ok("Account deleted successfully");
    }
}
