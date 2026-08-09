package com.george.taskmanagement.db.mapper;

import com.george.taskmanagement.db.entity.ProjectMemberEntity;
import com.george.taskmanagement.domain.ProjectMember;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring",
        uses = {
                ProjectMapper.class,
                UserMapper.class
        }
)
public abstract class ProjectMemberMapper {

    @Autowired
    protected ProjectMapper projectMapper;

    @Autowired
    protected UserMapper userMapper;

    public abstract ProjectMemberEntity toEntity(ProjectMember projectMember);

    public ProjectMember toDomain(ProjectMemberEntity entity) {
        if (entity == null)
            return null;

        return new ProjectMember(
                entity.getId(),
                projectMapper.toDomain(entity.getProject()),
                userMapper.toDomain(entity.getUser()),
                entity.getRole(),
                entity.getJoinedAt()
        );
    }
}