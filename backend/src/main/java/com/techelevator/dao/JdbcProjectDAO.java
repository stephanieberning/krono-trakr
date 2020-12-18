package com.techelevator.dao;

import com.techelevator.model.Project;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProjectDAO implements ProjectDAO{

    private JdbcTemplate jdbcTemplate;

    public JdbcProjectDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long createProject(Project newProject){
        String sql = "INSERT INTO projects (project_name, project_desc, project_img, end_date) VALUES (?,?,?,?) RETURNING project_id";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, newProject.getProjectName(), newProject.getProjectDescription(), newProject.getProjectImg(),
                 newProject.getEndDate());
            if(rowSet.next()){
                newProject.setProjectID(rowSet.getLong("project_id"));
            }
            return newProject.getProjectID();
    }

    @Override
    public void createProjectUser(Long projectID, Long userID){
        String sql = "INSERT INTO user_project(project_id, user_id) VALUES (?,?)";
        jdbcTemplate.update(sql, projectID, userID);
    }

    @Override
    public List<Project> allProjects(Long userId) {
        List<Project> result = new ArrayList<>();
        String sql = "SELECT  projects.project_id, projects.project_name, projects.project_desc, projects.project_img, projects.end_date, projects.shared\n" +
                "FROM projects\n" +
                "JOIN user_project ON user_project.project_id = projects.project_id\n" +
                "JOIN users ON user_project.user_id = users.user_id\n" +
                "WHERE users.user_id = ? OR (users.user_id = (SELECT users.manager_id FROM users WHERE users.user_id = ?) AND shared IS true);";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId, userId);

        while(rowSet.next()) {
            Project project = mapRowToProject(rowSet);
            result.add(project);
        }

        return result;
    }

    @Override
    public Project getProjectByID(Long id){
        String sql = "SELECT  projects.project_id, projects.project_name, projects.project_desc, projects.project_img, projects.end_date, projects.shared\n" +
                "FROM projects\n" +
                "JOIN user_project ON user_project.project_id = projects.project_id\n" +
                "JOIN users ON user_project.user_id = users.user_id\n" +
                "WHERE projects.project_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        if(rowSet.next()){
            return mapRowToProject(rowSet);
        }
        return null;
    }

    @Override
    public void updateProject(Project project, Long id) {
        String sql = "UPDATE projects SET project_name = ?, project_desc = ?, project_img = ?, end_date = ?, shared = ? WHERE project_id = ?;";
        jdbcTemplate.update(sql, project.getProjectName(), project.getProjectDescription(), project.getProjectImg(),project.getEndDate(), project.isShared(), id);
    }


    @Override
    public void deleteProjectById(Long projectID){
        String sql = "DELETE FROM user_project WHERE project_id = ?;";
        jdbcTemplate.update(sql, projectID);
        sql = "DELETE FROM timesheet WHERE project_id = ?;";
        jdbcTemplate.update(sql, projectID);
        sql = "DELETE FROM projects WHERE project_id = ?;";
        jdbcTemplate.update(sql, projectID);
    }

    @Override
    public Timestamp getMostRecent(Long projectID) {
        String sql = "SELECT MAX(ending_time)\n" +
                "FROM timesheet\n" +
                "WHERE project_id = ?\n;";
           return jdbcTemplate.queryForObject(sql, Timestamp.class, projectID);
    }

    private Project mapRowToProject(SqlRowSet rowSet) {
        Project result = new Project();
        result.setProjectID(rowSet.getLong("project_id"));
        result.setProjectName(rowSet.getString("project_name"));
        result.setProjectDescription(rowSet.getString("project_desc"));
        result.setProjectImg(rowSet.getString("project_img"));
        result.setMostRecent(getMostRecent(result.getProjectID()));
        if(rowSet.getString("end_date") != null) {
            result.setEndDate(rowSet.getString("end_date"));
        } else {
            result.setEndDate("Ongoing");
        }
        result.setShared(rowSet.getBoolean("shared"));
        return result;
    }


}