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

import java.util.*;


public class TodoControllerTest {

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    private final String title = "Test Title";
    private final String description = "Test Description";
    private final Todo todo = new Todo(title, description);

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
    public void testGetTodoById_Success() {

        when(todoService.findTodoById(1L)).thenReturn(todo);

        ResponseEntity<Response<Todo>> responseEntity = todoController.getTodoById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).isSuccess());
        assertEquals(Collections.singletonList(todo), responseEntity.getBody().getData());
        assertEquals("Todo #1 has been found.", responseEntity.getBody().getMessage());
    }

    @Test
    public void testGetTodoById_NotFound() {

        when(todoService.findTodoById(anyLong())).thenReturn(null);

        ResponseEntity<Response<Todo>> responseEntity = todoController.getTodoById(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertFalse(Objects.requireNonNull(responseEntity.getBody()).isSuccess());
        assertEquals("Todo #1 has not been found.", responseEntity.getBody().getMessage());
        assertEquals(Collections.emptyList(),responseEntity.getBody().getData());
    }

    @Test
    public void testUpdateTodoById_Success() {
        when(todoService.findTodoById(anyLong())).thenReturn(todo);
        when(todoService.saveTodo(any(Todo.class))).thenReturn(todo);
        ResponseEntity<Response<Todo>> responseEntity = todoController.updateTodoById(1L, todo);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).isSuccess());
        System.out.println( responseEntity.getBody());
        assertEquals(Collections.singletonList(todo), responseEntity.getBody().getData());
        assertEquals("Todo #1 has been updated successfully.", responseEntity.getBody().getMessage());
    }

    @Test
    public void testUpdateTodoById_NotFound() {
        when(todoService.findTodoById(anyLong())).thenReturn(null);
        ResponseEntity<Response<Todo>> responseEntity = todoController.updateTodoById(1L, todo);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertFalse(Objects.requireNonNull(responseEntity.getBody()).isSuccess());
        assertEquals("Todo #1 has not been found.", responseEntity.getBody().getMessage());
        assertEquals(Collections.emptyList(),responseEntity.getBody().getData());
    }
    @Test
    public void testCreateTodo() {
        when(todoService.saveTodo(any(Todo.class))).thenReturn(todo);

        ResponseEntity<Response<Todo>> responseEntity = todoController.createTodo(todo);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).isSuccess());
        assertEquals(Collections.singletonList(todo), responseEntity.getBody().getData());
        assertEquals("Todo #0 has been created successfully.", responseEntity.getBody().getMessage());
    }

    @Test
    public void testDeleteTodoById_Success() {
        when(todoService.findTodoById(anyLong())).thenReturn(todo);
        when(todoService.removeTodoById(anyLong())).thenReturn(true);

        ResponseEntity<Response<Todo>> responseEntity = todoController.removeTodoById(1L);

       // assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Collections.emptyList(), Objects.requireNonNull(responseEntity.getBody()).getData());
        assertTrue(responseEntity.getBody().isSuccess());
        assertEquals("Todo #1 has been removed successfully.", responseEntity.getBody().getMessage());
    }

    @Test
    public void testDeleteTodoById_NotFound() {
        when(todoService.findTodoById(anyLong())).thenReturn(null);

        ResponseEntity<Response<Todo>> responseEntity = todoController.removeTodoById(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(Collections.emptyList(), Objects.requireNonNull(responseEntity.getBody()).getData());
        assertFalse(responseEntity.getBody().isSuccess());
        assertEquals("Todo #1 has not been found.", responseEntity.getBody().getMessage());

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
        assertEquals(2, Objects.requireNonNull(responseFalse.getBody()).size());
        assertEquals(todos.stream().filter( item -> !item.isDone()).toList(),responseFalse.getBody());


        assertEquals(HttpStatus.OK, responseTrue.getStatusCode());
        assertEquals(1, Objects.requireNonNull(responseTrue.getBody()).size());
        assertEquals(todos.stream().filter(Todo::isDone).toList(),responseTrue.getBody());
    }
}
