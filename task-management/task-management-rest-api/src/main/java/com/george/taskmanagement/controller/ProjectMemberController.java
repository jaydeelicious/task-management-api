package com.george.taskmanagement.controller;

import com.george.taskmanagement.domain.ProjectMember;
import com.george.taskmanagement.dto.AddProjectMemberRequest;
import com.george.taskmanagement.dto.ChangeProjectMemberRoleRequest;
import com.george.taskmanagement.dto.ProjectMemberResponse;
import com.george.taskmanagement.service.ProjectMemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/members")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    public ProjectMemberController(
            ProjectMemberService projectMemberService
    ) {
        this.projectMemberService = projectMemberService;
    }

    @PostMapping
    public ResponseEntity<ProjectMemberResponse> addMember(
            @PathVariable Long projectId,
            @Valid @RequestBody AddProjectMemberRequest request
    ) {
        ProjectMember member = projectMemberService.addMember(
                projectId,
                request.userId(),
                request.role()
        );

        ProjectMemberResponse response = toResponse(member);

        URI location = URI.create(
                "/api/projects/" +
                        projectId +
                        "/members/" +
                        response.userId()
        );

        return ResponseEntity
                .created(location)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectMemberResponse>> getMembers(
            @PathVariable Long projectId
    ) {
        List<ProjectMemberResponse> members =
                projectMemberService.findByProjectId(projectId)
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return ResponseEntity.ok(members);
    }

    @PatchMapping("/{userId}/role")
    public ResponseEntity<ProjectMemberResponse> changeRole(
            @PathVariable Long projectId,
            @PathVariable Long userId,
            @Valid @RequestBody ChangeProjectMemberRoleRequest request
    ) {
        ProjectMember member = projectMemberService.changeRole(
                projectId,
                userId,
                request.role()
        );

        return ResponseEntity.ok(toResponse(member));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable Long projectId,
            @PathVariable Long userId
    ) {
        projectMemberService.removeMember(projectId, userId);

        return ResponseEntity.noContent().build();
    }

    private ProjectMemberResponse toResponse(ProjectMember member) {
        return new ProjectMemberResponse(
                member.getId(),
                member.getProject().getId(),
                member.getUser().getId(),
                member.getUser().getUsername(),
                member.getRole(),
                member.getJoinedAt()
        );
    }
}
