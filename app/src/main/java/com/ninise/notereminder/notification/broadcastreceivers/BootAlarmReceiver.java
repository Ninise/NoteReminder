package com.ninise.notereminder.notification.broadcastreceivers;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.ninise.notereminder.notification.service.BootAlarmService;

public class BootAlarmReceiver extends WakefulBroadcastReceiver{

    private static final String TAG = "BootAlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, BootAlarmService.class);

        Log.i(TAG, "Starting service @ " + SystemClock.elapsedRealtime());
        startWakefulService(context, service);
    }

}
