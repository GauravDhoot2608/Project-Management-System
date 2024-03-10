package com.assignment.projectmanagementsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "projects")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    // id, name, description, startDate, endDate,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "project name can't be blank")
    private String name;
    @NotNull(message = "Write some description about project")
    private String description;

    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
}
