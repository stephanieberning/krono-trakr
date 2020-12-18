package com.techelevator.controller;

import com.techelevator.dao.ProjectDAO;
import com.techelevator.dao.UserDAO;
import com.techelevator.model.Project;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import org.springframework.http.HttpStatus;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class ProjectController {
    private final ProjectDAO projectDAO;
    private final UserDAO userDAO;



    public ProjectController(ProjectDAO projectDAO, UserDAO userDAO) {
        this.projectDAO = projectDAO;
        this.userDAO = userDAO;
    }

    @PutMapping(path = "/projects/{id}")
    public void updateProject(@PathVariable Long id, @Valid @RequestBody Project updatedProject) {
        projectDAO.updateProject(updatedProject, id);
    }

    @DeleteMapping(path = "/projects/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectDAO.deleteProjectById(id);
    }

    @GetMapping(value = "/projects")
    public List<Project> getProjectsList(Principal principal){
        Long userID = userDAO.findByUsername(principal.getName()).getId();
        return projectDAO.allProjects(userID);
    }

    @GetMapping(value = "/projects/{id}")
    public Project getProjectsById(@PathVariable Long id){
        return projectDAO.getProjectByID(id);
    }

    @PostMapping("/projects")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProject(@RequestBody Project project, Principal principal){
       Long projectID = projectDAO.createProject(project);
       Long userID = userDAO.findByUsername(principal.getName()).getId();
       projectDAO.createProjectUser(projectID, userID);

    }

    //adding to push
}
