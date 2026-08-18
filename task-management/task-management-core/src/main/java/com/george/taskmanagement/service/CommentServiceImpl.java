package com.george.taskmanagement.service;


import com.george.taskmanagement.domain.Comment;
import com.george.taskmanagement.domain.Task;
import com.george.taskmanagement.domain.User;
import com.george.taskmanagement.exception.InvalidOperationException;
import com.george.taskmanagement.exception.ResourceNotFoundException;
import com.george.taskmanagement.repository.CommentRepository;
import com.george.taskmanagement.repository.ProjectMemberRepository;
import com.george.taskmanagement.repository.TaskRepository;
import com.george.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public CommentServiceImpl(
            CommentRepository commentRepository,
            TaskRepository taskRepository,
            UserRepository userRepository,
            ProjectMemberRepository projectMemberRepository
    ) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectMemberRepository = projectMemberRepository;
    }

    @Override
    public Comment create(
            Long taskId,
            Long authorId,
            String content
    ) {
        Task task = getTaskOrThrow(taskId);
        User author = getUserOrThrow(authorId);

        Long projectId =
                task.getList().getProject().getId();

        if (!projectMemberRepository
                .existsByProjectIdAndUserId(projectId, authorId)) {
            throw new InvalidOperationException(
                    "Only project members can comment on tasks"
            );
        }

        return commentRepository.save(
                new Comment(task, author, content)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Comment findById(Long id) {
        return getCommentOrThrow(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findByTaskId(Long taskId) {
        getTaskOrThrow(taskId);

        return commentRepository
                .findByTaskIdOrderByCreatedAt(taskId);
    }

    @Override
    public Comment update(Long id, String content) {
        Comment comment = getCommentOrThrow(id);

        comment.updateContent(content);

        return commentRepository.save(comment);
    }

    @Override
    public void delete(Long id) {
        getCommentOrThrow(id);
        commentRepository.deleteById(id);
    }

    private Comment getCommentOrThrow(Long id) {
        return commentRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Comment not found with id: " + id
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

    private User getUserOrThrow(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id
                        )
                );
    }
}
