package com.example.todo.models;

public class Todo {

    private int id;
    private String text;
    private boolean isCompleted;

    public Todo() {
    }

    public Todo(int id, String text, boolean isCompleted) {
        this.id = id;
        this.text = text;
        this.isCompleted = isCompleted;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
