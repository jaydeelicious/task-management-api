package com.george.taskmanagement.db.repository;

import com.george.taskmanagement.db.entity.TaskMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaTaskMemberRepository
        extends JpaRepository<TaskMemberEntity, Long> {

    List<TaskMemberEntity> findByTaskId(Long taskId);

    Optional<TaskMemberEntity> findByTaskIdAndUserId(
            Long taskId,
            Long userId
    );

    boolean existsByTaskIdAndUserId(
            Long taskId,
            Long userId
    );
}