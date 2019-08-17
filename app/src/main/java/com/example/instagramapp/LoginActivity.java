package com.example.instagramapp;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    public static String usernameCookie = "";

    TextInputLayout textpassword;
    EditText etUser;
    Button BtnLogin;
    TextView tvSignup;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textpassword = (TextInputLayout) findViewById(R.id.tipassword);
        etUser = (EditText) findViewById(R.id.etUsername);
        BtnLogin = (Button) findViewById(R.id.btn_Login);
        tvSignup = (TextView) findViewById(R.id.signup);
        ivBack = (ImageView) findViewById(R.id.btnback);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ParseUser currentUser = ParseUser.getCurrentUser();
       /* if (currentUser != null) {

            //goActivity();
        } else {*/


            BtnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = etUser.getText().toString();
                    String password = textpassword.getEditText().getText().toString();
                    if (username.isEmpty() && password.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
                    } else {
                        login(username, password);
                    }

                }
            });
        //}
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e!= null){
                    Log.e(TAG,"Issue with login");
                    e.printStackTrace();
                    return;
                }
                goActivity();
            }
        });
    }

    private void goActivity() {
        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);
        finish();
    }


}
