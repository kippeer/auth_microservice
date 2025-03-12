package com.authmicroservice.demo.service;

import com.authmicroservice.demo.dto.UserDto;
import com.authmicroservice.demo.entity.User;
import java.util.List;

public interface UserService {
    User getCurrentUser();
    User getUserById(Long id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    User updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
    boolean changePassword(Long userId, String oldPassword, String newPassword);
}

