package com.example.application.views.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.gentyref.TypeToken;
import com.nimbusds.jose.shaded.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TodoModel {
    private static List<Todo> todos = new ArrayList<>();

    public static List<Todo> findAll() {
        try {
            todos = new Gson().fromJson(new FileReader("./todos.json"), new TypeToken<ArrayList<Todo>>() {}.getType());

        }catch (FileNotFoundException e){
            ObjectMapper mapper = new ObjectMapper();
            try {
                mapper.writeValue(new File("./todos.json"), todos);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
        return todos.stream().filter(todo -> !todo.isDeleted()).toList();
    }

    public static String insert(Todo item) {
        item.setId(String.valueOf(UUID.randomUUID()));
        todos.add(item);
        write();
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
        write();
    }

    public static void updateDone(String id, Boolean item) {
        todos.stream().filter((todo)->todo.getId().equals(id)).findFirst().get().setDone(item);
        write();
    }

    public static void updateDeleted(String id, Boolean item) {
        todos.stream().filter((todo)->todo.getId().equals(id)).findFirst().get().setDeleted(item);
        write();
    }
    public static void updateNotes(String id, String notes) {
        todos.stream().filter((todo) -> todo.getId().equals(id)).findFirst().get().setNotes(notes);
        write();
    }
    public static void updateDate(String id, String date) {
        System.out.println(date);
        todos.stream().filter((todo) -> todo.getId().equals(id)).findFirst().get().setDate(date);
        write();
    }
    public static void updatePriority(String id, String priority) {
        todos.stream().filter((todo) -> todo.getId().equals(id)).findFirst().get().setPriority(priority);
        write();
    }
    public static void write() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("./todos.json"), todos);
            System.out.println(mapper.writeValueAsString(todos));
        }catch (JsonProcessingException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
