package com.george.taskmanagement.service;

import com.george.taskmanagement.domain.Comment;

import java.util.List;

public interface CommentService {

    Comment create(
            Long taskId,
            Long authorId,
            String content
    );

    Comment findById(Long id);

    List<Comment> findByTaskId (Long taskId);

    Comment update(Long id, String content);

    void delete(Long id);
}
