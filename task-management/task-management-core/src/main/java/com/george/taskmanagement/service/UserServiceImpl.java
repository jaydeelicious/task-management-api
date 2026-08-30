package com.george.taskmanagement.service;

import com.george.taskmanagement.domain.User;
import com.george.taskmanagement.exception.DuplicateResourceException;
import com.george.taskmanagement.exception.ResourceNotFoundException;
import com.george.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User create(String username, String email) {
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new DuplicateResourceException(
                    "User with username '" + username + "' already exists"
            );
        }

        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new DuplicateResourceException(
                    "User with email '" + email + "' already exists"
            );
        }

        User user = new User(
                username,
                email
        );

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return getUserOrThrow(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository
                .findAll();
    }

    @Override
    @Transactional
    public User updateUsername(Long id, String username) {
        User user = getUserOrThrow(id);

        if (!user.getUsername().equalsIgnoreCase(username)
                && userRepository.existsByUsernameIgnoreCase(username)) {
            throw new DuplicateResourceException(
                    "User with username '" + username + "' already exists"
            );
        }

        user.updateUsername(username);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateEmail(Long id, String email) {
        User user = getUserOrThrow(id);

        if (!user.getEmail().equalsIgnoreCase(email)
                && userRepository.existsByEmailIgnoreCase(email)) {
            throw new DuplicateResourceException(
                    "User with email '" + email + "' already exists"
            );
        }

        user.updateEmail(email);

        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        getUserOrThrow(id);
        userRepository.deleteById(id);
    }

    private User getUserOrThrow(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id
                        )
                );
    }
}