package com.george.taskmanagement.service;

import com.george.taskmanagement.domain.Project;
import com.george.taskmanagement.domain.TaskList;

import java.util.List;

public interface TaskListService {

    TaskList create(Long id,
                    String name,
                    int position
    );

    TaskList findById(Long id);

    List<TaskList> findByProjectId(Long projectId);

    TaskList rename(
            Long id,
            String name
    );

    TaskList move(
            Long id,
            int position
    );

    void delete(Long id);
}
