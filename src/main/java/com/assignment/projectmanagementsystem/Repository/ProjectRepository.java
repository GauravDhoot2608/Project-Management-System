package com.assignment.projectmanagementsystem.Repository;

import com.assignment.projectmanagementsystem.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {

}
