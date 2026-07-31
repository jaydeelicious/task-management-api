package com.george.task_management.controller;

import com.george.task_management.dto.ProjectCreateRequest;
import com.george.task_management.dto.ProjectResponse;
import com.george.task_management.dto.ProjectUpdateRequest;
import com.george.task_management.service.ProjectService;
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
            @Valid @RequestBody ProjectCreateRequest request
    ) {
        ProjectResponse createdProject = projectService.create(request);

        URI location = URI.create("/api/projects/" + createdProject.id());

        return ResponseEntity
                .created(location)
                .body(createdProject);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getProjects() {
        List<ProjectResponse> projects = projectService.findAll();

        return ResponseEntity.ok(projects);
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
            @Valid @RequestBody ProjectUpdateRequest request
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
