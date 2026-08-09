package com.george.taskmanagement.db.adapter;

import com.george.taskmanagement.db.mapper.ProjectMapper;
import com.george.taskmanagement.db.repository.JpaProjectRepository;
import com.george.taskmanagement.domain.Project;
import com.george.taskmanagement.repository.ProjectRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProjectRepositoryAdapter implements ProjectRepository {

    private final JpaProjectRepository jpaProjectRepository;
    private final ProjectMapper projectMapper;

    public ProjectRepositoryAdapter(
            JpaProjectRepository jpaProjectRepository,
            ProjectMapper projectMapper
    ) {
        this.jpaProjectRepository = jpaProjectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public Project save(Project project) {
        var entity = projectMapper.toEntity(project);
        var savedEntity = jpaProjectRepository.save(entity);

        return projectMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Project> findById(Long id) {
        return jpaProjectRepository
                .findById(id)
                .map(projectMapper::toDomain);
    }

    @Override
    public List<Project> findAll() {
        return jpaProjectRepository
                .findAll()
                .stream()
                .map(projectMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaProjectRepository.deleteById(id);
    }
}