package com.dailytodo.dashboard;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dailytodo.R;
import com.dailytodo.Utility.BaseFragment;
import com.dailytodo.Utility.Constants;
import com.dailytodo.Utility.PrefManager;
import com.dailytodo.model.TaskEntity;
import com.dailytodo.model.UserEntity;
import com.dailytodo.taskdetails.TaskDetailsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends BaseFragment {

    RecyclerView rvPending, rvCompleted;
    PendingTaskAdapter pendingTaskAdapter;
    CompletedTaskAdapter completedTaskAdapter;
    PrefManager prefManager;
    UserEntity userEntity;
    List<TaskEntity> totalTask, pendingTask, completedTAsk;
    private PopupWindow mPopupWindow;
    RelativeLayout rlparentView;
    DatabaseReference dbTAsk;
    ValueEventListener addValueEventListener;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initialise(view);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp();
            }
        });
        prefManager = new PrefManager(getActivity());
        userEntity = prefManager.getUser();
        dbTAsk = FirebaseDatabase.getInstance().getReference(Constants.TASK_DB);
        totalTask = new ArrayList<>();
        pendingTask = new ArrayList<>();
        completedTAsk = new ArrayList<>();
        dbTAsk.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                totalTask.clear();
                pendingTask.clear();
                completedTAsk.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    TaskEntity taskEntity = postSnapshot.getValue(TaskEntity.class);
                    if (taskEntity.getUserID().equals(userEntity.getUserId())) {
                        if (taskEntity.isStatus()) {
                            completedTAsk.add(taskEntity);
                        } else {
                            pendingTask.add(taskEntity);
                        }
                        totalTask.add(taskEntity);
                    }

                }

                pendingTaskAdapter = new PendingTaskAdapter(DashboardFragment.this, pendingTask);
                rvPending.setAdapter(pendingTaskAdapter);
                completedTaskAdapter = new CompletedTaskAdapter(DashboardFragment.this, completedTAsk);
                rvCompleted.setAdapter(completedTaskAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void showPopUp() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                rlparentView.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.layout_add_task, null);
        mPopupWindow = new PopupWindow(customView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);

        // Call requires API level 21
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }

        TextView btnSave = (TextView) customView.findViewById(R.id.btnSave);
        final EditText etTAskTitle = (EditText) customView.findViewById(R.id.etTAskTitle);
        etTAskTitle.requestFocus();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                if (!etTAskTitle.getText().toString().trim().equals("")) {
                    addTAsk(etTAskTitle.getText().toString().trim());
                }
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etTAskTitle.getWindowToken(), 0);
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etTAskTitle.getWindowToken(), 0);
            }
        });
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.showAtLocation(rlparentView, Gravity.CENTER, 0, 800);
    }

    private void addTAsk(String taskNAme) {
        String id = dbTAsk.push().getKey();
        TaskEntity taskEntity = new TaskEntity(userEntity.getUserId(), id, taskNAme, "", false, System.currentTimeMillis());
        dbTAsk.child(id).setValue(taskEntity);
    }

    private void initialise(View view) {
        rlparentView = (RelativeLayout) view.findViewById(R.id.rlparentView);
        rvCompleted = (RecyclerView) view.findViewById(R.id.rvCompleted);
        rvCompleted.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPending = (RecyclerView) view.findViewById(R.id.rvPending);
        rvPending.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public boolean updateTask(String id, TaskEntity taskEntity) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference(Constants.TASK_DB).child(id);
        dR.setValue(taskEntity);
        return true;
    }

    public void redirectToDetails(TaskEntity taskEntity) {
        startActivity(new Intent(getActivity(), TaskDetailsActivity.class).putExtra("TASK", taskEntity));
    }
}
