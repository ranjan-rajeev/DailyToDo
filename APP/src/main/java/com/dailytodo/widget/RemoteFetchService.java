package com.dailytodo.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;

import com.dailytodo.Utility.Constants;
import com.dailytodo.Utility.PrefManager;
import com.dailytodo.model.TaskEntity;
import com.dailytodo.model.UserEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class RemoteFetchService extends Service {

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    public static List<TaskEntity> listItemList;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    /*
     * Retrieve appwidget id from intent it is needed to update widget later
     * initialize our AQuery class
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_ID))
            appWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);


        PrefManager prefManager = new PrefManager(getBaseContext());
        final UserEntity userEntity = prefManager.getUser();
        DatabaseReference dbTAsk = FirebaseDatabase.getInstance().getReference(Constants.TASK_DB);

        dbTAsk.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    TaskEntity taskEntity = postSnapshot.getValue(TaskEntity.class);
                    if (taskEntity.getUserId().equals(userEntity.getUserId())) {
                        /*if (taskEntity.isStatus()) {
                            completedTAsk.add(taskEntity);
                        } else {
                            pendingTask.add(taskEntity);
                        }*/
                        listItemList.add(taskEntity);
                    }

                }
                populateWidget();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //listItemList = WidgetUtility.getUpcomingTasks(getBaseContext());
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Method which sends broadcast to WidgetProvider
     * so that widget is notified to do necessary action
     * and here action == WidgetProvider.DATA_FETCHED
     */
    private void populateWidget() {

        Intent widgetUpdateIntent = new Intent();
        widgetUpdateIntent.setAction(MyWidgetProvider.DATA_FETCHED);
        widgetUpdateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                appWidgetId);
        sendBroadcast(widgetUpdateIntent);

        this.stopSelf();
    }
}