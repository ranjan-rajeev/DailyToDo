package com.dailytodo.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class MyWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(final Intent intent) {

        return new MyWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}