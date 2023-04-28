package com.example.miniprojet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

public class JoinRoomFragment extends Fragment {
    private Button JoinPrv;
    private Button JoinPub;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_join_room, container, false);
        JoinPrv = view.findViewById(R.id.JoinPriv);
        JoinPub = view.findViewById(R.id.JoinPub);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String value = bundle.getString("key"); // replace "key" with the key you used to send the data
        }
        return view;
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        super.onStart();
        JoinPub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JoinRoomFragment newFragment = new JoinRoomFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        JoinPrv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JoinRoomFragment newFragment = new JoinRoomFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }


}