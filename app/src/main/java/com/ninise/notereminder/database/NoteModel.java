package com.ninise.notereminder.database;

public class NoteModel {

    private int id;
    private String title;
    private String description;
    private long time;
    private int request;

    public NoteModel () {

    }

    public NoteModel(final String title, final String description, int request) {
        this.title = title;
        this.description = description;
        this.request = request;
    }

    public NoteModel(final int id, final String title, final String description, int request) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.request = request;
    }

    public NoteModel(final int id, final String title, final String description, final long time, int request) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.time = time;
        this.request = request;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public long getTime() {
        return time;
    }

    public void setTime(final long time) {
        this.time = time;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "NoteModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", time=" + time +
                ", request=" + request +
                '}';
    }
}
