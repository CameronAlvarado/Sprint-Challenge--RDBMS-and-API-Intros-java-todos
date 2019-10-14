package com.lambdaschool.sprint2.respositories;

import com.lambdaschool.sprint2.models.Todo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todo, Long>
{
    List<Todo> findAllByUser_Username(String name);
}
