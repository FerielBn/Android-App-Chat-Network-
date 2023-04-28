package com.example.miniprojet;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import AppClasses.Room;
import Interfaces.OnDataRecievedRoom;

public class JoinPrivRoom extends Fragment {
    private TextView RoomNameTV;
    private TextView RoomKeyTV;
    private Button JoinBtn;
    private Button CancelBtn;

    public interface DataCallback {
        void onDataReceived(String data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_join_priv_room, container, false);
        RoomNameTV = view.findViewById(R.id.room_name);
        RoomKeyTV = view.findViewById(R.id.room_key);
        JoinBtn = view.findViewById(R.id.JoinPrivRoomBtn);
        CancelBtn = view.findViewById(R.id.CancelPrivBtn);
        return view;
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        super.onStart();


        JoinBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String room_name = String.valueOf(RoomNameTV.getText());
                String room_key = String.valueOf(RoomKeyTV.getText());
                if(TextUtils.isEmpty(room_name)){
                    Toast.makeText(getContext(), "Enter room name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(room_key)){
                    Toast.makeText(getContext(), "Enter room key", Toast.LENGTH_SHORT).show();
                    return;
                }
                Room room = new Room();

                room.JoinRoomPriv(getContext(), room_name, room_key, new OnDataRecievedRoom() {
                    @Override
                    public void callback(Room room) {
                        if(room== null){
                            Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "Welcome to '"+room_name +"' room", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyRoomFragment myRoomFragment = new MyRoomFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, myRoomFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });




    }


}