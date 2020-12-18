package com.techelevator.dao;

import com.techelevator.model.Project;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ProjectDAO {

    Long createProject(Project newProject);

    void createProjectUser(Long projectID, Long userID);

    List<Project>allProjects(Long userId);

    Project getProjectByID(Long id);

   void updateProject(Project project, Long id);

   void deleteProjectById(Long projectID);

    Timestamp getMostRecent(Long projectID);

}
