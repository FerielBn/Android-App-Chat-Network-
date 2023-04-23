package com.example.miniprojet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
    private Button signupBtn, loginBtn;
    private User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initEvent();
    }

    private void initView() {
        emailInput = findViewById(R.id.emailET);
        passwordInput = findViewById(R.id.passwordET);
        loginBtn = findViewById(R.id.loginBT);
        signupBtn = findViewById(R.id.goSignupBT);
    }

    private void initEvent() {

        SharedPreferences shared = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        /*if(shared.getBoolean("logged",false)){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }*/
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(emailInput.getText());
                String password = String.valueOf(passwordInput.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                // hello world
                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean foundHim = false;
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            String email2 = userSnapshot.child("email").getValue(String.class);
                            String phone = userSnapshot.child("phone").getValue(String.class);
                            String username = userSnapshot.child("username").getValue(String.class);
                            String pass2 = userSnapshot.child("password").getValue(String.class);

                            if (email2 != null && email2.equals(email)) {
                                // i have found the email
                                foundHim = true;
                                if(pass2 != null && pass2.equals(password)){
                                    Toast.makeText(LoginActivity.this, "Welcome User", Toast.LENGTH_SHORT).show();
                                    user = new User(phone,username,email,password,LoginActivity.this);
                                    Log.d("User Data",user.toString());
                                    editor.putString("email", email);
                                    editor.putString("phone", phone);
                                    editor.putString("username", username);
                                    editor.putBoolean("logged", true);
                                    editor.apply();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(LoginActivity.this, "Check Your Email/Password", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                        }
                        if (!foundHim) {
                            // There's no user with that email
                            Toast.makeText(LoginActivity.this, "There's no user with that email ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


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
    }


}
