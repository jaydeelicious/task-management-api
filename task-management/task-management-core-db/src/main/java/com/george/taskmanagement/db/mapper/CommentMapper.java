package com.george.taskmanagement.db.mapper;

import com.george.taskmanagement.db.entity.CommentEntity;
import com.george.taskmanagement.domain.Comment;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring",
        uses = {
                TaskMapper.class,
                UserMapper.class
        }
)
public abstract class CommentMapper {

    @Autowired
    protected TaskMapper taskMapper;

    @Autowired
    protected UserMapper userMapper;

    public abstract CommentEntity toEntity(Comment comment);

    public Comment toDomain(CommentEntity entity) {
        if (entity == null)
            return null;

        return new Comment(
                entity.getId(),
                taskMapper.toDomain(entity.getTask()),
                userMapper.toDomain(entity.getAuthor()),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}