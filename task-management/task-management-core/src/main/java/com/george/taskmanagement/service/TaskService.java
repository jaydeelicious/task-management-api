package com.george.taskmanagement.service;

import com.george.taskmanagement.domain.Task;
import com.george.taskmanagement.domain.TaskPriority;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {

    Task create(
            Long listId,
            String title,
            String description,
            int position,
            TaskPriority priority,
            LocalDate dueDate
    );

    Task findById(Long id);

    List<Task> findByListId(Long listId);

    Task updateTitle(Long id, String title);

    Task updateDescription(Long id, String description);

    Task updatePriority(Long id, TaskPriority priority);

    Task updateDueDate(Long id, LocalDate dueDate);

    Task move(
            Long id,
            Long listId,
            int position
    );

    Task addLabel(Long taskId, Long labelId);

    Task removeLabel(Long taskId, Long labelId);

    void delete(Long id);
}
