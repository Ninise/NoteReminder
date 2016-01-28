package com.ninise.notereminder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {

    public static final String TAG = "DatabaseHandler";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notereminder";
    private static final String TABLE_NOTES = "notes";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_TIME = "time";

    private static final String CREATE_NOTES_TABLE = "CREATE TABLE " +
            TABLE_NOTES + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_TITLE + " TEXT," +
            KEY_DESCRIPTION + " TEXT," +
            KEY_TIME + " BIGINT" +
            ")";

    private SQLiteDatabase storage;
    private SQLiteOpenHelper helper;

    public DatabaseHandler(final Context ctx) {
        this.helper = new SQLiteOpenHelper(ctx, DATABASE_NAME, null, DATABASE_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(CREATE_NOTES_TABLE);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXIST " + TABLE_NOTES);
                onCreate(db);
            }
        };

        this.storage = this.helper.getWritableDatabase();
    }

    public void addNote(NoteModel noteModel) {

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, noteModel.getTitle());
        values.put(KEY_DESCRIPTION, noteModel.getDescription());
        values.put(KEY_TIME, noteModel.getTime());

        this.storage.insert(TABLE_NOTES, null, values);
    }

    public void deleteNote(int id) {
        this.storage.delete(TABLE_NOTES, KEY_ID + " = " + id, null);
    }

    public void deleteAllNotes() {
        this.storage.delete(TABLE_NOTES, null, null);
    }

    public NoteModel getNote(int id) {
        Cursor cursor = this.storage.query(TABLE_NOTES,
                                new String[] {
                                        KEY_ID,
                                        KEY_TITLE,
                                        KEY_DESCRIPTION,
                                        KEY_TIME
                                }, KEY_ID + " = ?",
                                new String[] { String.valueOf(id) },
                                null,
                                null,
                                null,
                                null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        assert cursor != null;
        NoteModel noteModel = new NoteModel(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                Long.parseLong(cursor.getString(3))
        );

        cursor.close();

        return noteModel;
    }

    public List<NoteModel> getAll(String whatFind) {
        List<NoteModel> dataNotesList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NOTES + " WHERE " + KEY_TIME + whatFind;

        Cursor cursor = this.storage.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                  NoteModel noteModel = new NoteModel();
                  noteModel.setId(Integer.parseInt(cursor.getString(0)));
                  noteModel.setTitle(cursor.getString(1));
                  noteModel.setDescription(cursor.getString(2));
                  noteModel.setTime(Long.parseLong(cursor.getString(3)));

                dataNotesList.add(noteModel);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return dataNotesList;
    }

    public int updateNote(NoteModel noteModel) {
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, noteModel.getTitle());
        values.put(KEY_DESCRIPTION, noteModel.getDescription());
        values.put(KEY_TIME, noteModel.getTime());

        return this.storage.update(TABLE_NOTES, values, KEY_ID + " = " + noteModel.getId(), null);
    }

    public void close() {
        this.storage.close();
        this.helper.close();
    }
}
