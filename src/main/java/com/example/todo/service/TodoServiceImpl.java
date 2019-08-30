package com.example.todo.service;

import com.example.todo.dao.TodoDao;
import com.example.todo.models.Todo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TodoServiceImpl implements TodoService {
    @Resource
    TodoDao todoDao;

    @Override
    public List<Todo> findAll() {
        return todoDao.findAll();
    }

    @Override
    public Todo addTodo(Todo todo) {
        return todoDao.addTodo(todo);
    }

    @Override
    public void updateTodo(int todoId, String message, boolean isCompleted) {
        todoDao.updateTodo(todoId, message, isCompleted);
    }

    @Override
    public void removeTodo(int todoId) {
        todoDao.removeTodo(todoId);
    }
}
