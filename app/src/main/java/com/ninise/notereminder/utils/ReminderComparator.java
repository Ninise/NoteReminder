package com.ninise.notereminder.utils;

import com.ninise.notereminder.database.NoteModel;

import java.util.Comparator;

public class ReminderComparator implements Comparator<NoteModel> {
    @Override
    public int compare(NoteModel lhs, NoteModel rhs) {
        if (lhs.getTime() < rhs.getTime())
            return 1;
        else
            return -1;
    }
}
