package com.george.taskmanagement.db.mapper;

import com.george.taskmanagement.db.entity.TaskListEntity;
import com.george.taskmanagement.domain.TaskList;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring",
        uses = ProjectMapper.class
)
public abstract class TaskListMapper {

    @Autowired
    protected ProjectMapper projectMapper;

    public abstract TaskListEntity toEntity(TaskList taskList);

    public TaskList toDomain(TaskListEntity entity) {
        if (entity == null)
            return null;

        return new TaskList(
                entity.getId(),
                projectMapper.toDomain(entity.getProject()),
                entity.getName(),
                entity.getPosition(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
