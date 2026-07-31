package com.george.task_management.exception;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(Long projectId) {
        super("Project with id " + projectId + " was not found");
    }
}
