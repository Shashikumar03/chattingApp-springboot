package com.example.chat.controller;

import com.example.chat.dto.UserDto;
import com.example.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public UserDto createUser(@Validated @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/{email}")
    public Optional<UserDto> getUserById(@PathVariable String email) {
        return userService.getUserById(email);
    }

    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{email}")
    public UserDto updateUser(@PathVariable String email, @RequestBody UserDto userDto) {
        return userService.updateUser(email, userDto);
    }

    @DeleteMapping("/{email}")
    public void deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
    }
}
