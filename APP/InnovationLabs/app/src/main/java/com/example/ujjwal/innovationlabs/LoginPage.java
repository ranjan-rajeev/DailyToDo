package com.example.ujjwal.innovationlabs;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;

public class LoginPage extends AppCompatActivity implements AsyncResponse, View.OnClickListener {

    private EditText emailId, password;
    Button logInButton,registerButton;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        emailId = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        logInButton = (Button) findViewById(R.id.log_in_button);
        registerButton=(Button)findViewById(R.id.register_button);


        logInButton.setOnClickListener(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginPage.this,RegisterPage.class);
                startActivity(i);
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    public void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        //client2.connect();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //AppIndex.AppIndexApi.start(client, getIndexApiAction());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //AppIndex.AppIndexApi.start(client2, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        //AppIndex.AppIndexApi.end(client2, getIndexApiAction());

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //AppIndex.AppIndexApi.end(client, getIndexApiAction());
        //client.disconnect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client2.disconnect();
    }

    @Override
    public void onClick(View view) {
        HashMap postData = new HashMap();
        postData.put("logInButton", "Login");
        postData.put("mobile", "android");
        postData.put("emailId", emailId.getText().toString());
        postData.put("password", password.getText().toString());

        PostResponseAsyncTask loginTask = new PostResponseAsyncTask(this, postData);

        loginTask.execute("http://innovationlabs.esy.es/login.php");


    }


    @Override
    public void processFinish(String result) {
        if (result.equals("success")) {
            Toast.makeText(this, "Login Successfully", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, HomePage.class);
            startActivity(i);
        }
        else {
            Toast.makeText(this, "Login Unsuccessful !!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, HomePage.class);
            startActivity(i);
        }
    }

}
