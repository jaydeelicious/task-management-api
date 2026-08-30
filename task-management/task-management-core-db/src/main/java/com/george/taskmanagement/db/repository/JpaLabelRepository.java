package com.george.taskmanagement.db.repository;

import com.george.taskmanagement.db.entity.LabelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaLabelRepository
        extends JpaRepository<LabelEntity, Long> {

    List<LabelEntity> findByProjectId(Long projectId);

    boolean existsByProjectIdAndNameIgnoreCase(Long projectId, String name);
}
