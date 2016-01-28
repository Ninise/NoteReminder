package com.ninise.notereminder.database;

import android.content.Context;

import com.ninise.notereminder.Utils.Constants;

import java.util.List;

public class NoteWorker {

    private static final String TAG = "NoteWorker";

    private DatabaseHandler dbHandler;

    public NoteWorker (Context context) {
        this.dbHandler = new DatabaseHandler(context);
    }

    public void addNote(NoteModel noteModel) {
        this.dbHandler.addNote(noteModel);
    }

    public void deleteNote(NoteModel noteModel) {
        this.dbHandler.deleteNote(noteModel.getId());
    }

    public void deleteAllNotes() {
        this.dbHandler.deleteAllNotes();
    }

    public NoteModel getNote(int id) {
        return this.dbHandler.getNote(id);
    }

    public List<NoteModel> getAllNotes() {
        return this.dbHandler.getAll(Constants.FIND_NOTE);
    }

    public int updateNote(NoteModel noteModel) {
        return this.dbHandler.updateNote(noteModel);
    }

    public List<NoteModel> getAllReminders() {
        return this.dbHandler.getAll(Constants.FIND_REMINDER);
    }

    public void close() {
        dbHandler.close();
    }
}
