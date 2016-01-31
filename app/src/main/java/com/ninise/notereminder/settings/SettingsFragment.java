package com.ninise.notereminder.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;

import com.ninise.notereminder.R;

public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings_layout, container, false);

        CheckedTextView mDeleteNotesCheckedTextView = (CheckedTextView) v.findViewById(R.id.deleteAllNotesCheckedTextView);
        mDeleteNotesCheckedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CheckedTextView) v).toggle();
            }
        });

        CheckedTextView mDeleteRemindersCheckedTextView = (CheckedTextView) v.findViewById(R.id.deleteAllRemindersCheckedTextView);
        mDeleteRemindersCheckedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CheckedTextView) v).toggle();
            }
        });

        Button saveBtn = (Button) v.findViewById(R.id.settingsSaveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return v;
    }
}
