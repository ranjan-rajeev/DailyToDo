package com.dailytodo.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dailytodo.R;
import com.dailytodo.Utility.BaseActivity;
import com.dailytodo.Utility.Constants;
import com.dailytodo.Utility.PrefManager;
import com.dailytodo.home.HomeActivity;
import com.dailytodo.model.UserEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    Button btnLogin;
    EditText etMobile;
    DatabaseReference dbUser;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefManager = new PrefManager(this);
        dbUser = FirebaseDatabase.getInstance().getReference(Constants.USER_DB);

        etMobile = (EditText) findViewById(R.id.etMobile);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin) {
            String mobileNo = etMobile.getText().toString().trim();
            if (!mobileNo.equals("")) {
                AddUserIfNotExhist(mobileNo);
            } else {
                Toast.makeText(this, "Please enter  Mobile", Toast.LENGTH_LONG).show();
            }
        }
    }

    /*private void AddUserIfNotExhist() {

        String mobileNo = etMobile.getText().toString().trim();

        if (!mobileNo.equals("")) {

            UserEntity userEntity;
            userEntity = checkIfUserExhist(mobileNo);

            if (userEntity == null) {
                String id = dbUser.push().getKey();

                userEntity = new UserEntity(id, "", "", mobileNo, "");

                Task<Void> task = dbUser.child(id).setValue(userEntity);

                etMobile.setText("");

                //displaying a success toast
                Toast.makeText(this, "Login Success", Toast.LENGTH_LONG).show();
            }
            prefManager.storeUser(userEntity);
        } else {
            Toast.makeText(this, "Please enter  Mobile", Toast.LENGTH_LONG).show();
        }

    }*/

    public void AddUserIfNotExhist(final String mobileNo) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child(Constants.USER_DB).orderByChild("mobile").equalTo(mobileNo);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        UserEntity userEntity = dataSnapshot1.getValue(UserEntity.class);
                        prefManager.storeUser(userEntity);
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        break;
                    }
                } else {
                    String id = dbUser.push().getKey();
                    UserEntity userEntity = new UserEntity(id, "", "", mobileNo, "");
                    dbUser.child(id).setValue(userEntity);
                    prefManager.storeUser(userEntity);
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    Toast.makeText(LoginActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
