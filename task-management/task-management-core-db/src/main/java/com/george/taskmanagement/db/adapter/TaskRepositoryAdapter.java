package com.george.taskmanagement.db.adapter;

import com.george.taskmanagement.db.mapper.TaskMapper;
import com.george.taskmanagement.db.repository.JpaTaskRepository;
import com.george.taskmanagement.domain.Task;
import com.george.taskmanagement.repository.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryAdapter implements TaskRepository {

    private final JpaTaskRepository jpaTaskRepository;
    private final TaskMapper taskMapper;

    public TaskRepositoryAdapter(
            JpaTaskRepository jpaTaskRepository,
            TaskMapper taskMapper
    ) {
        this.jpaTaskRepository = jpaTaskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public Task save(Task task) {
        var entity = taskMapper.toEntity(task);
        var savedEntity = jpaTaskRepository.save(entity);

        return taskMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return jpaTaskRepository
                .findById(id)
                .map(taskMapper::toDomain);
    }

    @Override
    public List<Task> findByListIdOrderByPosition(Long listId) {
        return jpaTaskRepository
                .findByListIdOrderByPosition(listId)
                .stream()
                .map(taskMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaTaskRepository.deleteById(id);
    }
}
