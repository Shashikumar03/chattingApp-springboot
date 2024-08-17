package com.example.chat.service;

import com.example.chat.dto.UserDto;
import com.example.chat.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDto createUser(UserDto user);

    Optional<UserDto> getUserById(String email);

    List<UserDto> getAllUsers();

    UserDto updateUser(String email, UserDto user);

    void deleteUser(String email);
}
