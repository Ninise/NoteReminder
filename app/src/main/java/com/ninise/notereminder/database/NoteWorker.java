package com.ninise.notereminder.database;

import android.content.Context;

import com.ninise.notereminder.utils.Constants;

import java.util.List;

public class NoteWorker {

    private static final String TAG = "NoteWorker";

    private final DatabaseHandler dbHandler;

    public NoteWorker (final Context context) {
        this.dbHandler = new DatabaseHandler(context);
    }

    public void addNote(final NoteModel noteModel) {
        this.dbHandler.addNote(noteModel);
    }

    public void deleteNote(final NoteModel noteModel) {
        this.dbHandler.deleteNote(noteModel.getId());
    }

    public void deleteAll(String whereClause) {
        if (whereClause.equals(Constants.DELETE_NOTES)) {
            this.dbHandler.deleteAll(Constants.CONTENT_TIME + Constants.DELETE_NOTES);
        } else {
            this.dbHandler.deleteAll(Constants.CONTENT_TIME + Constants.DELETE_REMINDERS);
        }
    }

    public NoteModel getNote(final int id) {
        return this.dbHandler.getNote(id);
    }

    public List<NoteModel> getAllNotes() {
        return this.dbHandler.getAll(Constants.FIND_NOTE);
    }

    public int updateNote(final NoteModel noteModel) {
        return this.dbHandler.updateNote(noteModel);
    }

    public List<NoteModel> getAllReminders() {
        return this.dbHandler.getAll(Constants.FIND_REMINDER);
    }

    public void close() {
        dbHandler.close();
    }
}
