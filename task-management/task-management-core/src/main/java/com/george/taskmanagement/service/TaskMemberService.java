package com.george.taskmanagement.service;

import com.george.taskmanagement.domain.TaskMember;

import java.util.List;

public interface TaskMemberService {

    TaskMember addMember(Long taskId, Long userId);

    List<TaskMember> findByTaskId(Long taskId);

    void removeMember(Long taskId, Long userId);
}
