package com.lambdaschool.sprint2.controllers;

import com.lambdaschool.sprint2.models.Todo;
import com.lambdaschool.sprint2.services.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController
{
    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    TodoService todoService;

    // http://localhost:2019/todos/todo
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/todo",
                produces = {"application/json"})
    public ResponseEntity<?> listAllTodos(HttpServletRequest request)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<Todo> allTodos = todoService.findAll();
        return new ResponseEntity<>(allTodos,
                                    HttpStatus.OK);
    }

    // http://localhost:2019/todos/todoid/8
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/todoid/{todoid}",
                produces = {"application/json"})
    public ResponseEntity<?> getUserEmailById(HttpServletRequest request,
                                              @PathVariable
                                                      Long todoid)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        Todo td = todoService.findTodoById(todoid);
        return new ResponseEntity<>(td,
                                    HttpStatus.OK);
    }


    // http://localhost:2019/useremails/username/cinnamon
    @GetMapping(value = "/username/{userName}",
                produces = {"application/json"})
    public ResponseEntity<?> findUseremailByUserName(HttpServletRequest request,
                                                     @PathVariable
                                                             String userName)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<Todo> theUseremails = todoService.findByUserName(userName,
                                                                        request.isUserInRole("ADMIN"));
        return new ResponseEntity<>(theUseremails,
                                    HttpStatus.OK);
    }

    // http://localhost:2019/useremails/useremail/8
    @DeleteMapping("/useremail/{useremailid}")
    public ResponseEntity<?> deleteUserEmailById(HttpServletRequest request,
                                                 @PathVariable
                                                         long useremailid)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        todoService.delete(useremailid,
                                request.isUserInRole("ADMIN"));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // http://localhost:2019/todos/todoid/9 <---- MVP, is working
    @PutMapping(value = "/todoid/{todoid}",
            consumes = {"application/json"})
    public ResponseEntity<?> updateTodo(HttpServletRequest request,
                                            @PathVariable
                                                long todoid,
                                             @RequestBody
                                                     Todo bool)
    {
        todoService.update(todoid, bool);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
