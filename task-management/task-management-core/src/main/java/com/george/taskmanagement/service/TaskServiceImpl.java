package com.george.taskmanagement.service;

import com.george.taskmanagement.domain.Task;
import com.george.taskmanagement.domain.TaskList;
import com.george.taskmanagement.domain.TaskPriority;
import com.george.taskmanagement.exception.ResourceNotFoundException;
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

    public TaskServiceImpl(
            TaskRepository taskRepository,
            TaskListRepository taskListRepository
    ) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
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
    public Task findById(Long id) {
        return getTaskOrThrow(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findByListId(Long listId) {
        getTaskListOrThrow(listId);

        return taskRepository
                .findByListIdOrderByPosition(listId);
    }

    @Override
    public Task updateTitle(Long id, String title) {
        Task task = getTaskOrThrow(id);
        task.updateTitle(title);

        return taskRepository.save(task);
    }

    @Override
    public Task updateDescription(
            Long id,
            String description
    ) {
        Task task = getTaskOrThrow(id);
        task.updateDescription(description);

        return taskRepository.save(task);
    }

    @Override
    public Task updatePriority(
            Long id,
            TaskPriority priority
    ) {
        Task task = getTaskOrThrow(id);
        task.updatePriority(priority);

        return taskRepository.save(task);
    }

    @Override
    public Task updateDueDate(
            Long id,
            LocalDate dueDate
    ) {
        Task task = getTaskOrThrow(id);
        task.updateDueDate(dueDate);

        return taskRepository.save(task);
    }

    @Override
    public Task move(
            Long id,
            Long listId,
            int position
    ) {
        Task task = getTaskOrThrow(id);
        TaskList list = getTaskListOrThrow(listId);

        task.moveToList(list);
        task.moveToPosition(position);

        return taskRepository.save(task);
    }

    @Override
    public void delete(Long id) {
        getTaskOrThrow(id);
        taskRepository.deleteById(id);
    }

    private Task getTaskOrThrow(Long id) {
        return taskRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task not found with id: " + id
                        )
                );
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