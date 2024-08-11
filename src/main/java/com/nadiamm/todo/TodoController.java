package com.nadiamm.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos(){
        return ResponseEntity.ok(todoService.findAllTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Todo>> getTodoById(@PathVariable long id){
        Todo todo = todoService.findTodoById(id);
        if(todo == null){
           return new ResponseEntity<>(new Response<>(false, "Todo #"+id+" has not been found."),HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new Response<>(true, todo, "Todo #"+id+" has been found."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Todo>> updateTodoById(@PathVariable long id, @RequestBody Todo todo){
        Todo existingTodo = todoService.findTodoById(id);
        if (existingTodo == null) {
            return new ResponseEntity<>(new Response<>(false, "Todo #" + id+ " has not been found."), HttpStatus.NOT_FOUND);
        }

        todo.setId(id);
        Todo savedTodo = todoService.saveTodo(todo);
        return ResponseEntity.ok(new Response<>(true, savedTodo,"Todo #"+id+" has been updated successfully."));
    }

    @PostMapping
    public ResponseEntity<Response<Todo>> createTodo(@RequestBody Todo todo){
        Todo savedTodo = todoService.saveTodo(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(true, savedTodo, "Todo #" + savedTodo.getId() + " has been created successfully."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Todo>> removeTodoById(@PathVariable long id){
        if (todoService.findTodoById(id) == null) {
            return new ResponseEntity<>(new Response<>(false, "Todo #" + id + " has not been found."), HttpStatus.NOT_FOUND);
        }
        todoService.removeTodoById(id);
        //return new ResponseEntity<>(new Response<>(true,"Todo #"+id+" has been removed successfully."),HttpStatus.valueOf(204));
        return ResponseEntity.ok(new Response<>(true,"Todo #"+id+" has been removed successfully."));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Todo>> getAllTodosByStatus(@RequestParam(value="completed") boolean isDone) {
        List<Todo> todos = todoService.findAllTodosByStatus(isDone);
        return ResponseEntity.ok(todos);
    }
}
