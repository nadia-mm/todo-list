package com.nadiamm.todo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    private final long id = 1L;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindTodoById() {
        Todo todo = new Todo("Test Todo");
        todo.setId(id);
        when(todoRepository.findById(id)).thenReturn(Optional.of(todo));

        Response<Todo> response = todoService.findTodoById(id);

        assertTrue(response.isSuccess());
        assertEquals(Collections.singletonList(todo), response.getData());
        assertEquals("Todo #1 found.",response.getMessage());
    }

    @Test
    public void testFindTodoByIdNotFound() {
        when(todoRepository.findById(id)).thenReturn(Optional.empty());

        TodoException thrownException = assertThrows(TodoException.class, () -> {
            todoService.findTodoById(id);
        });
        assertEquals("Todo with id 1 not found.",thrownException.getMessage());

    }

    @Test
    public void testSaveTodo() {
        Todo todo = new Todo("Test Todo");
        todo.setId(id);
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);


        Response<Todo> response = todoService.saveTodo(todo);


        assertTrue(response.isSuccess());
        assertEquals(Collections.singletonList(todo),response.getData());
        assertEquals("Todo with id 1: Test Todo saved successfully.",response.getMessage());
    }

    @Test
    public void testRemoveTodoById() {

        when(todoRepository.existsById(id)).thenReturn(true);
        doNothing().when(todoRepository).deleteById(id);


        Response<Todo> response = todoService.removeTodoById(id);


        assertTrue(response.isSuccess());
        assertEquals("Todo with id 1 removed successfully.",response.getMessage());
    }

    @Test
    public void testRemoveTodoByIdNotFound() {

        when(todoRepository.existsById(id)).thenReturn(false);

        Response<Todo> response = todoService.removeTodoById(id);

        assertFalse(response.isSuccess());
        assertEquals("Todo with id 1 not found.",response.getMessage());
    }

    @Test
    public void testFindAllTodos() {
        Todo todo1 = new Todo("Todo 1");
        Todo todo2 = new Todo("Todo 2");
        Iterable<Todo> todos = List.of(todo1, todo2);
        when(todoRepository.findAll()).thenReturn(todos);

        List<Todo> result = todoService.findAllTodos();

        assertEquals(2,result.size());
        assertEquals(todo1,result.getFirst());
        assertEquals(todo2,result.get(1));
    }

    @Test
    public void testFindAllTodosByStatus() {
        Todo doneTodo = new Todo("Done Todo");
        doneTodo.setDone(true);
        List<Todo> todos = List.of(new Todo(), new Todo(), doneTodo);

        when(todoRepository.findAllTodosByIsDone(true)).thenReturn(todos.stream().filter(Todo::isDone).toList());
        when(todoRepository.findAllTodosByIsDone(false)).thenReturn(todos.stream().filter( item -> !item.isDone()).toList());

        assertEquals(2,todoRepository.findAllTodosByIsDone(false).size());
        assertEquals(1,todoRepository.findAllTodosByIsDone(true).size());

    }
}
