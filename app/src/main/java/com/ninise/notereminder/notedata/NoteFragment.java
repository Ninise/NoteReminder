package com.ninise.notereminder.notedata;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ninise.notereminder.R;
import com.ninise.notereminder.Utils.Constants;
import com.ninise.notereminder.database.NoteModel;
import com.ninise.notereminder.database.NoteWorker;

public class NoteFragment extends Fragment {

    private static final String TAG = "NoteListFragment";

    private Button mSaveBtn;
    private EditText mTitleEditText;
    private EditText mDescriptionEditText;

    private NoteWorker noteWorker;

    private static int ID;
    private static long TIME;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        noteWorker = new NoteWorker(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note_layout, container, false);

        mTitleEditText = (EditText) v.findViewById(R.id.titleEditText);
        mDescriptionEditText = (EditText) v.findViewById(R.id.descriptEditText);

        if (getBundleNotNull()) {
            ID = getActivity().getIntent().getExtras().getInt(Constants.EXTRA_ID);
            String title = getActivity().getIntent().getExtras().getString(Constants.EXTRA_TITLE);
            String descript = getActivity().getIntent().getExtras().getString(Constants.EXTRA_DESCRIPT);
            TIME = getActivity().getIntent().getExtras().getLong(Constants.EXTRA_TIME);

            mTitleEditText.setText(title);
            mDescriptionEditText.setText(descript);
        }

        mSaveBtn = (Button) v.findViewById(R.id.saveNoteBtn);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getBundleNotNull()) {
                    if (!mTitleEditText.getText().toString().equals("")) {
                        noteWorker.addNote(getNote());
                        Log.d(TAG, " ADDED ");
                    }
                } else {
                    noteWorker.updateNote(getNote());
                }

                getActivity().onBackPressed();
            }
        });

        return v;
    }

    private NoteModel getNote() {
        return new NoteModel (
                ID,
                mTitleEditText.getText().toString(),
                mDescriptionEditText.getText().toString(),
                TIME
        );
    }

    private boolean getBundleNotNull() {
        return (getActivity().getIntent().getExtras() != null);
    }
}
