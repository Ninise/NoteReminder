package com.ninise.notereminder.notification.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.ninise.notereminder.notification.broadcastreceivers.BootAlarmReceiver;

public class BootAlarmService extends IntentService {

    public static final String TAG = "BootAlarmService";

    public BootAlarmService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Completed service @ " + SystemClock.elapsedRealtime());
        BootAlarmReceiver.completeWakefulIntent(intent);
    }
}
