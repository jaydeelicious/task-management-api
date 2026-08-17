package com.george.taskmanagement.service;

import com.george.taskmanagement.domain.Task;
import com.george.taskmanagement.domain.TaskMember;
import com.george.taskmanagement.domain.User;
import com.george.taskmanagement.exception.DuplicateResourceException;
import com.george.taskmanagement.exception.ResourceNotFoundException;
import com.george.taskmanagement.repository.ProjectMemberRepository;
import com.george.taskmanagement.repository.TaskMemberRepository;
import com.george.taskmanagement.repository.TaskRepository;
import com.george.taskmanagement.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class TaskMemberServiceImpl implements TaskMemberService {

    private final TaskMemberRepository taskMemberRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskMemberServiceImpl(
            TaskMemberRepository taskMemberRepository,
            ProjectMemberRepository projectMemberRepository,
            TaskRepository taskRepository,
            UserRepository userRepository
    ) {
        this.taskMemberRepository = taskMemberRepository;
        this.projectMemberRepository = projectMemberRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TaskMember addMember(
            Long taskId,
            Long userId
    ) {
        Task task = getTaskOrThrow(taskId);
        User user = getUserOrThrow(userId);

        Long projectId =
                task.getList().getProject().getId();

        if (!projectMemberRepository
                .existsByProjectIdAndUserId(projectId, userId)) {
            throw new IllegalArgumentException(
                    "User must belong to the project before being added to a task"
            );
        }

        if (taskMemberRepository
                .existsByTaskIdAndUserId(taskId, userId)) {
            throw new DuplicateResourceException(
                    "User is already a member of this task"
            );
        }

        return taskMemberRepository.save(
                new TaskMember(task, user)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskMember> findByTaskId(Long taskId) {
        getTaskOrThrow(taskId);

        return taskMemberRepository.findByTaskId(taskId);
    }

    @Override
    public void removeMember(
            Long taskId,
            Long userId
    ) {
        TaskMember member = taskMemberRepository
                .findByTaskIdAndUserId(taskId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task member not found"
                        )
                );

        taskMemberRepository.deleteById(member.getId());
    }

    private User getUserOrThrow(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id
                        )
                );
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
}
