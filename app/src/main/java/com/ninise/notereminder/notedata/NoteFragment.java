package com.ninise.notereminder.notedata;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ninise.notereminder.R;
import com.ninise.notereminder.database.NoteModel;
import com.ninise.notereminder.database.NoteWorker;
import com.ninise.notereminder.notification.AlarmNotification;
import com.ninise.notereminder.utils.Constants;
import com.ninise.notereminder.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class NoteFragment extends Fragment {

    private static final String TAG = "NoteFragment";

    private EditText mTitleEditText;
    private EditText mDescriptionEditText;
    private TextView mTimeTextView;

    private NoteWorker noteWorker;

    private static int ID;
    private static long TIME;
    private static int REQUEST;

    private SimpleDateFormat date;

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        noteWorker = new NoteWorker(getActivity());

        date = new SimpleDateFormat("dd MMM hh:mm:ss");
    }

    @SuppressLint("SetTextI18n")
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
            REQUEST = getActivity().getIntent().getExtras().getInt(Constants.EXTRA_REQUEST);

            mTitleEditText.setText(title);
            mDescriptionEditText.setText(descript);

            Log.d(TAG, title + "  " + descript +  "  " + REQUEST);
        }


        final Button mSetTimeBtn = (Button) v.findViewById(R.id.setAlarmBtn);

        mTimeTextView = (TextView) v.findViewById(R.id.timeTextView);

        if (TIME > 0) {
            mSetTimeBtn.setText(getString(R.string.change_time_reminder));
            mTimeTextView.setText(getString(R.string.to_time) + " " + date.format(TIME));
        }

        if (Utils.isTextViewEmpty(mTitleEditText) && Utils.isTextViewEmpty(mDescriptionEditText)) {
            mSetTimeBtn.setEnabled(false);
            mSetTimeBtn.setVisibility(View.GONE);
        }

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

                    if (!Utils.isTextViewEmpty(mTitleEditText)) {
                        REQUEST = Utils.generateRequest();
                        noteWorker.addNote(getNote());
                        Log.d(TAG, getNote().toString());
                    }

                } else {
                    noteWorker.updateNote(getNote());
                }

                if (Utils.isTextViewEmpty(mTitleEditText) && Utils.isTextViewEmpty(mDescriptionEditText)) {
                    Toast.makeText(getActivity(), R.string.note_not_created, Toast.LENGTH_SHORT).show();
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
                TIME,
                REQUEST);
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
                .setPositiveButton(R.string.ok_btn, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
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
                        mTimeTextView.setText(getString(R.string.to_time) + " " + date.format(TIME));
                        alarm(getNote());
                    }
                })
                .setNegativeButton(R.string.cancel_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        final AlertDialog alert = builder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextSize(30);
                alert.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(30);
            }
        });
        alert.show();
    }

    private void alarm(NoteModel noteModel) {
        final AlarmNotification alarm = new AlarmNotification(getActivity());
        Log.d(TAG, "setAlarm");
        alarm.setOnceAlarm(TIME, noteModel);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        noteWorker.close();
    }
}
