package com.dailytodo.widget;

import android.content.Context;
import android.view.View;

import com.dailytodo.Utility.Constants;
import com.dailytodo.Utility.PrefManager;
import com.dailytodo.dashboard.CompletedTaskAdapter;
import com.dailytodo.dashboard.DashboardFragment;
import com.dailytodo.dashboard.PendingTaskAdapter;
import com.dailytodo.model.TaskEntity;
import com.dailytodo.model.UserEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajeev Ranjan -  ABPB on 25-04-2019.
 */
public class WidgetUtility {


    public static List<TaskEntity> getUpcomingTasks(Context context) {

        PrefManager prefManager = new PrefManager(context);
        final UserEntity userEntity = prefManager.getUser();
        DatabaseReference dbTAsk = FirebaseDatabase.getInstance().getReference(Constants.TASK_DB);
        final List<TaskEntity> taskEntities = new ArrayList<>();

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
                        taskEntities.add(taskEntity);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return taskEntities;
    }
}
