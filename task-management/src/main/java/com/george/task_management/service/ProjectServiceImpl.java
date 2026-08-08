package com.george.task_management.service;

import com.george.task_management.dto.ProjectRequest;
import com.george.task_management.dto.ProjectResponse;
import com.george.task_management.entity.Project;
import com.george.task_management.exception.ProjectNotFoundException;
import com.george.task_management.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectResponse create(ProjectRequest request) {
        Project project = new Project(
                request.name(),
                request.description()
        );

        Project savedProject = projectRepository.save(project);

        return toResponse(savedProject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> findAll() {
        return projectRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse findById(Long id) {
        Project project = getProjectOrThrow(id);

        return toResponse(project);
    }

    @Override
    public ProjectResponse update(Long id, ProjectRequest request) {
        Project project = getProjectOrThrow(id);

        project.update(
                request.name(),
                request.description()
        );

        return toResponse(project);
    }

    @Override
    public void delete(Long id) {
        Project project = getProjectOrThrow(id);

        projectRepository.delete(project);
    }

    private Project getProjectOrThrow(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    private ProjectResponse toResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}