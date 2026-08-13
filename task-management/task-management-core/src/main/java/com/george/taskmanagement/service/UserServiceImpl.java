package com.george.taskmanagement.service;

import com.george.taskmanagement.domain.User;
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
    public User create(String username, String email) {
        return userRepository.save(
                new User(username, email)
        );
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

        user.updateUsername(username);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateEmail(Long id, String email) {
        User user = getUserOrThrow(id);

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