package com.example.todo.dao;

import com.example.todo.models.Todo;
import com.example.todo.mapper.TodoRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TodoDaoImpl implements TodoDao {

    @Autowired
    NamedParameterJdbcTemplate template;

    public TodoDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Todo> findAll() {
        return template.query("select * from todos", new TodoRowMapper());
    }

    @Override
    public Todo addTodo(Todo todo) {
        final String sqlInsert = "insert into todos(message, isCompleted) values(:message, :isCompleted)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("message", todo.getText())
                .addValue("isCompleted", todo.getIsCompleted());
        template.update(sqlInsert, param, holder, new String[] { "id" });

        todo.setId(holder.getKey().intValue());
        return todo;
    }

    @Override
    public void updateTodo(int todoId, String message, boolean isCompleted) {
        final String sql = "UPDATE todos SET message=:message, isCompleted=:isCompleted where id=:todoId";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("message", message)
                .addValue("isCompleted", isCompleted)
                .addValue("todoId", todoId);
        template.update(sql, param, holder);
    }

    @Override
    public void removeTodo(int todoId) {
        final String sql = "delete from todos where id=:todoId";

        Map<String,Object> map=new HashMap<String,Object>();
        map.put("todoId", todoId);

        template.execute(sql,map,new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement ps)
                    throws SQLException, DataAccessException {
                return ps.executeUpdate();
            }
        });
    }
}
