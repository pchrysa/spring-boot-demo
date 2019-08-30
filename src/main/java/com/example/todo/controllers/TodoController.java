package com.example.todo.controllers;

import com.example.todo.models.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private HashSet<Todo> allTodos = new HashSet<Todo>() {{
        add(new Todo(1, "test", false));
        add(new Todo(2, "test 2", false));
        add(new Todo(3, "test 3", false));
    }};

    @GetMapping("")
    public ResponseEntity<HashSet<Todo>> getAllTodos() {
        return new ResponseEntity<>(allTodos, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable long id) {
        Todo todoItem = allTodos.stream().filter(todo -> todo.getId() == id).findFirst().orElseThrow(() -> new IllegalStateException("Not Found"));
        return new ResponseEntity<Todo>(todoItem, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<HashSet<Todo>> addTodo(@RequestParam String text) {
        int newId = allTodos.size() + 1;
        allTodos.add(new Todo(newId, text, false));
        return new ResponseEntity<>(allTodos, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<HashSet<Todo>> updateTodo(@PathVariable int id, @RequestParam("text") String text, @RequestParam("isCompleted") boolean isCompleted) {
        Todo todoItem = allTodos.stream().filter(todo -> todo.getId() == id).findFirst().orElseThrow(() -> new IllegalStateException("Not Found"));

        allTodos.remove(todoItem);
        allTodos.add(new Todo(id, text, isCompleted));

        return new ResponseEntity<>(allTodos, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HashSet<Todo>> removeTodo(@PathVariable int id) {
        Todo todoItem = allTodos.stream().filter(todo -> todo.getId() == id).findFirst().orElseThrow(() -> new IllegalStateException("Not Found"));
        allTodos.remove(todoItem);
        return new ResponseEntity<>(allTodos, HttpStatus.OK);
    }
}
