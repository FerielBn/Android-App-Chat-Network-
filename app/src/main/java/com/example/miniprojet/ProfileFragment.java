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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Adapters.AdapterOneAvatarImage;
import AppClasses.User;
import Interfaces.OnDataRecieve;
import Interfaces.OnDataRecieveImage;

public class ProfileFragment extends Fragment {
    private Button update_password_btn, update_profile_btn;
    private TextView username_field, email_field;
    private EditText username_input, email_input, phone_input;
    public ImageView userImage;
    String username, email, phone;
    User currentUser = new User();

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


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

                User user = new User();
                user.ChangeProfile(getContext(), username, phone, email, new OnDataRecieve() {
                    @Override
                    public void callback() {
                        // 5adem 7aja lena kan t7eb
                    }
                });
            }
        });

        userImage = view.findViewById(R.id.profile_image);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogImageSelect();
            }
        });


        email_field = view.findViewById(R.id.email_field);
        username_field = view.findViewById(R.id.username_field);
        username_field.setText(currentUser.userName);
        email_field.setText(currentUser.email);

        //update profile
        username_input = view.findViewById(R.id.username_input);
        email_input = view.findViewById(R.id.email_input);
        phone_input = view.findViewById(R.id.phone_input);

        username_input.setText(currentUser.userName);
        email_input.setText(currentUser.email);
        phone_input.setText(currentUser.num_tel);
        Glide.with(getActivity())
                .load(currentUser.user_img)
                .into(userImage);


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

                User user = new User();
                user.GetFromStorage(getActivity());

                if(TextUtils.isEmpty(currentPassword)){
                    Toast.makeText(getActivity(), "Enter your current password ...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(newPassword.length()<6){
                    Toast.makeText(getActivity(), "Password length must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!user.password.equals(currentPassword)){
                    Toast.makeText(getActivity(), "enter your current password correctly", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!confirmNewPassword.equals(newPassword)){
                    Toast.makeText(getActivity(), "password not matched", Toast.LENGTH_SHORT).show();
                    return;
                }

                user.ChangePassword(getActivity(), newPassword, new OnDataRecieve() {
                    @Override
                    public void callback() {
                        dialog.dismiss();
                    }
                });


            }
        });
    }

    public void showDialogImageSelect() {
        View dialog_view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_user_images, null);

        RecyclerView ImagesRV;
        ArrayList<String> images = new ArrayList<String>();;
        ImagesRV = dialog_view.findViewById(R.id.ImageList);

        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410201/avatars/pjyi7s4od1pqhl1sibyn.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410201/avatars/fkanrzk1kin3y8bpig0u.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410204/avatars/dfwjmmym5bz5vefnd5oo.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410202/avatars/ym0cf8yyf5frfz1kgoou.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410204/avatars/sagacd08kd1ekatcxlz7.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410209/avatars/at5qscqfixgmvfvyyyc4.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410209/avatars/ebbie0jw8oc8brbrxx1c.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410209/avatars/fnmr3g3v3ulxm6gb3ovr.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410208/avatars/yr5qadehapvhhu5c8bb7.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410207/avatars/dla0dltwqsx6ho4f8csn.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410207/avatars/awjum6vrcknfepbnk2fn.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410206/avatars/q2hjex2i6hlu7gxtdacl.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410207/avatars/iazxvgllrwpdffdgvmra.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410204/avatars/djlhw6avzkzbyjrdaipg.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410204/avatars/d7j98fpyusbajzxici4t.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410201/avatars/zlsq5ahiinurwp7hc2nl.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410201/avatars/maquune1vsok39oc6mt9.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410200/avatars/wddztcg0knmsol3ukgdy.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410199/avatars/ubbi9p6v0bucxybm21sa.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410198/avatars/n4xj8meo7vlmyjtwaatm.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1641410197/avatars/ldsmgj80efvcmnp5z9ao.png");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialog_view);

        AlertDialog dialog = builder.create();
        dialog.show();

        try{
            AdapterOneAvatarImage adapter = new AdapterOneAvatarImage(getContext(), images, new OnDataRecieveImage() {
                @Override
                public void callback(String img_url) {
                    User user = new User();
                    user.ChangeImageAvatar(getActivity(), img_url, new OnDataRecieve() {
                        @Override
                        public void callback() {
                            Glide.with(getActivity())
                                    .load(img_url)
                                    .into(userImage);
                            dialog.dismiss();
                        }
                    });
                }
            });
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL,false);
            ImagesRV.setLayoutManager(gridLayoutManager);
            ImagesRV.setAdapter(adapter);
            ImagesRV.setClickable(true);
        }catch ( Exception exception){
            Log.d("error in contenxt","error in contenxt choose room img");
        }


    }

}