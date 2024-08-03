package com.nadiamm.todo;

import org.springframework.beans.factory.annotation.Autowired;
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
        return ResponseEntity.ok(todoService.findTodoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Todo>> updateTodoById(@PathVariable long id, @RequestBody Todo todo){
        todo.setId(id);
        return ResponseEntity.ok(todoService.saveTodo(todo));
    }

    @PostMapping
    public ResponseEntity<Response<Todo>> createTodo(@RequestBody Todo todo){
        return ResponseEntity.ok(todoService.saveTodo(todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Todo>> removeTodoById(@PathVariable long id){
        return ResponseEntity.ok(todoService.removeTodoById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Todo>> getAllTodosByStatus(@RequestParam(value="completed") boolean isDone) {
        List<Todo> todos = todoService.findAllTodosByStatus(isDone);
        return ResponseEntity.ok(todos);
    }
}
