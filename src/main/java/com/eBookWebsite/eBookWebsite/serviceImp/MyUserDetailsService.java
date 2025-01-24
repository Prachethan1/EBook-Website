package com.eBookWebsite.eBookWebsite.serviceImp;

import com.eBookWebsite.eBookWebsite.Repository.UserRepo;
import com.eBookWebsite.eBookWebsite.entity.UserPrinicpal;
import com.eBookWebsite.eBookWebsite.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findByUsername(username);
        if (user != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        }

        throw new UsernameNotFoundException("User not found with username: " + username);

//        if (user == null) {
//            throw new UsernameNotFoundException("user not found");
//        }
//
//        return new UserPrinicpal(user);
    }
}
