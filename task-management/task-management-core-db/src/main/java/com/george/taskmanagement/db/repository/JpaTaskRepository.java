package com.george.taskmanagement.db.repository;

import com.george.taskmanagement.db.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaTaskRepository
        extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findByListIdOrderByPosition(Long listId);
}
