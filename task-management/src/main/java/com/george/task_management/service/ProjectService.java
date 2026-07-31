package com.george.task_management.service;

import com.george.task_management.dto.ProjectCreateRequest;
import com.george.task_management.dto.ProjectResponse;
import com.george.task_management.dto.ProjectUpdateRequest;

import java.util.List;

public interface ProjectService {

    ProjectResponse create(ProjectCreateRequest request);

    List<ProjectResponse> findAll();

    ProjectResponse findById(Long id);

    ProjectResponse update(Long id, ProjectUpdateRequest request);

    void delete(Long id);
}
