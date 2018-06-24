package com.dailytodo.taskdetails;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dailytodo.R;
import com.dailytodo.Utility.BaseActivity;
import com.dailytodo.Utility.Constants;
import com.dailytodo.dashboard.CompletedTaskAdapter;
import com.dailytodo.dashboard.DashboardFragment;
import com.dailytodo.dashboard.PendingTaskAdapter;
import com.dailytodo.model.CommentsEntity;
import com.dailytodo.model.TaskEntity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TaskDetailsActivity extends BaseActivity {

    RelativeLayout rlparentView;
    TaskEntity taskEntity;
    private PopupWindow mPopupWindow;
    DatabaseReference dbComments;
    TextView tvTaskTitle, desc;
    RecyclerView rvComments;
    CommentsAdapter commentsAdapter;
    List<CommentsEntity> commentsEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        if (getIntent().hasExtra("TASK")) {
            taskEntity = getIntent().getParcelableExtra("TASK");
        }
        init_Widgets();
        dbComments = FirebaseDatabase.getInstance().getReference(Constants.COMMENTS_DB);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp();
            }
        });

        //region comments listener
        dbComments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentsEntities.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    CommentsEntity commentsEntity = postSnapshot.getValue(CommentsEntity.class);
                    if (taskEntity.getTaskId().equals(commentsEntity.getTaskId())) {
                        commentsEntities.add(commentsEntity);
                    }
                }
                commentsAdapter = new CommentsAdapter(TaskDetailsActivity.this, commentsEntities);
                rvComments.setAdapter(commentsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //endregion
    }

    private void init_Widgets() {
        tvTaskTitle = (TextView) findViewById(R.id.tvTaskTitle);
        tvTaskTitle.setText("" + taskEntity.getName());
        desc = (TextView) findViewById(R.id.desc);
        desc.setText("" + taskEntity.getDesc());
        rlparentView = (RelativeLayout) findViewById(R.id.rlparentView);
        rvComments = (RecyclerView) findViewById(R.id.rvComments);
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        commentsEntities = new ArrayList<>();
    }

    private void showPopUp() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                rlparentView.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.layout_add_task, null);
        mPopupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // Call requires API level 21
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }

        final EditText etTAskTitle = (EditText) customView.findViewById(R.id.etTAskTitle);

        etTAskTitle.setHint("Enter comments");
        TextView btnSave = (TextView) customView.findViewById(R.id.btnSave);
        etTAskTitle.requestFocus();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                if (!etTAskTitle.getText().toString().trim().equals("")) {
                    addComment(etTAskTitle.getText().toString().trim());
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etTAskTitle.getWindowToken(), 0);
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etTAskTitle.getWindowToken(), 0);
            }
        });
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.showAtLocation(rlparentView, Gravity.CENTER, 0, 800);
    }

    private void addComment(String comment) {
        String id = dbComments.push().getKey();
        CommentsEntity commentsEntity = new CommentsEntity(taskEntity.getTaskId(), id, comment);
        dbComments.child(id).setValue(commentsEntity);
    }
}
