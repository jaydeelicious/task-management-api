package com.george.taskmanagement.service;

import com.george.taskmanagement.domain.Project;

import java.util.List;

public interface ProjectService {

    Project create(String name, String description);

    List<Project> findAll();

    Project findById(Long id);

    Project update(Long id, String name, String description);

    void delete(Long id);
}