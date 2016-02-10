package com.ninise.notereminder.utils;

import com.ninise.notereminder.database.NoteModel;

import java.util.Comparator;

public class NoteComparator implements Comparator<NoteModel> {
    @Override
    public int compare(NoteModel lhs, NoteModel rhs) {
        if (lhs.getId() > rhs.getId())
            return -1;
        else
            return 1;
    }
}
