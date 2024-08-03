package com.nadiamm.todo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TODOS")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private boolean isDone;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Todo() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public Todo(String title) {
        this();
        this.title = title;
        this.isDone = false;
    }

    public Todo(String title, String description) {
        this(title);
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.isDone = false;
        updateTimestamp();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        updateTimestamp();
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        this.isDone = done;
        updateTimestamp();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    private void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }
}
