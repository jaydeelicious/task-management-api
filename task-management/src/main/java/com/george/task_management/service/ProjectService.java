package com.george.task_management.service;

import com.george.task_management.dto.ProjectRequest;
import com.george.task_management.dto.ProjectResponse;

import java.util.List;

public interface ProjectService {

    ProjectResponse create(ProjectRequest request);

    List<ProjectResponse> findAll();

    ProjectResponse findById(Long id);

    ProjectResponse update(Long id, ProjectRequest request);

    void delete(Long id);
}
