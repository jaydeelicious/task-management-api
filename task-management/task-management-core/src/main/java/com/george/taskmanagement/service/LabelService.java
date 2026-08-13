package com.george.taskmanagement.service;

import com.george.taskmanagement.domain.Label;

import java.util.List;

public interface LabelService {

    Label create(Long projectId,
                 String name,
                 String color
    );

    Label findById(Long id);

    List<Label> findByProjectId(Long projectId);

    Label rename(Long id, String name);

    Label changeColor(Long id, String color);

    void delete(Long id);
}
