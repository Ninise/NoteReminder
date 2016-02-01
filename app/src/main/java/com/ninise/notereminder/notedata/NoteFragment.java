package com.ninise.notereminder.notedata;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.ninise.notereminder.R;
import com.ninise.notereminder.Utils.Constants;
import com.ninise.notereminder.database.NoteModel;
import com.ninise.notereminder.database.NoteWorker;
import com.ninise.notereminder.notification.AlarmNotification;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class NoteFragment extends Fragment {

    private static final String TAG = "NoteListFragment";

    private EditText mTitleEditText;
    private EditText mDescriptionEditText;

    private NoteWorker noteWorker;

    private static int ID;
    private static long TIME;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        noteWorker = new NoteWorker(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_note_layout, container, false);

        mTitleEditText = (EditText) v.findViewById(R.id.titleEditText);
        mDescriptionEditText = (EditText) v.findViewById(R.id.descriptEditText);

        if (getBundleNotNull()) {
            ID = getActivity().getIntent().getExtras().getInt(Constants.EXTRA_ID);
            final String title = getActivity().getIntent().getExtras().getString(Constants.EXTRA_TITLE);
            final String descript = getActivity().getIntent().getExtras().getString(Constants.EXTRA_DESCRIPT);
            TIME = getActivity().getIntent().getExtras().getLong(Constants.EXTRA_TIME);

            mTitleEditText.setText(title);
            mDescriptionEditText.setText(descript);
        }

        final Button mSetTimeBtn = (Button) v.findViewById(R.id.setAlarmBtn);

        mSetTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        final Button mSaveBtn = (Button) v.findViewById(R.id.saveNoteBtn);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getBundleNotNull()) {
                    if (!mTitleEditText.getText().toString().equals("")) {
                        noteWorker.addNote(getNote());
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

    @Override
    public void onPause() {
        super.onPause();
        TIME = 0;
        noteWorker.close();
    }

    @SuppressLint("InflateParams")
    private void showDialog() {
        final LayoutInflater factory = LayoutInflater.from(getActivity());
        final View dateAndTimePicker = factory.inflate(R.layout.date_time_picker, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(dateAndTimePicker)
                .setCancelable(true)
                .setPositiveButton(R.string.on_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatePicker datePicker = (DatePicker) dateAndTimePicker.findViewById(R.id.date_picker);
                        TimePicker timePicker = (TimePicker) dateAndTimePicker.findViewById(R.id.time_picker);

                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute());

                        TIME = calendar.getTimeInMillis();
                        alarm(getNote());
                    }
                });

        final AlertDialog alert = builder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveBtn = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveBtn.setTextSize(30);
            }
        });
        alert.show();
    }

    private void alarm(NoteModel noteModel) {
        final AlarmNotification alarm = new AlarmNotification(getActivity());
        alarm.setOnceAlarm(TIME, noteModel.getDescription());
    }
}
