package com.eBookWebsite.eBookWebsite.Repository;

import com.eBookWebsite.eBookWebsite.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users, Integer> {

    Users findByUsername(String username);
}
