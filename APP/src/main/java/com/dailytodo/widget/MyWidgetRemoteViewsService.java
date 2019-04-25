package com.dailytodo.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.dailytodo.Utility.Constants;
import com.dailytodo.Utility.PrefManager;
import com.dailytodo.model.TaskEntity;
import com.dailytodo.model.UserEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(final Intent intent) {

        return new MyWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}