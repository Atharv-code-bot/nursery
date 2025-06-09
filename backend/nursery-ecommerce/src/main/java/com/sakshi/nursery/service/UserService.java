package com.sakshi.nursery.service;

import com.sakshi.nursery.config.AuthUtil;
import com.sakshi.nursery.dto.*;
import com.sakshi.nursery.model.AuthProvider;
import com.sakshi.nursery.model.Role;
import com.sakshi.nursery.model.User;
import com.sakshi.nursery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public UserDto createUser(UserRequest userRequest) {
        // Check if email already exists
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(Role.CUSTOMER);  // default role
        user.setProvider(AuthProvider.LOCAL);  // default provider
        user.setAddress(userRequest.getAddress());
        user.setPhoneNumber(userRequest.getPhoneNumber());

        User savedUser = userRepository.save(user);
        return UserMapper.toDto(savedUser);
    }


    public UserDto getCurrentUser() {
        User user = authUtil.getLoggedInUser();
        return UserMapper.toDto(user);
    }

    public UserDto updateUser(UpdateUserRequest request) {
        User user = authUtil.getLoggedInUser();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());

        userRepository.save(user);
        return UserMapper.toDto(user);
    }

    public void changePassword(PasswordChangeRequest request) {
        User user = authUtil.getLoggedInUser();

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public void deleteCurrentUser() {
        User user = authUtil.getLoggedInUser();
        userRepository.delete(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toDto(user);
    }

    public void updateUserRole(UUID id, Role role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    public void deleteUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }
}
