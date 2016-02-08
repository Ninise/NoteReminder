package com.ninise.notereminder.notification.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.ninise.notereminder.database.NoteModel;
import com.ninise.notereminder.notification.broadcastreceivers.NoticeReceiver;
import com.ninise.notereminder.utils.Constants;

public class AlarmNotification {

    private final Context context;

    public AlarmNotification (final Context c) {
        this.context = c;
    }

    public void setMultiAlarm(final long time, final int repeatTime, final NoteModel noteModel) {
        final AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        final Intent intent = new Intent(context, NoticeReceiver.class);

        intent.putExtra(Constants.NOTICE_ACTION, Boolean.FALSE);
        intent.putExtra(Constants.EXTRA_DESCRIPT, noteModel.getDescription());

        final PendingIntent pendingIntent = PendingIntent.getBroadcast(context, noteModel.getRequest(), intent, 0);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, time, repeatTime, pendingIntent);
    }

    public void cancelAlarm(NoteModel noteModel) {
        final Intent intent = new Intent(context, NoticeReceiver.class);

        final PendingIntent sender = PendingIntent.getBroadcast(context, noteModel.getId(), intent, 0);

        final AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        manager.cancel(sender);
    }

    public void setOnceAlarm(final long time, final NoteModel noteModel) {
        final AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        final Intent intent = new Intent(context, NoticeReceiver.class);

        intent.putExtra(Constants.NOTICE_ACTION, Boolean.FALSE);
        intent.putExtra(Constants.EXTRA_DESCRIPT, noteModel.getDescription());
        intent.putExtra(Constants.EXTRA_TITLE, noteModel.getTitle());
        intent.putExtra(Constants.EXTRA_TIME, noteModel.getTime());
        intent.putExtra(Constants.EXTRA_ID, noteModel.getId());
        intent.putExtra(Constants.EXTRA_REQUEST, noteModel.getRequest());

        final PendingIntent pendingIntent = PendingIntent.getBroadcast(context, noteModel.getRequest(), intent, 0);

        manager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }


}
