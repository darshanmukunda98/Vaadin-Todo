package com.example.application.views.main;

public class Todo {
    private String id;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private boolean done;
    private boolean deleted;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Todo(String id,String title, boolean done, boolean deleted) {
        this.id = id;
        this.title = title;
        this.done = done;
        this.deleted = deleted;
    }
    public Todo(String title, boolean done, boolean deleted) {
        this.title = title;
        this.done = done;
        this.deleted = deleted;
    }
}
