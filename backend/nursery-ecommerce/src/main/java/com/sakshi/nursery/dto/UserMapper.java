package com.sakshi.nursery.dto;


import com.sakshi.nursery.dto.UserDto;
import com.sakshi.nursery.model.User;

public class UserMapper {

    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAddress(user.getAddress());
        dto.setRole(user.getRole());
        dto.setProvider(user.getProvider());
        return dto;
    }
}

