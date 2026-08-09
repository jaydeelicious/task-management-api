package com.george.taskmanagement.db.adapter;

import com.george.taskmanagement.db.mapper.TaskListMapper;
import com.george.taskmanagement.db.repository.JpaTaskListRepository;
import com.george.taskmanagement.domain.TaskList;
import com.george.taskmanagement.repository.TaskListRepository;
import org.hibernate.annotations.DialectOverride;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskListRepositoryAdapter implements TaskListRepository {

    private final JpaTaskListRepository jpaTaskListRepository;
    private final  TaskListMapper taskListMapper;

    public TaskListRepositoryAdapter(
            JpaTaskListRepository jpaTaskListRepository,
            TaskListMapper taskListMapper
    ) {
        this.jpaTaskListRepository = jpaTaskListRepository;
        this.taskListMapper = taskListMapper;
    }

    @Override
    public TaskList save(TaskList taskList) {
        var entity = taskListMapper.toEntity(taskList);
        var savedEntity = jpaTaskListRepository.save(entity);

        return taskListMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<TaskList> findById(Long id) {
        return jpaTaskListRepository
                .findById(id)
                .map(taskListMapper::toDomain);
    }

    @Override
    public List<TaskList> findByProjectIdOrderByPosition(Long projectId) {
        return jpaTaskListRepository
                .findByProjectIdOrderByPosition(projectId)
                .stream()
                .map(taskListMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByProjectIdAndNameIgnoreCase(
            Long projectId,
            String name
    ) {
        return jpaTaskListRepository
                .existsByProjectIdAndNameIgnoreCase(projectId, name);
    }

    @Override
    public void deleteById(Long id) {
        jpaTaskListRepository.deleteById(id);
    }


}
