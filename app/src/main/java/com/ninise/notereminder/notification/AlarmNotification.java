package com.ninise.notereminder.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.ninise.notereminder.Utils.Constants;

public class AlarmNotification {

    private Context context;

    public AlarmNotification (Context c) {
        this.context = c;
    }

    public void setMultiAlarm(long time, int repeatTime, String description) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, NoticeReceiver.class);

        intent.putExtra(Constants.NOTICE_ACTION, Boolean.FALSE);
        intent.putExtra(Constants.CONTENT_TEXT, description);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, time, repeatTime, pendingIntent);
    }

    public void cancelAlarm() {
        Intent intent = new Intent(context, NoticeReceiver.class);

        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        manager.cancel(sender);
    }

    public void setOnceAlarm(long time, String description) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, NoticeReceiver.class);

        intent.putExtra(Constants.NOTICE_ACTION, Boolean.FALSE);
        intent.putExtra(Constants.CONTENT_TEXT, description);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        manager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }


}
