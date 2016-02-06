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
    private static final String KEY_REQUEST = "request";

    private static final String CREATE_NOTES_TABLE = "CREATE TABLE " +
            TABLE_NOTES + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_TITLE + " TEXT," +
            KEY_DESCRIPTION + " TEXT," +
            KEY_TIME + " BIGINT," +
            KEY_REQUEST + " INTEGER" +
            ")";

    private final SQLiteDatabase storage;
    private final SQLiteOpenHelper helper;

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

    public void addNote(final NoteModel noteModel) {
        final ContentValues values = new ContentValues();

        values.put(KEY_TITLE, noteModel.getTitle());
        values.put(KEY_DESCRIPTION, noteModel.getDescription());
        values.put(KEY_TIME, noteModel.getTime());
        values.put(KEY_REQUEST, noteModel.getRequest());

        this.storage.insert(TABLE_NOTES, null, values);
    }

    public void deleteNote(final int id) {
        this.storage.delete(TABLE_NOTES, KEY_ID + " = " + id, null);
    }

    public void deleteAll(String whereClause) {
        this.storage.delete(TABLE_NOTES, whereClause, null);
    }

    public NoteModel getNote(final int id) {
        final Cursor cursor = this.storage.query(TABLE_NOTES,
                                new String[] {
                                        KEY_ID,
                                        KEY_TITLE,
                                        KEY_DESCRIPTION,
                                        KEY_TIME,
                                        KEY_REQUEST
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
        final NoteModel noteModel = new NoteModel(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                Long.parseLong(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4)));

        cursor.close();

        return noteModel;
    }

    public List<NoteModel> getAll(final String whatFind) {
        final List<NoteModel> dataNotesList = new ArrayList<>();

        final String selectQuery = "SELECT * FROM " + TABLE_NOTES + " WHERE " + KEY_TIME + whatFind;

        final Cursor cursor = this.storage.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                  NoteModel noteModel = new NoteModel();
                  noteModel.setId(Integer.parseInt(cursor.getString(0)));
                  noteModel.setTitle(cursor.getString(1));
                  noteModel.setDescription(cursor.getString(2));
                  noteModel.setTime(Long.parseLong(cursor.getString(3)));
                  noteModel.setRequest(Integer.parseInt(cursor.getString(4)));

                dataNotesList.add(noteModel);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return dataNotesList;
    }

    public int updateNote(final NoteModel noteModel) {
        final ContentValues values = new ContentValues();

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
