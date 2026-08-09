package com.george.taskmanagement.db.adapter;

import com.george.taskmanagement.db.mapper.CommentMapper;
import com.george.taskmanagement.db.repository.JpaCommentRepository;
import com.george.taskmanagement.domain.Comment;
import com.george.taskmanagement.repository.CommentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public class CommentRepositoryAdapter implements CommentRepository {

    private final JpaCommentRepository jpaCommentRepository;
    private final CommentMapper commentMapper;

    public CommentRepositoryAdapter(
            JpaCommentRepository jpaCommentRepository,
            CommentMapper commentMapper
    ) {
        this.jpaCommentRepository = jpaCommentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public Comment save(Comment comment) {
        var entity = commentMapper.toEntity(comment);
        var savedEntity = jpaCommentRepository.save(entity);

        return commentMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return jpaCommentRepository
                .findById(id)
                .map(commentMapper::toDomain);
    }

    @Override
    public List<Comment> findByTaskIdOrderByCreatedAt(Long taskId) {
        return jpaCommentRepository
                .findByTaskIdOrderByCreatedAt(taskId)
                .stream()
                .map(commentMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaCommentRepository.deleteById(id);
    }
}