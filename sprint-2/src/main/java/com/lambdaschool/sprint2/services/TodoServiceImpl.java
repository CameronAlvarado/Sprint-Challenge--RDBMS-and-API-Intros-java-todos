package com.lambdaschool.sprint2.services;

import com.lambdaschool.sprint2.models.Todo;
import com.lambdaschool.sprint2.respositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "todoService")
public class TodoServiceImpl implements TodoService
{
    @Autowired
    private TodoRepository todorepos;

    @Override
    public List<Todo> findAll()
    {
        List<Todo> list = new ArrayList<>();
        todorepos.findAll()
                      .iterator()
                      .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Todo findTodoById(long id)
    {
        return todorepos.findById(id)
                             .orElseThrow(() -> new EntityNotFoundException("Todo with id " + id + " Not Found!"));
    }

    @Override
    public List<Todo> findByUserName(String username,
                                          boolean isAdmin)
    {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        if (username.equalsIgnoreCase(authentication.getName()) || isAdmin)
        {
            return todorepos.findAllByUser_Username(username);
        } else
        {
            throw new EntityNotFoundException(authentication.getName() + " not authorized to make change");
        }
    }


    @Override
    public void delete(long id,
                       boolean isAdmin)
    {
        if (todorepos.findById(id)
                          .isPresent())
        {
            Authentication authentication = SecurityContextHolder.getContext()
                                                                 .getAuthentication();
            if (todorepos.findById(id)
                              .get()
                              .getUser()
                              .getUsername()
                              .equalsIgnoreCase(authentication.getName()) || isAdmin)
            {
                todorepos.deleteById(id);
            } else
            {
                throw new EntityNotFoundException(authentication.getName() + " not authorized to make change");
            }
        } else
        {
            throw new EntityNotFoundException("Useremail with id " + id + " Not Found!");
        }
    }

    @Override
    public Todo update(long todoid,
//                            String description,
                            boolean isCompleted)
    {
//        Authentication authentication = SecurityContextHolder.getContext()
//                                                             .getAuthentication();
        if (todorepos.findById(todoid)
                          .isPresent())
        {
//            if (todorepos.findById(todoid)
//                              .get()
//                              .getUser()
//                              .getUsername();
////                              .equalsIgnoreCase(authentication.getName()) || isAdmin)
            {
                Todo todo = findTodoById(todoid);
//                todo.setDescription(description);
                todo.setCompleted(isCompleted);
                return todorepos.save(todo);
            } /*else*/
//            {
//                throw new EntityNotFoundException(authentication.getName() + " not authorized to make change");
//            }
        } else
        {
            throw new EntityNotFoundException("Todo with id " + todoid + " Not Found!");
        }
    }
}
