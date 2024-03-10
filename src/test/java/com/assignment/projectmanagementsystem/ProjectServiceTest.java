package com.assignment.projectmanagementsystem;

import com.assignment.projectmanagementsystem.Dto.ProjectDto;
import com.assignment.projectmanagementsystem.Exception.ProjectNotFoundException;
import com.assignment.projectmanagementsystem.Model.Project;
import com.assignment.projectmanagementsystem.Repository.ProjectRepository;
import com.assignment.projectmanagementsystem.Service.ProjectService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void createProject_ValidInput_ReturnsSavedProject() {
        ProjectDto projectDto = new ProjectDto("Project Name", "Project Description", LocalDate.parse("2024-03-10"), LocalDate.parse("2024-03-20"));
        Project project = new Project(1L, "Project Name", "Project Description", LocalDate.parse("2024-03-10"), LocalDate.parse("2024-03-20"));

        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project savedProject = projectService.createProject(projectDto);

        assertEquals(project.getName(), savedProject.getName());
        assertEquals(project.getDescription(), savedProject.getDescription());
        assertEquals(project.getStartDate(), savedProject.getStartDate());
        assertEquals(project.getEndDate(), savedProject.getEndDate());
    }

    @Test
    void getAllProjects_ReturnsListOfProjects() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1L, "Project 1", "Description 1", LocalDate.parse("2024-03-15"), LocalDate.parse("2024-03-20")));
        projects.add(new Project(2L, "Project 2", "Description 2", LocalDate.parse("2024-03-15"), LocalDate.parse("2024-03-25")));

        when(projectRepository.findAll()).thenReturn(projects);

        List<Project> result = projectService.getAllProjects();

        assertEquals(2, result.size());
    }

    @Test
    void getProjectById_ExistingId_ReturnsProject() {
        Long projectId = 1L;
        Project project = new Project(projectId, "Project Name", "Project Description", LocalDate.parse("2024-03-10"), LocalDate.parse("2024-03-20"));

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        Project result = projectService.getProjectById(projectId);

        assertNotNull(result);
        assertEquals(project.getName(), result.getName());
        assertEquals(project.getDescription(), result.getDescription());
        assertEquals(project.getStartDate(), result.getStartDate());
        assertEquals(project.getEndDate(), result.getEndDate());
    }

    @Test
    void getProjectById_NonExistingId_ThrowsProjectNotFoundException() {
        Long nonExistingId = 999L;

        when(projectRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> projectService.getProjectById(nonExistingId));
    }

    @Test
    void updateProject_ExistingId_ReturnsUpdatedProject() {
        Long projectId = 1L;
        ProjectDto projectDto = new ProjectDto("Updated Name", "Updated Description", LocalDate.parse("2024-03-11"), LocalDate.parse("2024-03-21"));
        Project existingProject = new Project(projectId, "Project Name", "Project Description", LocalDate.parse("2024-03-10"), LocalDate.parse("2024-03-20"));

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));
        when(projectRepository.save(any(Project.class))).thenReturn(existingProject);

        Project updatedProject = projectService.updateProject(projectId, projectDto);

        assertNotNull(updatedProject);
        assertEquals(projectDto.getName(), updatedProject.getName());
        assertEquals(projectDto.getDescription(), updatedProject.getDescription());
        assertEquals(projectDto.getStartDate(), updatedProject.getStartDate());
        assertEquals(projectDto.getEndDate(), updatedProject.getEndDate());
    }

    @Test
    void updateProject_NonExistingId_ThrowsProjectNotFoundException() {
        Long nonExistingId = 999L;
        ProjectDto projectDto = new ProjectDto("Updated Name", "Updated Description", LocalDate.parse("2024-03-11"), LocalDate.parse("2024-03-21"));

        when(projectRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> projectService.updateProject(nonExistingId, projectDto));
    }

    @Test
    void deleteProjectById_ExistingId_ReturnsSuccessMessage() {
        Long projectId = 1L;
        Project existingProject = new Project(projectId, "Project Name", "Project Description", LocalDate.parse("2024-03-10"), LocalDate.parse("2024-03-20"));

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));

        String result = projectService.deleteProjectById(projectId);

        assertEquals("Project has successfully deleted", result);
        verify(projectRepository, times(1)).deleteById(projectId);
    }

    @Test
    void deleteProjectById_NonExistingId_ThrowsProjectNotFoundException() {
        Long nonExistingId = 999L;

        when(projectRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> projectService.deleteProjectById(nonExistingId));
        verify(projectRepository, never()).deleteById(nonExistingId);
    }
}
