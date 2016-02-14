package com.ninise.notereminder.notedata;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ninise.notereminder.R;
import com.ninise.notereminder.database.NoteModel;
import com.ninise.notereminder.database.NoteWorker;
import com.ninise.notereminder.notification.alarm.AlarmNotification;
import com.ninise.notereminder.settings.preferences.SingletonSharedPreferences;
import com.ninise.notereminder.utils.Constants;
import com.ninise.notereminder.utils.SingletonCameraWorker;
import com.ninise.notereminder.utils.SingletonPhotoLoader;
import com.ninise.notereminder.utils.Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class NoteFragment extends Fragment {

    private static final String TAG = "NoteFragment";

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private EditText mTitleEditText;
    private EditText mDescriptionEditText;
    private TextView mTimeTextView;
    private AppCompatImageView mImageView;

    private NoteWorker noteWorker;

    private static int ID;
    private static long TIME;
    private static int REQUEST;
    private static String PATH;

    private SimpleDateFormat date;

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        noteWorker = new NoteWorker(getActivity());

        date = new SimpleDateFormat("dd MMM hh:mm a");
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_note_layout, container, false);

        mTitleEditText = (EditText) v.findViewById(R.id.titleEditText);
        mDescriptionEditText = (EditText) v.findViewById(R.id.descriptEditText);

        final Button mSetTimeBtn = (Button) v.findViewById(R.id.setAlarmBtn);
        final Button mSaveBtn = (Button) v.findViewById(R.id.saveNoteBtn);

        /** Get data from intent (NoteListFragment or ReminderListFragment or Notification) */
        if (getBundleNotNull()) {
            ID = getActivity().getIntent().getExtras().getInt(Constants.EXTRA_ID);
            final String title = getActivity().getIntent().getExtras().getString(Constants.EXTRA_TITLE);
            final String descript = getActivity().getIntent().getExtras().getString(Constants.EXTRA_DESCRIPT);
            TIME = getActivity().getIntent().getExtras().getLong(Constants.EXTRA_TIME);
            REQUEST = getActivity().getIntent().getExtras().getInt(Constants.EXTRA_REQUEST);
            PATH = getActivity().getIntent().getExtras().getString(Constants.EXTRA_PATH);

            Log.d(TAG, getNote().toString());

            mTitleEditText.setText(title);
            mDescriptionEditText.setText(descript);
        }

        mTimeTextView = (TextView) v.findViewById(R.id.timeTextView);


        /**
         * If note changed to reminder than we need to write the time of reminder and
         * give user a chance for canceling the reminder or changing time of alarm
         **/
        if (TIME > 0) {
            mSetTimeBtn.setText(getString(R.string.change_time_reminder));
            mTimeTextView.setText(getString(R.string.to_time) + " " + date.format(TIME));
        }

        /** Set SetTime Button as visible when note has been create */
        if (Utils.isTextViewEmpty(mTitleEditText) && Utils.isTextViewEmpty(mDescriptionEditText)) {
            mSetTimeBtn.setEnabled(false);
            mSetTimeBtn.setVisibility(View.VISIBLE);
        }


        mSetTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });


        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getBundleNotNull()) {

                    if (!Utils.isTextViewEmpty(mTitleEditText)) {
                        REQUEST = Utils.generateRequest();
                        noteWorker.addNote(getNote());
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

        mImageView = (AppCompatImageView) v.findViewById(R.id.notePhotoImageView);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_note, menu);
        MenuItem item = menu.findItem(R.id.cancelAlarm);
        if (TIME < System.currentTimeMillis()) {
            item.setVisible(false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.cancelAlarm:
                cancelAlarm(getNote());
                return true;

            case R.id.takePhoto:

                if (SingletonCameraWorker.getInstance(getActivity()).openCamera().
                        resolveActivity(getActivity().getPackageManager()) != null) {

                    startActivityForResult(
                            SingletonCameraWorker
                            .getInstance(getActivity())
                            .openCamera()
                            .putExtra(
                                    MediaStore.EXTRA_OUTPUT,
                                    SingletonPhotoLoader.getInstance(getActivity()).save()
                            ),
                            REQUEST_IMAGE_CAPTURE
                    );
                            PATH = SingletonPhotoLoader.getInstance(getActivity()).save().toString();
                } else {

                    Toast.makeText(getActivity(), R.string.no_camera, Toast.LENGTH_LONG).show();

                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private NoteModel getNote() {
        return new NoteModel (
                ID,
                mTitleEditText.getText().toString(),
                mDescriptionEditText.getText().toString(),
                TIME,
                REQUEST,
                PATH);
    }

    private boolean getBundleNotNull() {
        return (getActivity().getIntent().getExtras() != null);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {

            if (PATH != null && !PATH.equals("")) {
                mImageView.setImageBitmap(SingletonPhotoLoader.getInstance(getActivity()).load(PATH));
                PATH = "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        TIME = 0;
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
                        setAlarm(getNote());
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

    private void setAlarm(NoteModel noteModel) {
        final AlarmNotification alarm = new AlarmNotification(getActivity());
        alarm.setOnceAlarm(TIME, noteModel);
    }

    private void cancelAlarm(NoteModel noteModel) {
        TIME = 0;
        final AlarmNotification alarm = new AlarmNotification(getActivity());
        alarm.cancelAlarm(noteModel);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
