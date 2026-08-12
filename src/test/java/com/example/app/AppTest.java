package com.example.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

    @Test
    void testMessage() {
        String message = "Hello from my Java Gradle DevOps project!";

        assertEquals(
            "Hello from my Java Gradle DevOps project!",
            message
        );
    }
}
