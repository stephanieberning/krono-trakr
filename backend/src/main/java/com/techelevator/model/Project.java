package com.techelevator.model;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Project {

    private Long projectID;
    @Valid
    private String projectName;
    private String projectDescription;
    private String projectImg;
    private String endDate;
    private Timestamp mostRecent;
    private boolean shared;

    public Project() {}

    public Project(String projectName, String projectDescription, String projectImg) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectImg = projectImg;
    }

    public Long getProjectID() {
        return projectID;
    }

    public void setProjectID(long projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectImg() {
        return projectImg;
    }

    public void setProjectImg(String projectImg) {
        this.projectImg = projectImg;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Timestamp getMostRecent() {
        return mostRecent;
    }

    public void setMostRecent(Timestamp mostRecent) {
        this.mostRecent = mostRecent;
    }

    public void setProjectID(Long projectID) {
        this.projectID = projectID;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }
}
