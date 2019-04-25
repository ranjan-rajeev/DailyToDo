package com.dailytodo.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.dailytodo.R;
import com.dailytodo.model.TaskEntity;

import java.util.ArrayList;
import java.util.List;

public class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;
    List<TaskEntity> taskEntities;

    public MyWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        taskEntities = WidgetUtility.getUpcomingTasks(mContext);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        final long identityToken = Binder.clearCallingIdentity();

        if (taskEntities != null) {
            //taskEntities.add(new TaskEntity("1", "1", "First Task", "", false, System.currentTimeMillis()));
            //taskEntities.add(new TaskEntity("2", "1", "Second Task", "", false, System.currentTimeMillis()));

        }

        Binder.restoreCallingIdentity(identityToken);

    }

    @Override
    public void onDestroy() {
        if (taskEntities != null) {
            taskEntities.clear();
        }
    }

    @Override
    public int getCount() {
        return taskEntities == null ? 0 : taskEntities.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                taskEntities == null) {
            return null;
        }

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_widget_list);
        rv.setTextViewText(R.id.tvTitle, taskEntities.get(position).getName());

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
        //return mCursor.moveToPosition(position) ? mCursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}