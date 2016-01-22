package com.ninise.notereminder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandeler {

    public static final String TAG = "DatabaseHandler";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notereminder";
    private static final String TABLE_NOTES = "notes";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_TIME = "time";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " +
                TABLE_NOTES +
                "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_TITLE + " TEXT," +
                KEY_DESCRIPTION + " TEXT," +
                KEY_TIME + " BIGINT" +
                ")";
        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NOTES);

        onCreate(db);
    }

    @Override
    public void addNote(NoteModel noteModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, noteModel.getTitle());
        values.put(KEY_DESCRIPTION, noteModel.getDescription());
        values.put(KEY_TIME, noteModel.getTime());

        db.insert(TABLE_NOTES, null, values);
        db.close();
    }

    @Override
    public void deleteNote(NoteModel noteModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(
                TABLE_NOTES,
                KEY_ID + " = ?",
                new String[] {String.valueOf(noteModel.getId())}
        );
        db.close();
    }

    @Override
    public void deleteAllNotes() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NOTES, null, null);
        db.close();
    }

    @Override
    public NoteModel getNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTES,
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

        return noteModel;
    }

    @Override
    public List<NoteModel> getAllNotes() {
        List<NoteModel> dataNotesList = new ArrayList<>();
        String selectQuety = "SELECT * FROM " + TABLE_NOTES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuety, null);

        if (cursor.moveToFirst()) {
            do {
                  NoteModel noteModel = new NoteModel();
                  noteModel.setId(Integer.parseInt(cursor.getString(0)));
                  noteModel.setTitle(cursor.getString(1));
                  noteModel.setDescription(cursor.getString(2));
                  noteModel.setTime(Long.parseLong(cursor.getString(3)));

                dataNotesList.add(noteModel);
            } while (cursor.moveToNext());
        }

        return dataNotesList;
    }

    @Override
    public int updateNote(NoteModel noteModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, noteModel.getTitle());
        values.put(KEY_DESCRIPTION, noteModel.getDescription());
        values.put(KEY_TITLE, noteModel.getTime());

        return db.update(TABLE_NOTES,
                values,
                KEY_ID + " = ?",
                new String[] { String.valueOf(noteModel.getId()) });
    }
}
