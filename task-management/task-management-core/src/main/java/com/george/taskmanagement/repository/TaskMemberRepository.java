package com.george.taskmanagement.repository;

import com.george.taskmanagement.domain.TaskMember;

import java.util.List;
import java.util.Optional;

public interface TaskMemberRepository {

    TaskMember save(TaskMember taskMember);

    Optional<TaskMember> findById(Long id);

    List<TaskMember> findByTaskId(Long taskId);

    Optional<TaskMember> findByTaskIdAndUserId(
            Long taskId,
            Long userId
    );

    boolean existsByTaskIdAndUserId(
            Long taskId,
            Long userId
    );

    void deleteById(Long id);
}
