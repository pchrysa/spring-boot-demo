package com.example.todo.mapper;

import com.example.todo.models.Todo;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TodoRowMapper implements RowMapper<Todo> {

    @Override
    public Todo mapRow(ResultSet rs, int arg1) throws SQLException {
        return new Todo(rs.getInt("id"), rs.getString("message"), rs.getBoolean("isCompleted"));
    }
}
