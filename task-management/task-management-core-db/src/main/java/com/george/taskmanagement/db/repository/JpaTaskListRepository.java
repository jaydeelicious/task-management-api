package com.george.taskmanagement.db.repository;

import com.george.taskmanagement.db.entity.TaskListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaTaskListRepository
        extends JpaRepository<TaskListEntity, Long> {
    List<TaskListEntity> findByProjectIdOrderByPosition(Long projectId);

    boolean existsByProjectIdAndNameIgnoreCase(Long projectId, String name);
}
