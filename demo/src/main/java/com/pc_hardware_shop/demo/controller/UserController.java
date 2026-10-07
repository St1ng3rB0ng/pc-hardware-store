package com.pc_hardware_shop.demo.controller;

import com.pc_hardware_shop.demo.dto.CreateUserDTO;
import com.pc_hardware_shop.demo.entity.User;
import com.pc_hardware_shop.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO userDTO){
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping("/by-id/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id){
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/by-email")
    public ResponseEntity<User> findUserByEmail(@RequestParam String email){
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
}
