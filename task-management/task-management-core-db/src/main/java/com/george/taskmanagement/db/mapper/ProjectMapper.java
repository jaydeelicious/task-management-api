package com.george.taskmanagement.db.mapper;

import com.george.taskmanagement.db.entity.ProjectEntity;
import com.george.taskmanagement.domain.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectEntity toEntity(Project project);


    default Project toDomain(ProjectEntity entity) {
        if (entity == null)
            return null;

        return new Project(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
