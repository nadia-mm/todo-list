package com.nadiamm.todo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TodoExceptionTest {

    private final String message = "Custom error message";

    @Test
    public void testExceptionMessage() {

        TodoException exception = new TodoException(message);

        assertEquals(message, exception.getMessage(), "Exception message should match the provided message");
    }

    @Test
    public void testExceptionInheritance() {
        assertThrows(RuntimeException.class, () -> {
            throw new TodoException(message);
        }, "TodoException should be a RuntimeException");
    }
}
