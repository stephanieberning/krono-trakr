package com.techelevator.dao;

import com.techelevator.model.Timesheet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcTimesheetDAO implements TimesheetDAO{
    private JdbcTemplate jdbcTemplate;

    public JdbcTimesheetDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createTimesheet(Timesheet newTimesheet) {
        String sql = "INSERT INTO timesheet (project_id, user_id, time_desc, beginning_time, ending_time)\n" +
                "VALUES (?, ?, ?, ?, ?) RETURNING time_id;";
        LocalDateTime startTime = newTimesheet.getBeginningTime() != null ? newTimesheet.getBeginningTime() : LocalDateTime.now();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, newTimesheet.getProjectID(), newTimesheet.getUserID(), newTimesheet.getDescription(),
                startTime, newTimesheet.getEndingTime());
        if(rowSet.next()){
            newTimesheet.setProjectID(rowSet.getLong("time_id"));
        }
    }

    @Override
    public void completeTimesheet(Long timeId, String description) {
        String sql = "UPDATE timesheet SET ending_time= now(), time_desc=? WHERE time_id = ?;";
        jdbcTemplate.update(sql, description, timeId);
    }

    @Override
    public void deleteTimesheet(Long timeId) {
        String sql = "DELETE FROM timesheet WHERE time_id = ?;";
        jdbcTemplate.update(sql, timeId);
    }

    @Override
    public List<Timesheet> listTimesheets(Long userId) {
        List <Timesheet> result = new ArrayList<>();
        String sql = "SELECT projects.project_id, projects.project_name, users.user_id, time_id, time_desc, beginning_time," +
                " ending_time \n" +
                "FROM timesheet \n" +
                "JOIN projects ON timesheet.project_id = projects.project_id\n" +
                "JOIN users ON timesheet.user_id = users.user_id\n" +
                "WHERE users.user_id = ? OR (manager_id = ? AND shared IS true);";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while(rowSet.next()){
            Timesheet timesheet = mapRowToTimesheet(rowSet);
            result.add(timesheet);
        }
        return result;
    }

    @Override
    public Timesheet getTimesheet(Long timeID) {
        String sql = "SELECT projects.project_id, projects.project_name, user_id, time_id, time_desc, beginning_time," +
                " ending_time \n" +
                "FROM timesheet \n" +
                "JOIN projects ON timesheet.project_id = projects.project_id\n" +
                "WHERE time_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, timeID);
        if(rowSet.next()){
            return mapRowToTimesheet(rowSet);
        }else
        return null;
    }

    @Override
    public Timesheet getActiveLog(Long userId) {
        String sql = "SELECT projects.project_id, projects.project_name, user_id, time_id, time_desc, beginning_time," +
                " ending_time \n" +
                "FROM timesheet \n" +
                "JOIN projects ON timesheet.project_id = projects.project_id\n" +
                "WHERE ending_time IS NULL AND user_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        if(rowSet.next()){
            return mapRowToTimesheet(rowSet);
        }else
            return null;
    }


    private Timesheet mapRowToTimesheet(SqlRowSet rowSet) {
        Timesheet result = new Timesheet();

        result.setTimeID(rowSet.getLong("time_id"));
        result.setProjectID(rowSet.getLong("project_id"));
        result.setProjectName(rowSet.getString("project_name"));
        result.setUserID(rowSet.getLong("user_id"));
        result.setDescription(rowSet.getString("time_desc"));
        result.setBeginningTime(rowSet.getTimestamp("beginning_time").toLocalDateTime());
        if(rowSet.getTimestamp("ending_time") != null) {
            result.setEndingTime(rowSet.getTimestamp("ending_time").toLocalDateTime());
        }

        return result;
    }
}
