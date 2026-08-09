package com.george.taskmanagement.db.repository;

import com.george.taskmanagement.db.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCommentRepository
        extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByTaskIdOrderByCreatedAt(Long taskId);
}

