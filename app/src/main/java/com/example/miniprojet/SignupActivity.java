package com.example.miniprojet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://miniprojet-android-8900a-default-rtdb.firebaseio.com");
    private EditText usernameInput, emailInput,phoneInput, passwordInput,confPassInput;
    private Button signupBtn, loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();
        initEvent();
    }

    private void initView() {
        usernameInput = findViewById(R.id.usernameET);
        emailInput = findViewById(R.id.emailET);
        phoneInput = findViewById(R.id.phoneET);
        passwordInput = findViewById(R.id.passwordET);
        confPassInput = findViewById(R.id.passwordET2);
        loginBtn = findViewById(R.id.goLoginBT);
        signupBtn = findViewById(R.id.signupBT);
    }

    private void initEvent() {
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = String.valueOf(usernameInput.getText());
                String email = String.valueOf(emailInput.getText());
                String phone = String.valueOf(phoneInput.getText());
                String password = String.valueOf(passwordInput.getText());
                String confirm_password = String.valueOf(confPassInput.getText());

                if(TextUtils.isEmpty(username)){
                    Toast.makeText(SignupActivity.this, "Enter username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(SignupActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(SignupActivity.this, "Enter phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(SignupActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(confirm_password)){
                    Toast.makeText(SignupActivity.this, "password not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!TextUtils.equals(password,confirm_password)){
                    Toast.makeText(SignupActivity.this, "password not match", Toast.LENGTH_SHORT).show();
                    return;
                }



                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // check if email exist already
                        if(snapshot.hasChild(phone)){
                            Toast.makeText(SignupActivity.this,"Phone Already used", Toast.LENGTH_SHORT).show();
                            return;
                        }else{

                            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    boolean emailExists = false;
                                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                        String email2 = userSnapshot.child("email").getValue(String.class);

                                        if (email2 != null && email2.equals(email)) {
                                            Toast.makeText(SignupActivity.this,"Email Already exist", Toast.LENGTH_SHORT).show();
                                            emailExists = true;
                                            break;
                                        }
                                    }
                                    if (!emailExists) {
                                        databaseReference.child("users").child(phone).child("username").setValue(username);
                                        databaseReference.child("users").child(phone).child("email").setValue(email);
                                        databaseReference.child("users").child(phone).child("phone").setValue(phone);
                                        databaseReference.child("users").child(phone).child("password").setValue(password);
                                        Toast.makeText(SignupActivity.this, "User Registered Successfuly", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

        });
    }

}