package com.george.taskmanagement.db.mapper;

import com.george.taskmanagement.db.entity.TaskEntity;
import com.george.taskmanagement.domain.Label;
import com.george.taskmanagement.domain.Task;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
                TaskListMapper.class,
                LabelMapper.class
        }
)
public abstract class TaskMapper {

    @Autowired
    protected TaskListMapper taskListMapper;

    @Autowired
    protected LabelMapper labelMapper;

    public abstract TaskEntity toEntity(Task task);

    public Task toDomain(TaskEntity entity) {
        if (entity == null) {
            return null;
        }

        List<Label> labels = entity.getLabels()
                .stream()
                .map(labelMapper::toDomain)
                .toList();

        return new Task(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                taskListMapper.toDomain(entity.getList()),
                entity.getPosition(),
                entity.getPriority(),
                entity.getDueDate(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                labels
        );
    }
}
