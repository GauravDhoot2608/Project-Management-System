package com.assignment.projectmanagementsystem;

import com.assignment.projectmanagementsystem.Controller.ProjectController;
import com.assignment.projectmanagementsystem.Dto.ProjectDto;
import com.assignment.projectmanagementsystem.Model.Project;
import com.assignment.projectmanagementsystem.Service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(ProjectController.class)
@AutoConfigureMockMvc
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @Test
    void testCreateProject() throws Exception {
        ProjectDto projectDto = new ProjectDto("Test Project", "Description", LocalDate.now(), LocalDate.now().plusDays(7));
        Project project = new Project(1L, "Test Project", "Description", LocalDate.now(), LocalDate.now().plusDays(7));

        when(projectService.createProject(any(ProjectDto.class))).thenReturn(project);

        mockMvc.perform(MockMvcRequestBuilders.post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Project\",\"description\":\"Description\",\"startDate\":\"2024-03-10\",\"endDate\":\"2024-03-17\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetAllProjects() throws Exception {
        Project project1 = new Project(1L, "Test Project 1", "Description 1", LocalDate.now(), LocalDate.now().plusDays(7));
        Project project2 = new Project(2L, "Test Project 2", "Description 2", LocalDate.now(), LocalDate.now().plusDays(7));
        List<Project> projects = Arrays.asList(project1, project2);

        when(projectService.getAllProjects()).thenReturn(projects);

        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetProjectById() throws Exception {
        Project project = new Project(1L, "Test Project", "Description", LocalDate.now(), LocalDate.now().plusDays(7));

        when(projectService.getProjectById(1L)).thenReturn(project);

        mockMvc.perform(MockMvcRequestBuilders.get("/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testUpdateProject() throws Exception {
        ProjectDto projectDto = new ProjectDto("Updated Project", "Updated Description", LocalDate.now(), LocalDate.now().plusDays(14));
        Project updatedProject = new Project(1L, "Updated Project", "Updated Description", LocalDate.now(), LocalDate.now().plusDays(14));

        when(projectService.updateProject(1L, projectDto)).thenReturn(updatedProject);

        mockMvc.perform(MockMvcRequestBuilders.put("/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Project\",\"description\":\"Updated Description\",\"startDate\":\"2024-03-10\",\"endDate\":\"2024-03-24\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteProjectById() throws Exception {
        when(projectService.deleteProjectById(1L)).thenReturn("Project deleted successfully");

        mockMvc.perform(MockMvcRequestBuilders.delete("/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

