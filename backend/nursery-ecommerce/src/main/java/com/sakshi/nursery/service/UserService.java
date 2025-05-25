package com.sakshi.nursery.service;

import com.sakshi.nursery.config.AuthUtil;
import com.sakshi.nursery.dto.PasswordChangeRequest;
import com.sakshi.nursery.dto.UpdateUserRequest;
import com.sakshi.nursery.dto.UserDto;
import com.sakshi.nursery.dto.UserMapper;
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

    @Override
    public UserDto getCurrentUser() {
        User user = authUtil.getLoggedInUser();
        return UserMapper.toDto(user);
    }

    @Override
    public UserDto updateUser(UpdateUserRequest request) {
        User user = authUtil.getLoggedInUser();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());

        userRepository.save(user);
        return UserMapper.toDto(user);
    }

    @Override
    public void changePassword(PasswordChangeRequest request) {
        User user = authUtil.getLoggedInUser();

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void deleteCurrentUser() {
        User user = authUtil.getLoggedInUser();
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return UserMapper.toDto(user);
    }

    @Override
    public void updateUserRole(UUID id, Role role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }
}
