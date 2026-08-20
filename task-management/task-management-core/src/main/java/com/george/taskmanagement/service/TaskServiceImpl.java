package com.george.taskmanagement.service;

import com.george.taskmanagement.domain.Label;
import com.george.taskmanagement.domain.Task;
import com.george.taskmanagement.domain.TaskList;
import com.george.taskmanagement.domain.TaskPriority;
import com.george.taskmanagement.exception.InvalidOperationException;
import com.george.taskmanagement.exception.ResourceNotFoundException;
import com.george.taskmanagement.repository.LabelRepository;
import com.george.taskmanagement.repository.TaskListRepository;
import com.george.taskmanagement.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;
    private final LabelRepository labelRepository;

    public TaskServiceImpl(
            TaskRepository taskRepository,
            TaskListRepository taskListRepository,
            LabelRepository labelRepository
    ) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
        this.labelRepository = labelRepository;
    }

    @Override
    public Task create(
            Long listId,
            String title,
            String description,
            int position,
            TaskPriority priority,
            LocalDate dueDate
    ) {
        TaskList list = getTaskListOrThrow(listId);

        Task task = new Task(
                title,
                description,
                list,
                position,
                priority,
                dueDate
        );

        return taskRepository.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public Task findById(
            Long projectId,
            Long listId,
            Long taskId
    ) {
        return getTaskOrThrow(projectId, listId, taskId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findByListId(
            Long projectId,
            Long listId
    ) {
        TaskList taskList = taskListRepository.findById(listId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task list not found with id: " + listId
                        )
                );

        if (!taskList.getProject().getId().equals(projectId)) {
            throw new ResourceNotFoundException(
                    "Task list not found with id: "
                            + listId
                            + " in project: "
                            + projectId
            );
        }

        return taskRepository.findByListIdOrderByPosition(listId);
    }

    @Override
    @Transactional
    public Task updateTitle(
            Long projectId,
            Long listId,
            Long taskId,
            String title
    ) {
        Task task = getTaskOrThrow(
                projectId,
                listId,
                taskId
        );

        task.updateTitle(title);

        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task updateDescription(
            Long projectId,
            Long listId,
            Long taskId,
            String description
    ) {
        Task task = getTaskOrThrow(
                projectId,
                listId,
                taskId
        );

        task.updateDescription(description);

        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task updatePriority(
            Long projectId,
            Long listId,
            Long taskId,
            TaskPriority priority
    ) {
        Task task = getTaskOrThrow(
                projectId,
                listId,
                taskId
        );

        task.updatePriority(priority);

        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task updateDueDate(
            Long projectId,
            Long listId,
            Long taskId,
            LocalDate dueDate
    ) {
        Task task = getTaskOrThrow(
                projectId,
                listId,
                taskId
        );

        task.updateDueDate(dueDate);

        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task move(
            Long projectId,
            Long sourceListId,
            Long taskId,
            Long targetListId,
            int position
    ) {
        Task task = getTaskOrThrow(
                projectId,
                sourceListId,
                taskId
        );

        TaskList targetList = taskListRepository.findById(targetListId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task list not found with id: " + targetListId
                        )
                );

        if (!targetList.getProject().getId().equals(projectId)) {
            throw new ResourceNotFoundException(
                    "Task list not found with id: "
                            + targetListId
                            + " in project: "
                            + projectId
            );
        }

        task.moveToList(targetList);
        task.moveToPosition(position);

        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task addLabel(
            Long projectId,
            Long listId,
            Long taskId,
            Long labelId
    ) {
        Task task = getTaskOrThrow(
                projectId,
                listId,
                taskId
        );

        Label label = labelRepository
                .findById(labelId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Label not found with id: " + labelId
                        )
                );

        if (!label.getProject().getId().equals(projectId)) {
            throw new ResourceNotFoundException(
                    "Label not found with id: "
                            + labelId
                            + " in project: "
                            + projectId
            );
        }

        task.addLabel(label);

        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task removeLabel(
            Long projectId,
            Long listId,
            Long taskId,
            Long labelId
    ) {
        Task task = getTaskOrThrow(
                projectId,
                listId,
                taskId
        );

        Label label = labelRepository
                .findById(labelId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Label not found with id: " + labelId
                        )
                );

        if (!label.getProject().getId().equals(projectId)) {
            throw new ResourceNotFoundException(
                    "Label not found with id: "
                            + labelId
                            + " in project: "
                            + projectId
            );
        }

        task.removeLabel(label);

        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public void delete(
            Long projectId,
            Long listId,
            Long taskId
    ) {
        getTaskOrThrow(
                projectId,
                listId,
                taskId
        );

        taskRepository.deleteById(taskId);
    }

    private Task getTaskOrThrow(
            Long projectId,
            Long listId,
            Long taskId
    ) {
        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task not found with id: " + taskId
                        )
                );

        Long actualListId = task.getList().getId();
        Long actualProjectId = task.getList().getProject().getId();

        if (!actualListId.equals(listId)
                || !actualProjectId.equals(projectId)) {
            throw new ResourceNotFoundException(
                    "Task not found with id: " +
                            taskId +
                            " in list: " +
                            listId +
                            " and project: " +
                            projectId
            );
        }

        return task;
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
}