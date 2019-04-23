package com.dailytodo.taskdetails;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dailytodo.R;
import com.dailytodo.Utility.BaseActivity;
import com.dailytodo.Utility.Constants;
import com.dailytodo.Utility.DateTimePicker;
import com.dailytodo.Utility.MyAlarm;
import com.dailytodo.model.CommentsEntity;
import com.dailytodo.model.TaskEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskDetailsActivity extends BaseActivity implements View.OnClickListener {

    RelativeLayout rlparentView;
    TaskEntity taskEntity;
    private PopupWindow mPopupWindow;
    DatabaseReference dbComments;
    TextView tvTaskTitle, desc;
    RecyclerView rvComments;
    CommentsAdapter commentsAdapter;
    List<CommentsEntity> commentsEntities;
    EditText etDate, etTime, etDetails;
    SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MM-yyyy");
    Calendar dateCAlender, timeCalender, overAllCalender;
    Button fab;
    ImageView ivSave;

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
        fab.setOnClickListener(this);
        ivSave.setOnClickListener(this);

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
        ivSave = findViewById(R.id.ivSave);
        fab = findViewById(R.id.fab);
        tvTaskTitle = (TextView) findViewById(R.id.tvTaskTitle);
        tvTaskTitle.setText("" + taskEntity.getName());
        rlparentView = (RelativeLayout) findViewById(R.id.rlparentView);
        rvComments = (RecyclerView) findViewById(R.id.rvComments);
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        commentsEntities = new ArrayList<>();
        etDate = (EditText) findViewById(R.id.etDate);
        etDetails = (EditText) findViewById(R.id.etDetails);
        etDetails.setText("" + taskEntity.getDesc());
        etTime = (EditText) findViewById(R.id.etTime);
        if (taskEntity.getTime() != null)
            etTime.setText("" + taskEntity.getTime());
        if (taskEntity.getDate() != null)
            etDate.setText("" + taskEntity.getDate());
        etDate.setOnClickListener(datePickerDialog);
        etTime.setOnClickListener(timePickerDialog);
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


    protected View.OnClickListener datePickerDialog = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            hideKeyBoard(view, TaskDetailsActivity.this);

            //region policy etDate
            if (view.getId() == R.id.etDate) {
                DateTimePicker.showDataPickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view1, int year, int monthOfYear, int dayOfMonth) {
                        if (view1.isShown()) {

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            String currentDay = displayFormat.format(calendar.getTime());
                            etDate.setText(currentDay);
                            taskEntity.setDate(etDate.getText().toString());
                            updateTask(taskEntity);
                        }
                    }
                });
            }
            //endregion

        }
    };
    protected View.OnClickListener timePickerDialog = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            hideKeyBoard(view, TaskDetailsActivity.this);

            //region policy etDate
            if (view.getId() == R.id.etTime) {
                DateTimePicker.showTimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), i, i1);
                        etTime.setText(i + ":" + i1);
                        taskEntity.setTime(etTime.getText().toString());
                        updateTask(taskEntity);
                        long diff = calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
                        //Toast.makeText(TaskDetailsActivity.this, "" + diff + "Secs", Toast.LENGTH_SHORT).show();
                        setAlarm(calendar);
                    }
                });
            }
            //endregion

        }
    };

    private void setAlarm(Calendar calendar) {

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(this, MyAlarm.class);
        i.putExtra("TITLE", taskEntity.getName());
        //creating a pending intent using the intent
        PendingIntent pi = PendingIntent.getBroadcast(this, 1000, i, PendingIntent.FLAG_UPDATE_CURRENT);

        //setting the repeating alarm that will be fired every day
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setExact(AlarmManager.RTC, calendar.getTimeInMillis(), pi);
        } else {
            am.set(AlarmManager.RTC, calendar.getTimeInMillis(), pi);
        }
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
    }

    private void setAlarm(long time) {
        //getting the alarm manager
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(this, MyAlarm.class);
        i.putExtra("TITLE", taskEntity.getName());
        //creating a pending intent using the intent
        PendingIntent pi = PendingIntent.getBroadcast(this, 1000, i, PendingIntent.FLAG_UPDATE_CURRENT);

        //setting the repeating alarm that will be fired every day
        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        Toast.makeText(this, "Alarm is set for - " + time + "MilliSEc", Toast.LENGTH_SHORT).show();
    }

    public boolean updateTask(TaskEntity taskEntity) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference(Constants.TASK_DB).child(taskEntity.getTaskId());
        dR.setValue(taskEntity);
        return true;
    }

    public boolean deleteTask() {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference(Constants.TASK_DB).child(taskEntity.getTaskId());
        dR.removeValue();
        return true;
    }

    public TaskEntity getTAskEntity() {
        return this.taskEntity;
    }

    @Override
    public void onClick(View v) {
        hideKeyBoard(ivSave, TaskDetailsActivity.this);
        switch (v.getId()) {
            case R.id.fab:
                showPopUp();
                break;
            case R.id.ivSave:
                taskEntity.setDesc(etDetails.getText().toString());
                updateTask(taskEntity);
                Toast.makeText(this, "Task updated successfully!!!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
