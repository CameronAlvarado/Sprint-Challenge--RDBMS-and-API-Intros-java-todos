package com.lambdaschool.sprint2.controllers;

import com.lambdaschool.sprint2.models.Todo;
import com.lambdaschool.sprint2.models.User;
import com.lambdaschool.sprint2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UserService userService;

    // http://localhost:2019/users/users/
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // allows only admin
    @GetMapping(value = "/users",
            produces = {"application/json"})
    public ResponseEntity<?> listAllUsers()
    {
        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(myUsers,
                HttpStatus.OK);
    }

    // http://localhost:2019/users/user/7
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/user/{userId}",
            produces = {"application/json"})
    public ResponseEntity<?> getUserById(
            @PathVariable
                    Long userId)
    {
        User u = userService.findUserById(userId);
        return new ResponseEntity<>(u,
                HttpStatus.OK);
    }

    // http://localhost:2019/users/user/name/cinnamon
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/user/name/{userName}",
            produces = {"application/json"})
    public ResponseEntity<?> getUserByName(
            @PathVariable
                    String userName)
    {
        User u = userService.findByName(userName);
        return new ResponseEntity<>(u,
                HttpStatus.OK);
    }

    // http://localhost:2019/users/user/name/like/da?sort=username
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/user/name/like/{userName}",
            produces = {"application/json"})
    public ResponseEntity<?> getUserLikeName(
            @PathVariable
                    String userName)
    {
        List<User> u = userService.findByNameContaining(userName);
        return new ResponseEntity<>(u,
                HttpStatus.OK);
    }

    // http://localhost:2019/users/getusername
    @GetMapping(value = "/getusername",
            produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<?> getCurrentUserName(Authentication authentication)
    {
        return new ResponseEntity<>(authentication.getPrincipal(),
                HttpStatus.OK);
    }

    //--------------------------------------------------------------------------

    // http://localhost:2019/users/mine <------ ***** for MVP, is working *****
    @GetMapping(value = "/mine",
            produces = {"application/json"})
    public ResponseEntity<?> getCurrentUserInfo(Authentication authentication)
    {
        User u = userService.findByName(authentication.getName());
        return new ResponseEntity<>(u,
                HttpStatus.OK);
    }

    // http://localhost:2019/users/user <------ ***** for MVP, is working *****
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/user",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewUser(HttpServletRequest request,
                                        @Valid
                                        @RequestBody
                                                User newuser) throws URISyntaxException
    {
        newuser = userService.save(newuser);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newuser.getUserid())
                .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null,
                responseHeaders,
                HttpStatus.CREATED);
    }

//         http://localhost:2019/users/todoid/14 <------ for MVP
    @PostMapping(value = "/todoid/{userid}",
            consumes = {"application/json"})
//            produces = {"application/json"})
    public ResponseEntity<?> addNewTodo(@PathVariable
                                                Long userid, // <------ was long.
                                        @RequestBody
                                                Todo todoJSON)
    {

        userService.addTodo(userid, todoJSON);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    //--------------------------------------------------------------------------

    // http://localhost:2019/users/user/7
    //        {
    //            "userid": 7,
    //                "username": "cinnamon",
    //                "primaryemail": "cinnamon@lambdaschool.home",
    //                "useremails": [
    //            {
    //                    "useremail": "cinnamon@mymail.local"
    //            },
    //            {
    //                    "useremail": "hops@mymail.local"
    //            },
    //            {
    //                    "useremail": "bunny@email.local"
    //            }
    //        ]
    //        }
    // http://localhost:2019/users/user/7
    @PutMapping(value = "/user/{id}")
    public ResponseEntity<?> updateUser(HttpServletRequest request,
                                        @RequestBody
                                                User updateUser,
                                        @PathVariable
                                                long id)
    {
        userService.update(updateUser,
                id,
                request.isUserInRole("ADMIN"));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // http://localhost:2019/users/user/14
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUserById(
            @PathVariable
                    long id)
    {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://localhost:2019/users/userid/14/ <------ MVP working?
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/userid/{userid}")
    public ResponseEntity<?> deleteUserByIds(
            @PathVariable
                    long userid)
    {
        userService.delete(userid);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    // http://localhost:2019/users/user/15/role/2
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/user/{userid}/role/{roleid}")
    public ResponseEntity<?> postUserRoleByIds(
            @PathVariable
                    long userid,
            @PathVariable
                    long roleid)
    {
        userService.addUserRole(userid,
                roleid);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}