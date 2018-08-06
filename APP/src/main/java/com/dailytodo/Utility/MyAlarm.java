package com.dailytodo.Utility;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.dailytodo.R;
import com.dailytodo.home.HomeActivity;
import com.dailytodo.model.TaskEntity;
import com.dailytodo.taskdetails.TaskDetailsActivity;


//class extending the Broadcast Receiver
public class MyAlarm extends BroadcastReceiver {

    public static final String CHANNEL_ID = "com.dailytodo";
    public static final String CHANNEL_NAME = "DAILYTODO_CHANNEL";
    int NOTIFICATION_ID = 0;
    private NotificationManager mManager;

    //the method will be fired when the alarm is triggerred
    Context context,kjghdj,lihfjh;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        addNotification(context, intent);
    }

    private void addNotification(Context context, Intent i) {
        if (context instanceof TaskDetailsActivity) {
            TaskEntity taskEntity = ((TaskDetailsActivity) context).getTAskEntity();
        }
        Intent intent = new Intent(context, HomeActivity.class);
        NotificationCompat.BigTextStyle BigTextstyle = new NotificationCompat.BigTextStyle()
                .bigText("This is the description")
                .setBigContentTitle("Title")
                .setSummaryText("Summary");
        createChannels();

        NotificationCompat.Builder notificationBuilder = null;

        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) Math.round(Math.random() * 1000), intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);

        notificationBuilder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentTitle(i.getExtras().getString("TITLE"))
                .setContentText("Gentle Reminder ")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setWhen(System.currentTimeMillis())
                .setVisibility(View.VISIBLE)
                .setChannelId(CHANNEL_ID)
                .setContentIntent(pendingIntent);


        getManager().notify(NOTIFICATION_ID, notificationBuilder.build());


        /*NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(context, HomeActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());*/
    }

    public void createChannels() {
        if (Build.VERSION.SDK_INT >= 26) {


            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.BLUE);
            channel.setDescription("Finmart");
            // Sets whether notifications posted to this channel appear on the lockscreen or not
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);    // Notification.VISIBILITY_PRIVATE
            getManager().createNotificationChannel(channel);
        }
    }

    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
}