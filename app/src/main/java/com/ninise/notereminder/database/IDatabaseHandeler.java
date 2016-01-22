package com.ninise.notereminder.database;

import java.util.List;

public interface IDatabaseHandeler {

     void addNote(NoteModel noteModel);
     void deleteNote(NoteModel noteModel);
     void deleteAllNotes();
     NoteModel getNote(int id);
     List<NoteModel> getAllNotes();
     int updateNote(NoteModel noteModel);

}
