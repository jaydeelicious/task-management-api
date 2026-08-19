package com.george.taskmanagement.controller;

import com.george.taskmanagement.domain.Project;
import com.george.taskmanagement.dto.ProjectRequest;
import com.george.taskmanagement.dto.ProjectResponse;
import com.george.taskmanagement.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @Valid @RequestBody ProjectRequest request
    ) {
        Project createdProject = projectService.create(
                request.name(),
                request.description()
        );

        ProjectResponse response = toResponse(createdProject);

        URI location = URI.create("/api/projects/" + response.id());

        return ResponseEntity
                .created(location)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getProjects() {
        List<ProjectResponse> projects = projectService.findAll()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(projects);
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

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProject(
            @PathVariable Long projectId
    ) {
        ProjectResponse project = projectService.findById(projectId);

        return ResponseEntity.ok(project);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectRequest request
    ) {
        ProjectResponse updatedProject =
                projectService.update(projectId, request);

        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable Long projectId
    ) {
        projectService.delete(projectId);

        return ResponseEntity.noContent().build();
    }
}
