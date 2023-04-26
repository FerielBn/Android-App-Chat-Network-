package com.example.miniprojet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class JoinRoomFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Get the arguments from the Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            String value = bundle.getString("key"); // replace "key" with the key you used to send the data
            Toast.makeText(getContext(), value, Toast.LENGTH_SHORT).show();
        }
        return inflater.inflate(R.layout.fragment_join_room, container, false);
    }
}