package com.example.application.views.main;

import java.util.ArrayList;
import java.util.List;

public class TodoModel {
    private static List<Todo> todos = new ArrayList<>();

    private static List<Todo> findAll() {
        return todos;
    }

    public static String insert(Todo item) {
        item.setId(String.valueOf(Math.random()));
        todos.add(item);
        return item.getId();
    }

    public static void update(String id, String item, String columnName) {
        switch (columnName) {
            case "title" -> updateTitle(id, item);
            case "done" -> updateDone(id, Boolean.valueOf(item));
            case "deleted" -> updateDeleted(id, Boolean.valueOf(item));
        }
    }

    public static void updateTitle(String id, String todoTitle) {
        todos.stream().filter((todo)->todo.getId().equals(id)).findFirst().get().setTitle(todoTitle);
    }

    public static void updateDone(String id, Boolean item) {
        todos.stream().filter((todo)->todo.getId().equals(id)).findFirst().get().setDone(item);
    }

    public static void updateDeleted(String id, Boolean item) {
        todos.stream().filter((todo)->todo.getId().equals(id)).findFirst().get().setDeleted(item);
    }
}
