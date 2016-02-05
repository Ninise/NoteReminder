package com.ninise.notereminder.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v7.app.NotificationCompat;

import com.ninise.notereminder.R;
import com.ninise.notereminder.utils.Constants;
import com.ninise.notereminder.notedata.NoteActivity;

public class NoticeReceiver extends BroadcastReceiver {

    private static final String TAG = "NoticeReceiver";

    @Override
    public void onReceive(final Context context, final Intent intent) {

        Intent notificationIntent = new Intent(context, NoteActivity.class);

        notificationIntent.putExtra(Constants.EXTRA_DESCRIPT, intent.getStringExtra(Constants.EXTRA_DESCRIPT));
        notificationIntent.putExtra(Constants.EXTRA_TITLE, intent.getStringExtra(Constants.EXTRA_TITLE));
        notificationIntent.putExtra(Constants.EXTRA_TIME, intent.getLongExtra(Constants.EXTRA_TIME, 0));
        notificationIntent.putExtra(Constants.EXTRA_ID, intent.getIntExtra(Constants.EXTRA_ID, 0));

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);

        final NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_nr)
                        .setContentTitle(context.getString(R.string.app_name))
                        .setContentText(intent.getStringExtra(Constants.EXTRA_DESCRIPT))
                        .setVibrate(new long[] {1000, 1000, 1000, 1000, 1000} )
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent);

        final Notification notification = mBuilder.build();

        final NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(TAG, 0, notification);

        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
