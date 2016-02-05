package com.ninise.notereminder.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;

import com.ninise.notereminder.R;
import com.ninise.notereminder.database.NoteWorker;
import com.ninise.notereminder.utils.Constants;

public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";

    private NoteWorker mNoteWorker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNoteWorker = new NoteWorker(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings_layout, container, false);

        final CheckedTextView mDeleteNotesCheckedTextView = (CheckedTextView) v.findViewById(R.id.deleteAllNotesCheckedTextView);
        mDeleteNotesCheckedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CheckedTextView) v).toggle();
            }
        });

        final CheckedTextView mDeleteRemindersCheckedTextView = (CheckedTextView) v.findViewById(R.id.deleteAllRemindersCheckedTextView);
        mDeleteRemindersCheckedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CheckedTextView) v).toggle();
            }
        });

        final Button saveBtn = (Button) v.findViewById(R.id.settingsSaveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDeleteNotesCheckedTextView.isChecked()) {
                    mNoteWorker.deleteAll(Constants.DELETE_NOTES);
                }
                if (mDeleteRemindersCheckedTextView.isChecked()) {
                    mNoteWorker.deleteAll(Constants.DELETE_REMINDERS);
                }

                getActivity().onBackPressed();
            }
        });

        return v;
    }
}
