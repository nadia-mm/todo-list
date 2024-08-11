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
    public void testFindTodoById_Success() {
        Todo todo = new Todo("Test Todo");
        todo.setId(id);
        when(todoRepository.findById(id)).thenReturn(Optional.of(todo));

        assertEquals(Optional.of(todo), todoRepository.findById(id));
        verify(todoRepository, times(1)).findById(1L);
}

    @Test
    public void testFindTodoById_NotFound() {
        when(todoRepository.findById(id)).thenReturn(Optional.empty());

        TodoException thrownException = assertThrows(TodoException.class, () -> {
            todoService.findTodoById(id);
        });

        assertEquals("Todo #1 not found.",thrownException.getMessage());
        verify(todoRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveTodo() {
        Todo todo = new Todo("Test Todo");
        todo.setId(id);
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        assertEquals(todo, todoService.saveTodo(todo));
        verify(todoRepository, times(1)).save(todo);
    }

    @Test
    public void testRemoveTodoById_Success() {
        when(todoRepository.existsById(id)).thenReturn(true);
        doNothing().when(todoRepository).deleteById(id);

        assertTrue(todoService.removeTodoById(id));
        verify(todoRepository, times(1)).existsById(1L);
        verify(todoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testRemoveTodoById_NotFound() {
        when(todoRepository.existsById(anyLong())).thenReturn(false);

        assertFalse( todoService.removeTodoById(1L));
        verify(todoRepository, times(1)).existsById(1L);
        verify(todoRepository, never()).deleteById(1L);
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
        verify(todoRepository, times(1)).findAll();
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
        verify(todoRepository, times(1)).findAllTodosByIsDone(true);
        verify(todoRepository, times(1)).findAllTodosByIsDone(false);
    }

}
