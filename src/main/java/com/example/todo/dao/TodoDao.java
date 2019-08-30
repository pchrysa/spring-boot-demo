package com.example.todo.dao;

import com.example.todo.models.Todo;

import java.util.List;

public interface TodoDao {
    List<Todo> findAll();
    Todo addTodo(Todo todo);
    void updateTodo(int todoId, String message, boolean isCompleted);
    void removeTodo(int todoId);
}
