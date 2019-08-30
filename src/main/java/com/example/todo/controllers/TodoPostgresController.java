package com.example.todo.controllers;

import com.example.todo.models.Todo;
import com.example.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/todoapp")
public class TodoPostgresController {
    @Autowired
    @Resource
    TodoService todoService;

    @GetMapping(value = "")
    public ResponseEntity<List<Todo>> getTodos() {
        return new ResponseEntity<>(todoService.findAll(), HttpStatus.OK);
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo) {
        Todo inserted = todoService.addTodo(todo);
        return new ResponseEntity<>(inserted, HttpStatus.CREATED);
    }


    @PutMapping("{id}")
    public ResponseEntity updateTodo(@PathVariable int id, @RequestBody Todo todo) {
        todoService.updateTodo(id, todo.getText(), todo.getIsCompleted());
        return new ResponseEntity(HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity removeTodo(@PathVariable int id) {
        todoService.removeTodo((id));
        return new ResponseEntity(HttpStatus.OK);
    }
}