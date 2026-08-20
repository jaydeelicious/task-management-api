package com.george.taskmanagement.service;

import com.george.taskmanagement.domain.Project;
import com.george.taskmanagement.domain.TaskList;

import java.util.List;

public interface TaskListService {

    TaskList create(Long projectId,
                    String name,
                    int position
    );

    TaskList findById(
            Long projectId,
            Long taskListId
    );

    List<TaskList> findByProjectId(Long projectId);

    TaskList rename(
            Long projectId,
            Long taskListId,
            String name
    );

    TaskList move(
            Long projectId,
            Long taskListId,
            int position
    );

    void delete(
            Long projectId,
            Long taskListId
    );
}
