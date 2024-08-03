package com.nadiamm.todo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TodoRepositoryTest {


    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    public void setUp() {
        Todo doneTodo = new Todo("Done Task");
        doneTodo.setDone(true);
        List<Todo> todos = List.of(new Todo("Not done 1"), new Todo(" Not done2"), doneTodo);
        todoRepository.saveAll(todos);
    }

    @AfterEach
    public void tearDown() {
        todoRepository.deleteAll();
    }


    @Test
    public void testFindByIsDone() {
        assertEquals(1,todoRepository.findAllTodosByIsDone(true).size());
        assertEquals(2,todoRepository.findAllTodosByIsDone(false).size());

    }
}
