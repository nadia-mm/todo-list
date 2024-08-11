package com.nadiamm.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TodoService {

    @Autowired
    private  TodoRepository todos;

    private static  final Logger LOGGER = LoggerFactory.getLogger(TodoService.class);

    public Todo findTodoById(long id) {
        LOGGER.info("TodoService - Retrieving Todo #{}.",id);
        return todos.findById(id)
                .orElseThrow(() -> new TodoException("Todo #"+id+" not found."));
    }
    public Todo saveTodo(Todo todo){
        LOGGER.info("TodoService - Saving Todo.");
        return  todos.save(todo);
    }

    public boolean removeTodoById(long id){
        LOGGER.info("TodoService - Removing Todo #{}.",id);
        if (!todos.existsById(id)) {
            return  false;
        }
        todos.deleteById(id);
        return true;
    }

   public List<Todo> findAllTodos() {
        LOGGER.info("TodoService - Finding all todos.");
        Iterable<Todo> iterableTodos = todos.findAll();
        return StreamSupport.stream(iterableTodos.spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Todo> findAllTodosByStatus(boolean isDone) {
        LOGGER.info("TodoService - Finding all todos with completion status: {}.",isDone);
        return todos.findAllTodosByIsDone(isDone);
    }
}
