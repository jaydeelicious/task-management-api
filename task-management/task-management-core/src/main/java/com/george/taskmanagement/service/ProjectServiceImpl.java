package com.george.taskmanagement.service;

import com.george.taskmanagement.domain.Project;
import com.george.taskmanagement.exception.DuplicateResourceException;
import com.george.taskmanagement.exception.ResourceNotFoundException;
import com.george.taskmanagement.repository.ProjectRepository;
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
    public Project create(String name, String description) {
        /*if (projectRepository.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException(
                    "A project with name '" + name + "' already exists"
            );
        }*/

        Project project = new Project(name, description);

        return projectRepository.save(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Project findById(Long id) {
        return getProjectOrThrow(id);
    }

    @Override
    public Project update(
            Long id,
            String name,
            String description
    ) {
        Project project = getProjectOrThrow(id);

        project.update(name, description);

        return projectRepository.save(project);
    }

    @Override
    public void delete(Long id) {
        getProjectOrThrow(id);

        projectRepository.deleteById(id);
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
}