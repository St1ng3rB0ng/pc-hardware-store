package com.pc_hardware_shop.demo.service;

import com.pc_hardware_shop.demo.dto.CreateUserDTO;
import com.pc_hardware_shop.demo.entity.User;
import com.pc_hardware_shop.demo.exceprion.NotFoundException;
import com.pc_hardware_shop.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
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
        return userRepository.save(createdUser);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id '" + id + "' not found"));
    }

    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User with email '" + email + "' not found"));
    }

    @Transactional
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with id '" + id + "' not found");
        }
        userRepository.deleteById(id);
    }
}
