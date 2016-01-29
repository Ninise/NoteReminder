package com.ninise.notereminder.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.ninise.notereminder.R;
import com.ninise.notereminder.Utils.Constants;

public class NoticeReceiver extends BroadcastReceiver {

    private static final String TAG = "NoticeReceiver";

    @Override
    public void onReceive(final Context context, final Intent intent) {

        final NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_nr)
                        .setContentTitle(context.getString(R.string.app_name))
                        .setContentText(intent.getStringExtra(Constants.CONTENT_TEXT));

        final Notification notification = mBuilder.build();

        final NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(TAG, 0, notification);
    }

}
