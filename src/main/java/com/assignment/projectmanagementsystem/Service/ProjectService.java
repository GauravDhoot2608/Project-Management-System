package com.assignment.projectmanagementsystem.Service;

import com.assignment.projectmanagementsystem.Dto.ProjectDto;
import com.assignment.projectmanagementsystem.Exception.ProjectNotFoundException;
import com.assignment.projectmanagementsystem.Model.Project;
import com.assignment.projectmanagementsystem.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project createProject(ProjectDto projectDto) {

        Project project = Project.builder()
                .name(projectDto.getName())
                .description(projectDto.getDescription())
                .startDate(projectDto.getStartDate())
                .endDate(projectDto.getEndDate())
                .build();

        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects;
    }

    public Project getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with the given ID."));
        return project;
    }

    public Project updateProject(Long id, ProjectDto projectDto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with the given ID."));

        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setStartDate(projectDto.getStartDate());
        project.setEndDate(projectDto.getEndDate());

        return projectRepository.save(project);
    }

    public String deleteProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with the given ID."));
        projectRepository.deleteById(id);
        return "Project has successfully deleted";
    }
}
