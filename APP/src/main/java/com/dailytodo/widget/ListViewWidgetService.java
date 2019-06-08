package com.dailytodo.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class ListViewWidgetService extends RemoteViewsService {


    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new ListViewRemoteViewsFactory(this.getApplicationContext(), intent);

    }

}