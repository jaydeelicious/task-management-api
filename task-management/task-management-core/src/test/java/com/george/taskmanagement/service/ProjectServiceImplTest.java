package com.george.taskmanagement.service;

import com.george.taskmanagement.domain.Project;
import com.george.taskmanagement.exception.ResourceNotFoundException;
import com.george.taskmanagement.service.ProjectServiceImpl;
import com.george.taskmanagement.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void create_shouldSaveAndReturnProject() {
        Project savedProject = new Project(
                1L,
                "Test Project",
                "Desc for test project",
                Instant.now(),
                Instant.now()
        );

        when(projectRepository.save(any(Project.class)))
                .thenReturn(savedProject);

        Project result = projectService.create(
                "Test Project",
                "Desc for test project"
        );

        assertEquals(1L, result.getId());
        assertEquals("Test Project", result.getName());
        assertEquals("Desc for test project", result.getDescription());

        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void findAll_shouldReturnProjects() {
        List<Project> projects = List.of(
                existingProject(1L, "Project A"),
                existingProject(2L, "Project B")
        );

        when(projectRepository.findAll())
                .thenReturn(projects);

        List<Project> result = projectService.findAll();

        assertEquals(2, result.size());
        assertEquals("Project A", result.get(0).getName());
        assertEquals("Project B", result.get(1).getName());

        verify(projectRepository).findAll();
    }

    @Test
    void findById_shouldReturnProject_whenProjectExists() {
        Project project = existingProject(1L, "Project A");

        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(project));

        Project result = projectService.findById(1L);

        assertSame(project, result);
        verify(projectRepository).findById(1L);
    }

    @Test
    void findById_shouldThrow_whenProjectDoesNotExist() {
        when(projectRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> projectService.findById(99L)
        );

        verify(projectRepository).findById(99L);
    }

    @Test
    void update_shouldUpdateAndSaveProject() {
        Project existing = existingProject(
                1L,
                "Old name"
        );

        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(projectRepository.save(existing))
                .thenReturn(existing);

        Project result = projectService.update(
                1L,
                "New name",
                "New description"
        );

        assertEquals("New name", result.getName());
        assertEquals("New description", result.getDescription());

        verify(projectRepository).findById(1L);
        verify(projectRepository).save(existing);
    }

    @Test
    void delete_shouldDeleteProject_whenProjectExists() {
        Project project = existingProject(1L, "Project A");

        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(project));

        projectService.delete(1L);

        verify(projectRepository).findById(1L);
        verify(projectRepository).deleteById(1L);
    }

    @Test
    void delete_shouldThrow_whenProjectDoesNotExist() {
        when(projectRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> projectService.delete(99L)
        );

        verify(projectRepository).findById(99L);
        verify(projectRepository, never()).deleteById(anyLong());
    }

    private Project existingProject(Long id, String name) {
        Instant now = Instant.now();

        return new Project(
                id,
                name,
                "Description",
                now,
                now
        );
    }




}
