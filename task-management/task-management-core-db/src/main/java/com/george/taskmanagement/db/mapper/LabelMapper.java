package com.george.taskmanagement.db.mapper;

import com.george.taskmanagement.db.entity.LabelEntity;
import com.george.taskmanagement.domain.Label;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring",
        uses = ProjectMapper.class
)
public abstract class LabelMapper {

    @Autowired
    protected ProjectMapper projectMapper;

    public abstract LabelEntity toEntity(Label label);

    public Label toDomain(LabelEntity entity) {
        if (entity == null)
            return null;

        return new Label(
                entity.getId(),
                projectMapper.toDomain(entity.getProject()),
                entity.getName(),
                entity.getColor(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}