package com.george.taskmanagement.service;

import com.george.taskmanagement.domain.User;

import java.util.List;

public interface UserService {

    User create(String username, String email);

    User findById(Long id);

    List<User> findAll();

    User updateUsername(Long id, String username);

    User updateEmail(Long id, String email);

    void delete(Long id);
}
