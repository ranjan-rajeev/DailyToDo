package com.example.ujjwal.innovationlabs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;

public class RegisterPage extends AppCompatActivity implements AsyncResponse, View.OnClickListener {

    private EditText username,emailId,contact,password,conformPassword;
    Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        username= (EditText) findViewById(R.id.name);
        emailId=(EditText) findViewById(R.id.register_email);
        contact=(EditText) findViewById(R.id.phone_number);
        password=(EditText) findViewById(R.id.register_password);
        conformPassword=(EditText) findViewById(R.id.conform_password);

        registerButton=(Button) findViewById(R.id.register_page_button);

        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        HashMap postData = new HashMap();
        postData.put("logInButton", "Login");
        postData.put("mobile", "android");
        postData.put("username", username.getText().toString());
        postData.put("emailId", emailId.getText().toString());
        postData.put("contact", contact.getText().toString());
        postData.put("password", password.getText().toString());
        postData.put("conformPassword", conformPassword.getText().toString());

        PostResponseAsyncTask registerTask = new PostResponseAsyncTask(this, postData);
        registerTask.execute("http://innovationlabs.esy.es/register.php");
    }


    @Override
    public void processFinish(String result) {
        if (result.equals("success")) {
            Toast.makeText(this, "Register Successfully", Toast.LENGTH_LONG).show();
            Intent i=new Intent(this,LoginPage.class);
            startActivity(i);
        }
        else if(result.equals("user exist")){
            Toast.makeText(this, "You already registered ", Toast.LENGTH_LONG).show();
        }
        else{
            System.out.println(result);
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }

    }
}
