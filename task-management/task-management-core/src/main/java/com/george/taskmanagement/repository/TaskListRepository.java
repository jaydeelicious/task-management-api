package com.george.taskmanagement.repository;

import com.george.taskmanagement.domain.TaskList;

import java.util.List;
import java.util.Optional;

public interface TaskListRepository {

    TaskList save(TaskList taskList);

    Optional<TaskList> findById(Long id);

    List<TaskList> findByProjectIdOrderByPosition(Long projectId);

    boolean existsByProjectIdAndNameIgnoreCase(Long projectId, String name);

    void deleteById(Long id);
}