package com.george.taskmanagement.repository;

import com.george.taskmanagement.domain.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    Project save(Project project);

    Optional<Project> findById(Long id);

    List<Project> findAll();

    void deleteById(Long id);

    //boolean existsByNameIgnoreCase(String );
}
