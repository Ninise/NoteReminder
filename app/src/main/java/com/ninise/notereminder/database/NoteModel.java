package com.ninise.notereminder.database;

public class NoteModel {

    private int id;
    private String title;
    private String description;
    private long time;

    public NoteModel () {

    }

    public NoteModel (String title, String description) {
        this.title = title;
        this.description = description;
    }

    public NoteModel (int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public NoteModel (int id, String title, String description, long time) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "NoteModel {" +
                "id = " + id +
                ", title = '" + title + '\'' +
                ", description = '" + description + '\'' +
                ", time = " + time +
                '}';
    }
}
