package com.assignment.projectmanagementsystem.Controller;

import com.assignment.projectmanagementsystem.Dto.ProjectDto;
import com.assignment.projectmanagementsystem.Model.Project;
import com.assignment.projectmanagementsystem.Service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity createProject(@Valid @RequestBody ProjectDto projectDto){
        Project project = projectService.createProject(projectDto);
        return new ResponseEntity(project , HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity getAllProjects(){
        List<Project> projects = projectService.getAllProjects();
        return new ResponseEntity(projects , HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getProjectById(@PathVariable Long id){
        Project project = projectService.getProjectById(id);
        return new ResponseEntity(project , HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProject(@PathVariable Long id , @Valid @RequestBody ProjectDto projectDto){
        Project project = projectService.updateProject(id , projectDto);
        return new ResponseEntity(project , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProjectById(@PathVariable Long id){
        String result = projectService.deleteProjectById(id);
        return new ResponseEntity(result , HttpStatus.OK);
    }
}
