package com.dailytodo.Utility;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.dailytodo.model.TaskEntity;
import com.dailytodo.widget.ToDoAppWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajeev Ranjan on 24-Jun-18.
 */

public class Constants {
    public static final String USER_DB = "Users";
    public static final String TASK_DB = "Tasks";
    public static final String COMMENTS_DB = "Comments";
    public static List<TaskEntity> pendTask = new ArrayList<>();

    public static void sendRefreshBroadcast(Context context) {
        if (context != null) {
            Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.setComponent(new ComponentName(context, ToDoAppWidget.class));
            context.sendBroadcast(intent);
        }
    }
}
