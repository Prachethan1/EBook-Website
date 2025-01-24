package com.eBookWebsite.eBookWebsite.controller;

import com.eBookWebsite.eBookWebsite.entity.Users;
import com.eBookWebsite.eBookWebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired
    private UserService userService;

    @PostMapping("/register-admin")
    public Users addAdmin(@RequestBody Users user){
        return userService.addAdmin(user);
    }

    @GetMapping("/get-all-users")
    public List<Users> getAllUsers(){
        return userService.getAllUsers();
    }
}
