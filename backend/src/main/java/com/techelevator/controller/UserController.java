package com.techelevator.controller;

import com.techelevator.dao.UserDAO;
import com.techelevator.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@CrossOrigin
public class UserController {

    private UserDAO userDAO;

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // GET - Fetch Logged In User Data
    @GetMapping(path = "/users/profile")
    public User getUserForProfile(Principal principal) {
        return userDAO.findByUsername(principal.getName());
    }

    // PUT - Update User Data By ID
    @PutMapping(path= "/users/profile/{id}")
    public void updateProfile(@PathVariable Long id, @Valid @RequestBody User user, Principal principal) {
        userDAO.updateUser(user, principal.getName(),id);
    }

    // GET - All User Data
    @GetMapping( path = "/users")
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    // GET - All Employee Data
    @GetMapping( path = "/employees")
    public List<User> getAllEmployees(Principal principal) {
        return userDAO.getEmployees(userDAO.findByUsername(principal.getName()).getId());
    }
}
