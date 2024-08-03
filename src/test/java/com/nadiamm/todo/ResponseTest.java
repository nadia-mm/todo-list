package com.nadiamm.todo;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ResponseTest {
    private final  String message = "Success";

    @Test
    public void testResponseWithList() {
        List<String> data = List.of("Item1", "Item2");
        Response<String> response = new Response<>(true, data, message);

        assertTrue(response.isSuccess());
        assertEquals(data, response.getData());
        assertEquals(message, response.getMessage());
    }

    @Test
    public void testResponseWithEmptyList() {
        Response<String> response = new Response<>(true, Collections.emptyList(), message);

        assertTrue(response.isSuccess());
        assertTrue(response.getData().isEmpty());
        assertEquals(message, response.getMessage());
    }

    @Test
    public void testResponseWithSingleItem() {
        String item = "SingleItem";
        Response<String> response = new Response<>(true, item, message);

        assertTrue(response.isSuccess());
        assertEquals(Collections.singletonList(item), response.getData());
        assertEquals(message, response.getMessage());
    }

    @Test
    public void testResponseWithNullList() {
        Response<String> response = new Response<>(true, null, message);

        assertTrue(response.isSuccess());
        assertTrue(response.getData().isEmpty());
        assertEquals(message, response.getMessage());
    }

    @Test
    public void testResponseWithEmptyMessage() {
        List<String> data = List.of("Item1");
        Response<String> response = new Response<>(true, data, "");

        assertTrue(response.isSuccess());
        assertEquals(data, response.getData());
        assertEquals("", response.getMessage());
    }
}
