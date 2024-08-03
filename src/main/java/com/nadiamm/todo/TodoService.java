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

    public Response<Todo> findTodoById(long id) {
        LOGGER.info("TodoService - Retrieving Todo with id {}.",id);
        Todo todo =  todos.findById(id)
                .orElseThrow(() -> new TodoException("Todo with id "+id+" not found."));

        return new Response<>(true, todo, "Todo #" + id + " found.");
    }
    public Response<Todo> saveTodo(Todo todo){
        Todo savedTodo = todos.save(todo);
        LOGGER.info("TodoService - Saving Todo with id {}.",savedTodo.getId());
        return new Response<>(true, savedTodo, "Todo with id "+savedTodo.getId() + ": "+ todo.getTitle() + " saved successfully.");
    }

    public Response<Todo> removeTodoById(long id){
        LOGGER.info("TodoService - Removing Todo with id {}.",id);
        if (!todos.existsById(id)) {
            return new Response<>(false, "Todo with id " + id + " not found.");
        }
        todos.deleteById(id);
        return new Response<>(true, "Todo with id " + id + " removed successfully.");
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
