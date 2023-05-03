package com.example.miniprojet;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import AppClasses.User;

public class HomeFragment extends Fragment {

    private TextView user_details;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        User user = new User();
        user.GetFromStorage(getContext());

        user_details = view.findViewById(R.id.user_details);
        user_details.setText(user.email);

        // Inflate the layout for this fragment
        return view;
    }



}