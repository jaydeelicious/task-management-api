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
    public TaskList findById(
            Long projectId,
            Long taskListId
    ) {
        return getTaskListOrThrow(projectId, taskListId);
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
            Long projectId,
            Long taskListId,
            String name
    ) {
        TaskList taskList = getTaskListOrThrow(
                projectId,
                taskListId
        );

        taskList.rename(name);

        return taskListRepository.save(taskList);
    }

    @Override
    public TaskList move(
            Long projectId,
            Long taskListId,
            int position
    ) {
        TaskList taskList = getTaskListOrThrow(
                projectId,
                taskListId
        );

        taskList.moveToPosition(position);

        return taskListRepository.save(taskList);
    }

    @Override
    public void delete(
            Long projectId,
            Long taskListId
    ) {
        getTaskListOrThrow(projectId, taskListId);

        taskListRepository.deleteById(taskListId);
    }

    private TaskList getTaskListOrThrow(
            Long projectId,
            Long taskListId
    ) {
        TaskList taskList = taskListRepository
                .findById(taskListId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task list not found with id: " + taskListId
                        )
                );

        if (!taskList.getProject().getId().equals(projectId)) {
            throw new ResourceNotFoundException(
                    "Task list not found with id: " +
                            taskListId +
                            " in project: " +
                            projectId
            );
        }

        return taskList;
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
