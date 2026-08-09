package com.george.taskmanagement.db.adapter;

import com.george.taskmanagement.db.mapper.LabelMapper;
import com.george.taskmanagement.db.repository.JpaLabelRepository;
import com.george.taskmanagement.domain.Label;
import com.george.taskmanagement.repository.LabelRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LabelRepositoryAdapter implements LabelRepository {

    private final JpaLabelRepository jpaLabelRepository;
    private final LabelMapper labelMapper;

    public LabelRepositoryAdapter(
            JpaLabelRepository jpaLabelRepository,
            LabelMapper labelMapper
    ) {
        this.jpaLabelRepository = jpaLabelRepository;
        this.labelMapper = labelMapper;
    }

    @Override
    public Label save(Label label) {
        var entity = labelMapper.toEntity(label);
        var savedEntity = jpaLabelRepository.save(entity);

        return labelMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Label> findById(Long id) {
        return jpaLabelRepository
                .findById(id)
                .map(labelMapper::toDomain);
    }

    @Override
    public List<Label> findByProjectId(Long projectId) {
        return jpaLabelRepository
                .findByProjectId(projectId)
                .stream()
                .map(labelMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByProjectIdAndNameIgnoreCase(
            Long projectId,
            String name
    ) {
        return jpaLabelRepository
                .existsByProjectIdAndNameIgnoreCase(projectId, name);
    }

    @Override
    public void deleteById(Long id) {
        jpaLabelRepository.deleteById(id);
    }
}
