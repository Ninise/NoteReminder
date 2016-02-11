package com.ninise.notereminder.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ninise.notereminder.R;
import com.ninise.notereminder.database.NoteWorker;
import com.ninise.notereminder.settings.preferences.SingletonSharedPreferences;
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

        final AppCompatCheckBox mDeleteNotesChecked = (AppCompatCheckBox) v.findViewById(R.id.deleteAllNotesChecked);

        final AppCompatCheckBox mDeleteRemindersChecked = (AppCompatCheckBox) v.findViewById(R.id.deleteAllRemindersChecked);

        final SwitchCompat mVibrateSwitch = (SwitchCompat) v.findViewById(R.id.vibrateSwitch);
        final SwitchCompat mSoundSwitch = (SwitchCompat) v.findViewById(R.id.soundSwitch);
        final SwitchCompat mPhotoSwitch = (SwitchCompat) v.findViewById(R.id.photoSwitch);

        mVibrateSwitch.setChecked(SingletonSharedPreferences.getInstance(getActivity()).getVibrateStatus());
        mSoundSwitch.setChecked(SingletonSharedPreferences.getInstance(getActivity()).getSoundStatus());
        mPhotoSwitch.setChecked(SingletonSharedPreferences.getInstance(getActivity()).getPhotoStatus());

        final Button saveBtn = (Button) v.findViewById(R.id.settingsSaveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDeleteNotesChecked.isChecked()) {
                    mNoteWorker.deleteAll(Constants.DELETE_NOTES);
                }
                if (mDeleteRemindersChecked.isChecked()) {
                    mNoteWorker.deleteAll(Constants.DELETE_REMINDERS);
                }

                SingletonSharedPreferences.getInstance(getActivity()).setVibrateStatus(mVibrateSwitch.isChecked());
                SingletonSharedPreferences.getInstance(getActivity()).setSoundStatus(mSoundSwitch.isChecked());
                SingletonSharedPreferences.getInstance(getActivity()).setPhotoStatus(mPhotoSwitch.isChecked());

                getActivity().onBackPressed();
            }
        });

        return v;
    }
}
