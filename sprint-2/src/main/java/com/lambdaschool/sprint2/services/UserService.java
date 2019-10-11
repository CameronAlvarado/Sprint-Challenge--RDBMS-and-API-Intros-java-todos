package com.lambdaschool.sprint2.services;

import com.lambdaschool.sprint2.models.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService
{
    UserDetails loadUserByUsername(String username); // required by auth

    List<User> findAll();

    List<User> findByNameContaining(String username);

    User findUserById(long id);

    User findByName(String name);

    void delete(long id);

    User save(User user);

    User update(User user,
                long id,
                boolean isAdmin);

    void deleteUserRole(long userid,
                        long roleid);

    void addUserRole(long userid,
                     long roleid);
}