package com.ninise.notereminder.database;

import android.content.Context;
import android.util.Log;

import java.util.List;

public class NoteWorker implements IDatabaseHandeler {

    private static final String TAG = "NoteWorker";

    private DatabaseHandler db;

    public NoteWorker (Context context) {
        db = new DatabaseHandler(context);
    }

    @Override
    public void addNote(NoteModel noteModel) {
        db.addNote(noteModel);
        Log.d(TAG, " ADD NOTE " + noteModel.toString());
    }

    @Override
    public void deleteNote(NoteModel noteModel) {
        db.deleteNote(noteModel);
        Log.d(TAG, " DELETE NOTE " + noteModel.toString());
    }

    @Override
    public void deleteAllNotes() {
        db.deleteAllNotes();
        Log.d(TAG, " DELETE ALL NOTES");
    }

    @Override
    public NoteModel getNote(int id) {
        Log.d(TAG, " GET NOTE " + id);
        return db.getNote(id);
    }

    @Override
    public List<NoteModel> getAllNotes() {
        Log.d(TAG, " GET ALL NOTES ");
        return db.getAllNotes();
    }

    @Override
    public int updateNote(NoteModel noteModel) {
        Log.d(TAG, " UPDATE NOTE " + noteModel.toString());
        return db.updateNote(noteModel);
    }

    @Override
    public List<NoteModel> getAllReminders() {
        Log.d(TAG, " GET ALL REMINDERS");
        return db.getAllReminders();
    }
}
