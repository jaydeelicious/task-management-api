package com.george.taskmanagement.service;

import com.george.taskmanagement.domain.Project;
import com.george.taskmanagement.domain.ProjectMember;
import com.george.taskmanagement.domain.ProjectRole;
import com.george.taskmanagement.domain.User;
import com.george.taskmanagement.exception.DuplicateResourceException;
import com.george.taskmanagement.exception.ResourceNotFoundException;
import com.george.taskmanagement.repository.ProjectMemberRepository;
import com.george.taskmanagement.repository.ProjectRepository;
import com.george.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectMemberServiceImpl(
            ProjectMemberRepository projectMemberRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository
    ) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProjectMember addMember(
            Long projectId,
            Long userId,
            ProjectRole role
    ) {
        Project project = getProjectOrThrow(projectId);
        User user = getUserOrThrow(userId);

        if (projectMemberRepository
                .existsByProjectIdAndUserId(projectId, userId)) {
            throw new DuplicateResourceException(
                    "User is already a member of this project"
            );
        }

        return projectMemberRepository.save(
                new ProjectMember(project, user, role)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectMember> findByProjectId(Long projectId) {
        getProjectOrThrow(projectId);

        return projectMemberRepository.findByProjectId(projectId);
    }

    @Override
    public ProjectMember changeRole(
            Long projectId,
            Long userId,
            ProjectRole role
    ) {
        ProjectMember member = getMemberOrThrow(projectId, userId);

        member.changeRole(role);

        return projectMemberRepository.save(member);
    }

    @Override
    public void removeMember(
            Long projectId,
            Long userId
    ) {
        ProjectMember member = getMemberOrThrow(projectId, userId);

        projectMemberRepository.deleteById(member.getId());
    }

    private ProjectMember getMemberOrThrow(
            Long projectId,
            Long userId
    ) {
        return projectMemberRepository
                .findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project member not found"
                        )
                );
    }

    private Project getProjectOrThrow(Long id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + id
                        )
                );
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
