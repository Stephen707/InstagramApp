package com.example.instagramapp;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {
    //private static final String APP_TAG = "SignupActivity";
    TextInputLayout Username,Email,Password,RePassword;
    Button BtnSignup;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Username = (TextInputLayout)findViewById(R.id.username);
        Email = (TextInputLayout)findViewById(R.id.email);
        Password = (TextInputLayout)findViewById(R.id.tipassword);
        RePassword = (TextInputLayout)findViewById(R.id.tirepassword);
        BtnSignup = (Button)findViewById(R.id.btn_signup);
        ivBack  = (ImageView)findViewById(R.id.btnback);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        BtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = Username.getEditText().getText().toString();
                String email = Email.getEditText().getText().toString();
                String password = Password.getEditText().getText().toString();
                String repassword = RePassword.getEditText().getText().toString();
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || repassword.isEmpty()){
                    Toast.makeText(SignupActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.contentEquals(repassword)){
                    Toast.makeText(SignupActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                    return;
                }
                signup(username,email,password);

            }
        });
    }

    private void signup(final String username, String email, String password) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                    startActivity(intent);
                    //LoginActivity.usernameCookie = username;
                    Toast.makeText(SignupActivity.this, "Accout Create Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    e.printStackTrace();
                    Toast.makeText(SignupActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
