package com.dailytodo.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.dailytodo.R;
import com.dailytodo.Utility.Constants;
import com.dailytodo.model.TaskEntity;

import java.util.ArrayList;
import java.util.List;

class ListViewRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;

    private List<TaskEntity> records;


    public ListViewRemoteViewsFactory(Context context, Intent intent) {

        mContext = context;
    }

    // Initialize the data set.

    public void onCreate() {

        // In onCreate() you set up any connections / cursors to your data source. Heavy lifting,

        // for example downloading or creating content etc, should be deferred to onDataSetChanged()

        // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.

        if (Constants.pendTask != null)
            records = Constants.pendTask;
        else
            records = new ArrayList<>();

    }

    // Given the position (index) of a WidgetItem in the array, use the item's text value in

    // combination with the app widget item XML file to construct a RemoteViews object.

    public RemoteViews getViewAt(int position) {

        // position will always range from 0 to getCount() - 1.

        // Construct a RemoteViews item based on the app widget item XML file, and set the

        // text based on the position.

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);

        // feed row

        if(records!=null && records.size() >0){

            TaskEntity data = records.get(position);


            rv.setTextViewText(R.id.tvTitle, data.getName());

            // end feed row

            // Next, set a fill-intent, which will be used to fill in the pending intent template

            // that is set on the collection view in ListViewWidgetProvider.

            Bundle extras = new Bundle();

            //extras.putInt(MyWidgetProvider.EXTRA_ITEM, position);

            Intent fillInIntent = new Intent();

            fillInIntent.putExtra("homescreen_meeting", data);

            fillInIntent.putExtras(extras);

            // Make it possible to distinguish the individual on-click

            // action of a given item

            rv.setOnClickFillInIntent(R.id.tvTitle, fillInIntent);
            // Return the RemoteViews object.


        }


        return rv;

    }

    public int getCount() {

        return records.size();

    }

    public void onDataSetChanged() {

        // Fetching JSON data from server and add them to records arraylist


    }

    public int getViewTypeCount() {

        return 1;

    }

    public long getItemId(int position) {

        return position;

    }

    public void onDestroy() {

        records.clear();

    }

    public boolean hasStableIds() {

        return true;

    }

    public RemoteViews getLoadingView() {

        return null;

    }

}
