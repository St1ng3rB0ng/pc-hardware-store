package com.pc_hardware_shop.demo.service;

import com.pc_hardware_shop.demo.dto.CreateUserDTO;
import com.pc_hardware_shop.demo.entity.User;
import com.pc_hardware_shop.demo.exceprion.NotFoundException;
import com.pc_hardware_shop.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User createUser(CreateUserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.email())) {
            throw new IllegalArgumentException("User with email '" + userDTO.email() + "' already exists");
        }

        User createdUser = User.builder()
                .email(userDTO.email())
                .passwordHash(String.valueOf(userDTO.password().hashCode()))
                .role(userDTO.role())
                .createdAt(Instant.now())
                .build();

        User savedUser = userRepository.save(createdUser);
        log.info("Successfully created new user with ID: {} and email: {}", savedUser.getId(), savedUser.getEmail());

        return savedUser;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id '" + id + "' not found"));
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email '" + email + "' not found"));
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with id '" + id + "' not found");
        }
        userRepository.deleteById(id);
        log.info("Successfully deleted user with ID: {}", id);
    }
}