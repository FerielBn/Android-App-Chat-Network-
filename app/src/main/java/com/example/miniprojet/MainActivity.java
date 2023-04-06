package com.example.miniprojet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private Button signupBtn, loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(emailInput.getText());
                String password = String.valueOf(passwordInput.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(MainActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(MainActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }


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