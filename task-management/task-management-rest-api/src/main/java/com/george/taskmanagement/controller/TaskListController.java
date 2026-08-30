package com.george.taskmanagement.controller;

import com.george.taskmanagement.domain.TaskList;
import com.george.taskmanagement.dto.MoveTaskListRequest;
import com.george.taskmanagement.dto.RenameTaskListRequest;
import com.george.taskmanagement.dto.TaskListCreateRequest;
import com.george.taskmanagement.dto.TaskListResponse;
import com.george.taskmanagement.service.TaskListService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/lists")
public class TaskListController {

    private final TaskListService taskListService;

    public TaskListController(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

    @PostMapping
    public ResponseEntity<TaskListResponse> createTaskList(
            @PathVariable Long projectId,
            @Valid @RequestBody TaskListCreateRequest request
    ) {
        TaskList taskList = taskListService.create(
                projectId,
                request.name(),
                request.position()
        );

        TaskListResponse response = toResponse(taskList);

        URI location = URI.create(
                "/api/projects/" +
                        projectId +
                        "/lists/" +
                        response.id()
        );

        return ResponseEntity
                .created(location)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<TaskListResponse>> getTaskLists(
            @PathVariable Long projectId
    ) {
        List<TaskListResponse> taskLists =
                taskListService.findByProjectId(projectId)
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return ResponseEntity.ok(taskLists);
    }

    @GetMapping("/{listId}")
    public ResponseEntity<TaskListResponse> getTaskList(
            @PathVariable Long projectId,
            @PathVariable Long listId
    ) {
        TaskList taskList = taskListService.findById(projectId, listId);

        return ResponseEntity.ok(toResponse(taskList));
    }

    @PatchMapping("/{listId}/name")
    public ResponseEntity<TaskListResponse> renameTaskList(
            @PathVariable Long projectId,
            @PathVariable Long listId,
            @Valid @RequestBody RenameTaskListRequest request
    ) {
        TaskList taskList = taskListService.rename(
                projectId,
                listId,
                request.name()
        );

        return ResponseEntity.ok(toResponse(taskList));
    }

    @PatchMapping("/{listId}/position")
    public ResponseEntity<TaskListResponse> moveTaskList(
            @PathVariable Long projectId,
            @PathVariable Long listId,
            @Valid @RequestBody MoveTaskListRequest request
    ) {
        TaskList taskList = taskListService.move(
                projectId,
                listId,
                request.position()
        );

        return ResponseEntity.ok(toResponse(taskList));
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<Void> deleteTaskList(
            @PathVariable Long projectId,
            @PathVariable Long listId
    ) {
        taskListService.delete(projectId, listId);

        return ResponseEntity.noContent().build();
    }

    private TaskListResponse toResponse(TaskList taskList) {
        return new TaskListResponse(
                taskList.getId(),
                taskList.getProject().getId(),
                taskList.getName(),
                taskList.getPosition(),
                taskList.getCreatedAt(),
                taskList.getUpdatedAt()
        );
    }
}
