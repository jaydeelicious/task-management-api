package com.george.taskmanagement.db.adapter;

import com.george.taskmanagement.db.mapper.ProjectMemberMapper;
import com.george.taskmanagement.db.repository.JpaProjectMemberRepository;
import com.george.taskmanagement.domain.ProjectMember;
import com.george.taskmanagement.repository.ProjectMemberRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProjectMemberRepositoryAdapter implements ProjectMemberRepository {

    private final JpaProjectMemberRepository jpaProjectMemberRepository;
    private final ProjectMemberMapper projectMemberMapper;

    public ProjectMemberRepositoryAdapter(
            JpaProjectMemberRepository jpaProjectMemberRepository,
            ProjectMemberMapper projectMemberMapper
    ) {
        this.jpaProjectMemberRepository = jpaProjectMemberRepository;
        this.projectMemberMapper = projectMemberMapper;
    }

    @Override
    public ProjectMember save(ProjectMember projectMember) {
        var entity = projectMemberMapper.toEntity(projectMember);
        var savedEntity = jpaProjectMemberRepository.save(entity);

        return projectMemberMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<ProjectMember> findById(Long id) {
        return jpaProjectMemberRepository
                .findById(id)
                .map(projectMemberMapper::toDomain);
    }

    @Override
    public List<ProjectMember> findByProjectId(Long projectId) {
        return jpaProjectMemberRepository
                .findByProjectId(projectId)
                .stream()
                .map(projectMemberMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<ProjectMember> findByProjectIdAndUserId(
            Long projectId,
            Long userId
    ) {
        return jpaProjectMemberRepository
                .findByProjectIdAndUserId(projectId, userId)
                .map(projectMemberMapper::toDomain);
    }

    @Override
    public boolean existsByProjectIdAndUserId(
            Long projectId,
            Long userId
    ) {
        return jpaProjectMemberRepository
                .existsByProjectIdAndUserId(projectId, userId);
    }

    @Override
    public void deleteById(Long id) {
        jpaProjectMemberRepository.deleteById(id);
    }
}