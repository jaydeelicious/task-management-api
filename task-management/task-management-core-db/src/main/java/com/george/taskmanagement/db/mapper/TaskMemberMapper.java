package com.george.taskmanagement.db.mapper;

import com.george.taskmanagement.db.entity.TaskMemberEntity;
import com.george.taskmanagement.domain.TaskMember;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring",
        uses = {
                TaskMapper.class,
                UserMapper.class
        }
)
public abstract class TaskMemberMapper {

    @Autowired
    protected TaskMapper taskMapper;

    @Autowired
    protected UserMapper userMapper;

    public abstract TaskMemberEntity toEntity(TaskMember taskMember);

    public TaskMember toDomain(TaskMemberEntity entity) {
        if (entity == null)
            return null;

        return new TaskMember(
                entity.getId(),
                taskMapper.toDomain(entity.getTask()),
                userMapper.toDomain(entity.getUser()),
                entity.getAddedAt()
        );
    }
}