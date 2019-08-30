package com.example.todo;

import com.example.todo.controllers.TodoPostgresController;
import com.example.todo.models.Todo;
import com.example.todo.service.TodoService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = TodoPostgresController.class)
public class TodoServiceMockTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TodoService todoService;

    @Before
    public void setup() {
        Mockito.when(todoService.findAll()).thenReturn(new ArrayList<Todo>() {{
            add(new Todo(1, "test", false));
            add(new Todo(2, "test 2", false));
            add(new Todo(3, "test 3", true));
        }});
    }

    @Test
    public void testFetchAllTodos() throws Exception {
        List<Todo> todos = todoService.findAll();
        Type todoType = new TypeToken<List<Todo>>(){}.getType();
        String todosJsonStr = new Gson().toJson(todos, todoType);
        mvc.perform( MockMvcRequestBuilders
                .get("/todoapp")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(todosJsonStr));
    }

    @Test
    public void testUpdateTodo() throws Exception {
        Todo todo = new Todo();
        todo.setCompleted(true);
        todo.setText("XOXO");

        Type todoType = new TypeToken<Todo>(){}.getType();
        String todoJsonStr = new Gson().toJson(todo, todoType);

        mvc.perform( MockMvcRequestBuilders
                .put("/todoapp/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(todoJsonStr)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testAddTodo() throws Exception {
        Todo todo = new Todo();
        todo.setCompleted(true);
        todo.setText("XOXO");

        Type todoType = new TypeToken<Todo>(){}.getType();
        String todoJsonStr = new Gson().toJson(todo, todoType);

        mvc.perform( MockMvcRequestBuilders
                .post("/todoapp")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(todoJsonStr)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddTodoAndCheckContent() throws Exception {
        Todo todo = new Todo();
        todo.setCompleted(true);
        todo.setText("XOXO");
        todoService.addTodo(todo);

        List<Todo> todos = todoService.findAll();

        Type todosType = new TypeToken<List<Todo>>(){}.getType();
        String todosJsonStr = new Gson().toJson(todos, todosType);

        mvc.perform( MockMvcRequestBuilders
                .get("/todoapp")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(todosJsonStr))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)));
    }


    @Test
    public void testRemoveTodo() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .delete("/todoapp/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void testRemoveTodoAndCheckContent() throws Exception {
        int todoId = 1;
        todoService.removeTodo(todoId);

        List<Todo> todos = todoService.findAll();
        Type todosType = new TypeToken<List<Todo>>(){}.getType();
        String todosJsonStr = new Gson().toJson(todos, todosType);

        mvc.perform( MockMvcRequestBuilders
                .get("/todoapp")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(todosJsonStr))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
    }
}
