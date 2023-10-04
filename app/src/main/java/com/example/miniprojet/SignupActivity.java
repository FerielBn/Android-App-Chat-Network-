package com.example.miniprojet;

<<<<<<< HEAD
import androidx.annotation.NonNull;
=======
>>>>>>> Final_1
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

<<<<<<< HEAD
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameInput, emailInput, passwordInput;
    private Button signupBtn, loginBtn;
    FirebaseAuth mAuth;
=======
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import AppClasses.User;

public class SignupActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://miniprojet-android-8900a-default-rtdb.firebaseio.com");
    private EditText usernameInput, emailInput,phoneInput, passwordInput,confPassInput;
    private Button signupBtn, loginBtn;
    private FirebaseAuth mAuth;
>>>>>>> Final_1

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
<<<<<<< HEAD
=======

        User user = new User();
        user.GetFromStorage(SignupActivity.this);

        if(user.connected){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

>>>>>>> Final_1
        initView();
        initEvent();
    }

    private void initView() {
<<<<<<< HEAD
        mAuth = FirebaseAuth.getInstance();
        usernameInput = findViewById(R.id.usernameET);
        emailInput = findViewById(R.id.emailET);
        passwordInput = findViewById(R.id.passwordET);
        loginBtn = findViewById(R.id.goLoginBT);
        signupBtn = findViewById(R.id.signupBT);
=======

        usernameInput = findViewById(R.id.usernameET);
        emailInput = findViewById(R.id.emailET);
        phoneInput = findViewById(R.id.phoneET);
        passwordInput = findViewById(R.id.mailET);
        confPassInput = findViewById(R.id.confirmPasswordET);
        loginBtn = findViewById(R.id.goLoginBT);
        signupBtn = findViewById(R.id.signupBT);



>>>>>>> Final_1
    }

    private void initEvent() {
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = String.valueOf(usernameInput.getText());
                String email = String.valueOf(emailInput.getText());
<<<<<<< HEAD
                String password = String.valueOf(passwordInput.getText());
=======
                String phone = String.valueOf(phoneInput.getText());
                String password = String.valueOf(passwordInput.getText());
                String confirm_password = String.valueOf(confPassInput.getText());
>>>>>>> Final_1

                if(TextUtils.isEmpty(username)){
                    Toast.makeText(SignupActivity.this, "Enter username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(SignupActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

<<<<<<< HEAD
=======
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(SignupActivity.this, "Enter phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

>>>>>>> Final_1
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(SignupActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

<<<<<<< HEAD

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(TAG, "createUserWithEmail:success");
                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(SignupActivity.this, "Account Created.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignupActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
=======
                if(TextUtils.isEmpty(confirm_password)){
                    Toast.makeText(SignupActivity.this, "password not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!TextUtils.equals(password,confirm_password)){
                    Toast.makeText(SignupActivity.this, "password not match", Toast.LENGTH_SHORT).show();
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

//                // Initialize Firebase Auth
//                mAuth = FirebaseAuth.getInstance();
//                mAuth.createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d("TAG", "createUserWithEmail:success");
//
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
//
//                                }
//                            }
//                        });


                User user = new User(phone,username,email,password);
                user.UserRegister(SignupActivity.this,success,fail);
>>>>>>> Final_1


            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
=======
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
>>>>>>> Final_1
                startActivity(intent);
                finish();
            }

        });
    }

}