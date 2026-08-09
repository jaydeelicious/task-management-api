package com.george.taskmanagement.repository;

import com.george.taskmanagement.domain.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    Optional<Task> findById(Long id);

    List<Task> findByListIdOrderByPosition(Long listId);

    void deleteById(Long id);
}
