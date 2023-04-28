package com.example.miniprojet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import AppClasses.User;

public class LoginActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://miniprojet-android-8900a-default-rtdb.firebaseio.com");
    private EditText emailInput, passwordInput;
    private TextView forgetPasswordTV;
    private Button signupBtn, loginBtn;
    private User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        User user = new User();
        user.GetFromStorage(LoginActivity.this);

        if(user.connected){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        initView();
        initEvent();
    }

    private void initView() {
        emailInput = findViewById(R.id.emailET);
        passwordInput = findViewById(R.id.passwordET);
        loginBtn = findViewById(R.id.loginBT);
        signupBtn = findViewById(R.id.goSignupBT);
        forgetPasswordTV = findViewById(R.id.forgetPasswordTV);
    }

    private void initEvent() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(emailInput.getText());
                String password = String.valueOf(passwordInput.getText());
                User user = new User();
                user.GetFromStorage(LoginActivity.this);
                Log.d("blablabla","blablabla");
                Log.d("blablabla",user.toString());
                if(user.connected){
                    Log.d("connected","user is really connected");
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }


                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                Runnable success = ()->{
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                };
                Runnable fail = ()->{
                    Log.d("callback","jawna mouch bahi");
                };
                user.UserLogin(LoginActivity.this,email,password,success,fail);


            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                finish();
            }

        });

        forgetPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialog_view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.dialog_forget_password, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setView(dialog_view);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }


}
