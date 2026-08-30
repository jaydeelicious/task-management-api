package com.george.taskmanagement.controller;

import com.george.taskmanagement.domain.User;
import com.george.taskmanagement.dto.UpdateEmailRequest;
import com.george.taskmanagement.dto.UpdateUsernameRequest;
import com.george.taskmanagement.dto.UserCreateRequest;
import com.george.taskmanagement.dto.UserResponse;
import com.george.taskmanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserCreateRequest request
            ) {
        User createdUser = userService.create(
                request.username(),
                request.email()
        );

        UserResponse response = toResponse(createdUser);

        URI location = URI.create("/api/users/" + response.id());

        return ResponseEntity
                .created(location)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserResponse> users = userService.findAll()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable Long userId
    ) {
        User user = userService.findById(userId);

        return ResponseEntity.ok(toResponse(user));
    }

    @PatchMapping("/{userId}/username")
    public ResponseEntity<UserResponse> updateUsername(
        @PathVariable Long userId,
        @Valid @RequestBody UpdateUsernameRequest request
    ) {
        User updatedUser = userService.updateUsername(
                userId,
                request.username()
        );

        return ResponseEntity.ok(toResponse(updatedUser));
    }

    @PatchMapping("/{userId}/email")
    public ResponseEntity<UserResponse> updateEmail(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateEmailRequest request
    ) {
        User updatedUser = userService.updateEmail(
                userId,
                request.email()
        );

        return ResponseEntity.ok(toResponse(updatedUser));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long userId
    ) {
        userService.delete(userId);

        return ResponseEntity.noContent().build();
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
