package com.nadiamm.todo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TodoControllerTest {

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    private final String title = "Test Title";
    private final String description = "Test Description";
    private final Todo todo = new Todo(title, description);

   Response<Todo> response = new Response<>(true,todo, "Todo found");
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllTodos() {
        List<Todo> todos = new ArrayList<>();
        todos.add(todo);

        when(todoService.findAllTodos()).thenReturn(todos);

        ResponseEntity<List<Todo>> responseEntity = todoController.getAllTodos();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(todos, responseEntity.getBody());
    }

    @Test
    public void testGetTodoById() {

        when(todoService.findTodoById(anyLong())).thenReturn(response);

        ResponseEntity<Response<Todo>> responseEntity = todoController.getTodoById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
    }

    @Test
    public void testUpdateTodoById() {
        when(todoService.saveTodo(any(Todo.class))).thenReturn(response);

        ResponseEntity<Response<Todo>> responseEntity = todoController.updateTodoById(1L, todo);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
    }

    @Test
    public void testCreateTodo() {
        when(todoService.saveTodo(any(Todo.class))).thenReturn(response);

        ResponseEntity<Response<Todo>> responseEntity = todoController.createTodo(todo);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
    }

    @Test
    public void testDeleteTodoById() {
        when(todoService.removeTodoById(anyLong())).thenReturn(response);

        ResponseEntity<Response<Todo>> responseEntity = todoController.removeTodoById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
    }

    @Test
    public void testGetAllTodosByStatus() {
        Todo completeTodo= new Todo();
        completeTodo.setDone(true);

        List<Todo> todos = Arrays.asList(todo, new Todo("test"), completeTodo);
        when(todoService.findAllTodosByStatus(true)).thenReturn(todos.stream().filter(Todo::isDone).toList());
        when(todoService.findAllTodosByStatus(false)).thenReturn(todos.stream().filter( item -> !item.isDone()).toList());

        ResponseEntity<List<Todo>> responseFalse = todoController.getAllTodosByStatus(false);
        ResponseEntity<List<Todo>> responseTrue = todoController.getAllTodosByStatus(true);

        assertEquals(HttpStatus.OK, responseFalse.getStatusCode());
        assertEquals(2, responseFalse.getBody().size());

        assertEquals(HttpStatus.OK, responseTrue.getStatusCode());
        assertEquals(1, responseTrue.getBody().size());
    }
}
