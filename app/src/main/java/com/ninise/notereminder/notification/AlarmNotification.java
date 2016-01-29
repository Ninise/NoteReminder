package com.ninise.notereminder.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.ninise.notereminder.Utils.Constants;

public class AlarmNotification {

    private final Context context;

    public AlarmNotification (final Context c) {
        this.context = c;
    }

    public void setMultiAlarm(final long time, final int repeatTime, final String description) {
        final AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        final Intent intent = new Intent(context, NoticeReceiver.class);

        intent.putExtra(Constants.NOTICE_ACTION, Boolean.FALSE);
        intent.putExtra(Constants.CONTENT_TEXT, description);

        final PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, time, repeatTime, pendingIntent);
    }

    public void cancelAlarm() {
        final Intent intent = new Intent(context, NoticeReceiver.class);

        final PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

        final AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        manager.cancel(sender);
    }

    public void setOnceAlarm(final long time, final String description) {
        final AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        final Intent intent = new Intent(context, NoticeReceiver.class);

        intent.putExtra(Constants.NOTICE_ACTION, Boolean.FALSE);
        intent.putExtra(Constants.CONTENT_TEXT, description);

        final PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        manager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }


}
