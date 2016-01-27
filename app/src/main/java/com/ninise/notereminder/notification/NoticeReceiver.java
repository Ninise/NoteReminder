package com.ninise.notereminder.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.ninise.notereminder.R;

public class NoticeReceiver extends BroadcastReceiver {

    private static final String TAG = "NoticeReceiver";

    private static final String ContentText = "notice";

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_nr)
                        .setContentTitle(context.getString(R.string.app_name))
                        .setContentText(intent.getStringExtra(ContentText));

        Notification notification = mBuilder.build();

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(101, notification);
    }

}
