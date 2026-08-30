package com.george.taskmanagement.service;

import com.george.taskmanagement.domain.Label;
import com.george.taskmanagement.domain.Project;
import com.george.taskmanagement.exception.ResourceNotFoundException;
import com.george.taskmanagement.repository.LabelRepository;
import com.george.taskmanagement.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;
    private final ProjectRepository projectRepository;

    public LabelServiceImpl(
            LabelRepository labelRepository,
            ProjectRepository projectRepository
    ) {
        this.labelRepository = labelRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Label create(
            Long projectId,
            String name,
            String color
    ) {
        Project project = getProjectOrThrow(projectId);

        Label label = new Label(project, name, color);

        return labelRepository.save(label);
    }

    @Override
    @Transactional(readOnly = true)
    public Label findById(Long id) {
        return getLabelOrThrow(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Label> findByProjectId(Long projectId) {
        getProjectOrThrow(projectId);

        return labelRepository.findByProjectId(projectId);
    }

    @Override
    public Label rename(Long id, String name) {
        Label label = getLabelOrThrow(id);

        label.rename(name);

        return labelRepository.save(label);
    }

    @Override
    public Label changeColor(Long id, String color) {
        Label label = getLabelOrThrow(id);

        label.changeColor(color);

        return labelRepository.save(label);
    }

    @Override
    public void delete(Long id) {
        getLabelOrThrow(id);
        labelRepository.deleteById(id);
    }

    private Label getLabelOrThrow(Long id) {
        return labelRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Label not found with id: " + id
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
}
