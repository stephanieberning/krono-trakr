package com.techelevator.model;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class Timesheet {

    @NotNull
    private Long timeID;
    @NotNull
    private Long projectID;
    private String projectName;
    @NotNull
    private Long userID;
    private String description;
    @NotNull
    private LocalDateTime beginningTime;
    private LocalDateTime endingTime;

    public Timesheet(long timeID, long projectID, long userID, LocalDateTime beginningTime) {
        this.timeID = timeID;
        this.projectID = projectID;
        this.userID = userID;
        this.beginningTime = beginningTime;
    }

    public Timesheet () {}

    public long getTimeID() {
        return timeID;
    }

    public void setTimeID(long timeID) {
        this.timeID = timeID;
    }

    public long getProjectID() {
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

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getBeginningTime() {
        return beginningTime;
    }

    public void setBeginningTime(LocalDateTime beginningTime) {
        this.beginningTime = beginningTime;
    }

    public LocalDateTime getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(LocalDateTime endingTime) {
        this.endingTime = endingTime;
    }
}
