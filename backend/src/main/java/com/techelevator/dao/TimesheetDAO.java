package com.techelevator.dao;

import com.techelevator.model.Timesheet;

import java.util.List;

public interface TimesheetDAO {

    void createTimesheet(Timesheet newTimesheet);

    void completeTimesheet(Long timeId, String description);

    void deleteTimesheet(Long timeId);

    List<Timesheet> listTimesheets(Long userId);

    Timesheet getTimesheet(Long timeID);

    Timesheet getActiveLog(Long userId);

}
