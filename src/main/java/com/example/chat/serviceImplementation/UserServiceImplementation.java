package com.example.chat.serviceImplementation;

import com.example.chat.dto.UserDto;
import com.example.chat.entities.User;
import com.example.chat.repository.UserRepository;
import com.example.chat.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
       boolean isPresent= this.userRepository.existsByEmail(userDto.getEmail());
       if(isPresent){
           throw  new RuntimeException("User already exists");
       }
        // Convert UserDto to User entity
        User user = modelMapper.map(userDto, User.class);
        // Save the user entity
        User savedUser = userRepository.save(user);
        // Convert the saved User entity back to UserDto
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public Optional<UserDto> getUserById(String email) {
        // Find the user by ID and map to UserDto if found
        return userRepository.findById(email)
                .map(user -> modelMapper.map(user, UserDto.class));
    }

    @Override
    public List<UserDto> getAllUsers() {
        // Find all users and map to UserDto list
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(String email, UserDto userDto) {
        // Find the existing user by ID
        Optional<User> existingUserOptional = userRepository.findById(email);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            // Update the existing user's details
            existingUser.setName(userDto.getName());
            existingUser.setEmail(userDto.getEmail());
            // Save the updated user entity
            User updatedUser = userRepository.save(existingUser);
            // Convert the updated User entity back to UserDto
            return modelMapper.map(updatedUser, UserDto.class);
        } else {
            throw new RuntimeException("User not found with ID " + email);
        }
    }

    @Override
    public void deleteUser(String email) {
        // Delete the user if exists
        if (userRepository.existsById(email)) {
            userRepository.deleteById(email);
        } else {
            throw new RuntimeException("User not found with ID " + email);
        }
    }
}
