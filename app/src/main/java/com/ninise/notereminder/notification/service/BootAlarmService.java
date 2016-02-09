package com.ninise.notereminder.notification.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.ninise.notereminder.database.NoteModel;
import com.ninise.notereminder.database.NoteWorker;
import com.ninise.notereminder.notification.alarm.AlarmNotification;
import com.ninise.notereminder.notification.broadcastreceivers.BootAlarmReceiver;

import java.util.ArrayList;
import java.util.List;

public class BootAlarmService extends IntentService {

    public static final String TAG = "BootAlarmService";

    public BootAlarmService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        List<NoteModel> noteList = new NoteWorker(getApplicationContext()).getAllReminders();

        for (NoteModel note: noteList) {
            if (note.getTime() >= System.currentTimeMillis()) {
                new AlarmNotification(getApplicationContext()).setOnceAlarm(note.getTime(), note);
            }
        }

        Log.i(TAG, "Completed service @ " + SystemClock.elapsedRealtime());
        BootAlarmReceiver.completeWakefulIntent(intent);
    }
}
