package com.george.taskmanagement.repository;

import com.george.taskmanagement.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> findById(Long id);

    List<Comment> findByTaskIdOrderByCreatedAt(Long taskId);

    void deleteById(Long id);
}
