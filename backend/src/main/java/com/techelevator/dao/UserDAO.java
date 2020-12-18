package com.techelevator.dao;

import com.techelevator.model.User;

import java.util.List;

public interface UserDAO {

    List<User> getAllUsers();

    void updateUser(User user, String username, Long id);

    Long getUserIDByName(String firstName, String lastName);

    User findByUsername(String username);

    boolean create(String username, String password, String role, String firstName, String lastName, String email, String avatar);

    List<User> getEmployees(Long userId);
}
