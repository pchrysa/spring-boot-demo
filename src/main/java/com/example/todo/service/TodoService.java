package com.example.todo.service;

import com.example.todo.models.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> findAll();
    Todo addTodo(Todo todo);
    void updateTodo(int todoId, String message, boolean isCompleted);
    void removeTodo(int todoId);
}
