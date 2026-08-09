package com.george.taskmanagement.db.adapter;

import com.george.taskmanagement.db.mapper.TaskMemberMapper;
import com.george.taskmanagement.db.repository.JpaTaskMemberRepository;
import com.george.taskmanagement.domain.TaskMember;
import com.george.taskmanagement.repository.TaskMemberRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskMemberRepositoryAdapter implements TaskMemberRepository {

    private final JpaTaskMemberRepository jpaTaskMemberRepository;
    private final TaskMemberMapper taskMemberMapper;

    public TaskMemberRepositoryAdapter(
            JpaTaskMemberRepository jpaTaskMemberRepository,
            TaskMemberMapper taskMemberMapper
    ) {
        this.jpaTaskMemberRepository = jpaTaskMemberRepository;
        this.taskMemberMapper = taskMemberMapper;
    }

    @Override
    public TaskMember save(TaskMember taskMember) {
        var entity = taskMemberMapper.toEntity(taskMember);
        var savedEntity = jpaTaskMemberRepository.save(entity);

        return taskMemberMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<TaskMember> findById(Long id) {
        return jpaTaskMemberRepository
                .findById(id)
                .map(taskMemberMapper::toDomain);
    }

    @Override
    public List<TaskMember> findByTaskId(Long taskId) {
        return jpaTaskMemberRepository
                .findByTaskId(taskId)
                .stream()
                .map(taskMemberMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<TaskMember> findByTaskIdAndUserId(
            Long taskId,
            Long userId
    ) {
        return jpaTaskMemberRepository
                .findByTaskIdAndUserId(taskId, userId)
                .map(taskMemberMapper::toDomain);
    }

    @Override
    public boolean existsByTaskIdAndUserId(
            Long taskId,
            Long userId
    ) {
        return jpaTaskMemberRepository
                .existsByTaskIdAndUserId(taskId, userId);
    }

    @Override
    public void deleteById(Long id) {
        jpaTaskMemberRepository.deleteById(id);
    }




}
