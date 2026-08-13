package com.george.taskmanagement.service;

import com.george.taskmanagement.domain.Project;
import com.george.taskmanagement.domain.TaskList;
import com.george.taskmanagement.exception.ResourceNotFoundException;
import com.george.taskmanagement.repository.ProjectRepository;
import com.george.taskmanagement.repository.TaskListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;
    private final ProjectRepository projectRepository;

    public TaskListServiceImpl(
            TaskListRepository taskListRepository,
            ProjectRepository projectRepository
    ) {
        this.taskListRepository = taskListRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public TaskList create(
            Long projectId,
            String name,
            int position
    ) {
        Project project = getProjectOrThrow(projectId);

        TaskList taskList = new TaskList(
                project,
                name,
                position
        );

        return taskListRepository.save(taskList);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskList findById(Long id) {
        return getTaskListOrThrow(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskList> findByProjectId(Long projectId) {
        getProjectOrThrow(projectId);

        return taskListRepository
                .findByProjectIdOrderByPosition(projectId);
    }

    @Override
    public TaskList rename(
            Long id,
            String name
    ) {
        TaskList taskList = getTaskListOrThrow(id);

        taskList.rename(name);

        return taskListRepository.save(taskList);
    }

    @Override
    public TaskList move(
            Long id,
            int position
    ) {
        TaskList taskList = getTaskListOrThrow(id);

        taskList.moveToPosition(position);

        return taskListRepository.save(taskList);
    }

    @Override
    public void delete(Long id) {
        getTaskListOrThrow(id);

        taskListRepository.deleteById(id);
    }

    private TaskList getTaskListOrThrow(Long id) {
        return taskListRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task list not found with id: " + id
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
