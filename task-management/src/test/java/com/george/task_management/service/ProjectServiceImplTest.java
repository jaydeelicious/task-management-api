package com.george.task_management.service;

import com.george.task_management.dto.ProjectCreateRequest;
import com.george.task_management.dto.ProjectResponse;
import com.george.task_management.entity.Project;
import com.george.task_management.exception.ProjectNotFoundException;
import com.george.task_management.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    private ProjectServiceImpl projectService;

    @BeforeEach
    void setUp() {
        projectService = new ProjectServiceImpl(projectRepository);
    }

    @Test
    void shouldReturnProjectWhenProjectExists() {
        Project project = new Project(
                "Task Management App",
                "A Jira-style application"
        );

        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(project));

        ProjectResponse response = projectService.findById(1L);

        assertEquals("Task Management App", response.name());
        assertEquals("A Jira-style application", response.description());
    }

    @Test
    void shouldThrowExceptionWhenProjectDoesNotExist() {
        when(projectRepository.findById(99L))
                .thenReturn(Optional.empty());

        ProjectNotFoundException exception =
                assertThrows(
                        ProjectNotFoundException.class,
                        () -> projectService.findById(99L)
                );

        assertEquals(
                "Project with id 99 was not found",
                exception.getMessage()
        );
    }

    @Test
    void shouldCreateProject() {
        ProjectCreateRequest request = new ProjectCreateRequest(
                "Task Management App",
                "A Jira-style application"
        );

        when(projectRepository.save(any(Project.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ProjectResponse response = projectService.create(request);

        assertEquals("Task Management App", response.name());
        assertEquals("A Jira-style application", response.description());

        verify(projectRepository).save(any(Project.class));
    }
}
