package com.george.taskmanagement.repository;

import com.george.taskmanagement.domain.Label;

import java.util.List;
import java.util.Optional;

public interface LabelRepository {

    Label save(Label label);

    Optional<Label> findById(Long id);

    List<Label> findByProjectId(Long projectId);

    boolean existsByProjectIdAndNameIgnoreCase(Long projectId, String name);

    void deleteById(Long id);
}
