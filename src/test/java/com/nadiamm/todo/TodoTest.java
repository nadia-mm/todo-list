package com.nadiamm.todo;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class TodoTest {

    private final String title = "Test Title";
    private final String description = "Test Description";

    @Test
    public void testDefaultConstructor() {
        Todo todo = new Todo();
        
        assertNotNull(todo);
        assertNull(todo.getTitle());
        assertNull(todo.getDescription());
        assertFalse(todo.isDone());
        assertNotNull(todo.getCreatedAt());
        assertNotNull(todo.getUpdatedAt());
        assertEquals(todo.getCreatedAt(),todo.getUpdatedAt());
    }

    @Test
    public void testConstructorWithTitle() {
        Todo todo = new Todo(title);
        
        assertNotNull(todo);
        assertEquals(title, todo.getTitle());
        assertNull(todo.getDescription());
        assertFalse(todo.isDone());
        assertNotNull(todo.getCreatedAt());
        assertNotNull(todo.getUpdatedAt());
        assertEquals(todo.getCreatedAt(),todo.getUpdatedAt());
    }

    @Test
    public void testConstructorWithTitleAndDescription() {

        Todo todo = new Todo(title, description);
        
        assertNotNull(todo);
        assertEquals(title, todo.getTitle());
        assertEquals(description, todo.getDescription());
        assertFalse(todo.isDone());
        assertNotNull(todo.getCreatedAt());
        assertNotNull(todo.getUpdatedAt());
        assertEquals(todo.getCreatedAt(),todo.getUpdatedAt());
    }

    @Test
    public void testSetters() {
        Todo todo = new Todo(title, description);
        String newTitle = "New Title";
        String newDescription = "New Description";
        boolean newIsDone = true;
        LocalDateTime  createdAt = todo.getCreatedAt();

        assertEquals(title, todo.getTitle());
        assertEquals(description, todo.getDescription());
        assertFalse(todo.isDone());
        assertNotNull(todo.getCreatedAt());
        assertNotNull(todo.getUpdatedAt());
        assertEquals(todo.getCreatedAt(),todo.getUpdatedAt());

        addPause();

        todo.setTitle(newTitle);
        todo.setDescription(newDescription);
        todo.setDone(newIsDone);

        assertEquals(newTitle, todo.getTitle());
        assertEquals(newDescription, todo.getDescription());
        assertTrue(todo.isDone());
        assertEquals(createdAt,todo.getCreatedAt());
        assertNotEquals(todo.getCreatedAt(),todo.getUpdatedAt());
    }

    @Test
    public void testTitleUpdateResetsTimestamps() {
        Todo todo = new Todo();
        LocalDateTime initialCreatedAt = todo.getCreatedAt();

        addPause();

        todo.setTitle("Updated Title");

        assertEquals(initialCreatedAt, todo.getCreatedAt());
        assertNotEquals(todo.getCreatedAt(), todo.getUpdatedAt());
    }

    @Test
    public void testDescriptionUpdateUpdatesTimestamp() {
        Todo todo = new Todo();
        LocalDateTime initialUpdatedAt = todo.getUpdatedAt();

        addPause();

        todo.setDescription("Updated Description");

        assertNotEquals(initialUpdatedAt, todo.getUpdatedAt());
    }

    @Test
    public void testDoneUpdateUpdatesTimestamp() {
        Todo todo = new Todo();
        LocalDateTime initialUpdatedAt = todo.getUpdatedAt();

        addPause();

        todo.setDone(true);

        assertNotEquals(initialUpdatedAt, todo.getUpdatedAt());
    }

    private void addPause(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
