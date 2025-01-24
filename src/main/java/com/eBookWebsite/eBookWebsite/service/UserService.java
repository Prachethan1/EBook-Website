package com.eBookWebsite.eBookWebsite.service;

import com.eBookWebsite.eBookWebsite.entity.Users;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    Users register(Users user);

    String verify(Users user);

    Users addAdmin(Users user);

    List<Users> getAllUsers();
}
