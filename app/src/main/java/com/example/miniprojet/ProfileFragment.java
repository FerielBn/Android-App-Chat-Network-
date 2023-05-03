package com.example.miniprojet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import AppClasses.User;

public class ProfileFragment extends Fragment {
    private Button update_password_btn, update_profile_btn;
    private TextView username_field, email_field;
    private EditText username_input, email_input, phone_input;
    String username, email, phone;
    User currentUser = new User();

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);


        currentUser.GetFromStorage(getContext());
        Log.d("current user is ", currentUser.toString());


        update_password_btn = view.findViewById(R.id.updatePasswordBT);
        update_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });



        update_profile_btn = view.findViewById(R.id.updateProfileBT);
        update_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = username_input.getText().toString();
                email = email_input.getText().toString();
                phone = phone_input.getText().toString();

                User newUser = new User(username, email, phone);
                Log.d("new user is ", newUser.toString());
                currentUser.UpdateUser(newUser, getContext());
                currentUser.UpdateStorage(getContext());
                Toast.makeText(getActivity(), "Profile Updated !", Toast.LENGTH_SHORT).show();

            }
        });


        username_field = view.findViewById(R.id.username_field);
        email_field = view.findViewById(R.id.email_field);

        username_field.setText(currentUser.userName);
        email_field.setText(currentUser.email);

        //update profile
        username_input = view.findViewById(R.id.username_input);
        email_input = view.findViewById(R.id.email_input);
        phone_input = view.findViewById(R.id.phone_input);

        username_input.setText(currentUser.userName);
        email_input.setText(currentUser.email);
        phone_input.setText(currentUser.num_tel);


        return view;
    }




    public void showDialog() {
        View dialog_view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_update_password, null);

        EditText currentPasswordET = dialog_view.findViewById(R.id.currentPasswordET);
        EditText newPasswordET = dialog_view.findViewById(R.id.newPasswordET);
        EditText confirmNewPasswordET = dialog_view.findViewById(R.id.confirmNewPasswordET);
        Button confirmPasswordBT = dialog_view.findViewById(R.id.confirmPasswordBT);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialog_view);

        AlertDialog dialog = builder.create();
        dialog.show();

        confirmPasswordBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = currentPasswordET.getText().toString().trim();
                String newPassword = newPasswordET.getText().toString().trim();
                String confirmNewPassword = confirmNewPasswordET.getText().toString().trim();


                if(TextUtils.isEmpty(currentPassword)){
                    Toast.makeText(getActivity(), "Enter your current password ...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(newPassword.length()<6){
                    Toast.makeText(getActivity(), "Password length must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                currentUser.GetFromStorage(getContext());
                currentUser.setPassword(newPassword);
                Log.d("new user is ", currentUser.toString());
                currentUser.UpdateUser(currentUser, getContext());
                currentUser.UpdateStorage(getContext());
                Toast.makeText(getActivity(), "Profile Updated !", Toast.LENGTH_SHORT).show();

                dialog.dismiss();

//                updatePassword(currentPassword, newPassword);

            }
        });
    }

    private void updatePassword(String currentPassword,String newPassword){

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference usersBD = database.getReference("users");   // query to check if email already used
//        Query checkMail = usersBD.orderByChild("email").equalTo(email);
//
//        checkMail.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
//                        User bd_user = userSnapshot.getValue(User.class);
//                        Log.d("TAG", "bd_user is: " + bd_user);
//                        // Handle data changes here
//                        currentUser.setPassword(newPassword);
//                        currentUser.UpdateUser(bd_user, getContext());
//                        currentUser.UpdateStorage(getContext());
//                        Log.d("TAG", "current user is: " + currentUser);
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.d("user login error","error error");
//
//            }
//        });
//


//        get currrent user ?????????????

//        FirebaseAuth firebaseAuth;
//        firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        Log.d("firebaseuser", user.toString());

//
//                //extract data
//            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
//            ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    User user = snapshot.getValue(User.class);
//                    Log.d("user from bd = ", user.toString());
//
//                    if(user !=null){
//
//                        String pass =user.password;
//                        passwordInput.setText(pass);
//
//
//                    }
//
////                }
////
////            });
////
////
//
//
//
//
////        validate data
//        AuthCredential authCredential = EmailAuthProvider.getCredential(currentUser.email, currentPassword);
//        user.reauthenticate(authCredential)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//
////                                update password
//                                user.updatePassword(newPassword)
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void unused) {
//                                                Toast.makeText(getActivity(), "Password updated successfully !", Toast.LENGTH_SHORT).show();
//                                            }
//                                        })
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//
//
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });


    }




}