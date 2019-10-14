package com.lambdaschool.sprint2.services;

import com.lambdaschool.sprint2.models.Todo;

import java.util.List;

public interface TodoService
{
    List<Todo> findAll();

    Todo findTodoById(long id);

    List<Todo> findByUserName(String username,
                                   boolean isAdmin);

    void delete(long id,
                boolean isAdmin);

    Todo update(long todoid,
                Todo boolJSON);

}
