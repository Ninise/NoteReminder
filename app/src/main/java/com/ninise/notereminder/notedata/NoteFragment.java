package com.ninise.notereminder.notedata;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ninise.notereminder.R;

public class NoteFragment extends Fragment {

    private static final String TAG = "NoteListFragment";

    private Button mSaveBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note_layout, container, false);

        mSaveBtn = (Button) v.findViewById(R.id.saveNoteBtn);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "SAVE BUTTON ON CLICK");
                getActivity().onBackPressed();
            }
        });

        return v;
    }
}
