package com.example.application.views.main;

import java.time.LocalDate;

public class Todo {
    private String id;
    private String title;
    private boolean done;
    private boolean deleted;
    private String notes;
    private String date;
    private String priority;

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

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

    public String getNotes() {return notes;}

    public void setNotes(String notes) {this.notes = notes;}

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public String getPriority() {return priority;}

    public void setPriority(String priority) {this.priority = priority;}

    public Todo(String id, boolean done, String title, boolean deleted,String notes, String date, String priority) {
        this.id = id;
        this.title = title;
        this.done = done;
        this.deleted = deleted;
        this.notes = notes;
        this.date = date;
        this.priority = priority;
    }

    public Todo(boolean done, String title, boolean deleted,String notes, String date, String priority) {
        this.title = title;
        this.done = done;
        this.deleted = deleted;
        this.notes = notes;
        this.date = date;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", done=" + done +
                ", deleted=" + deleted +
                ", notes='" + notes + '\'' +
                ", date='" + date + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
