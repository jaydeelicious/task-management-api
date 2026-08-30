package com.george.taskmanagement.db.repository;

import com.george.taskmanagement.db.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProjectRepository
    extends JpaRepository<ProjectEntity, Long> {
}

