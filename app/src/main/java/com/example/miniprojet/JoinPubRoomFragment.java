package com.example.miniprojet;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Adapters.AdapterOneRoom;
import Adapters.AdapterOneRoomImg;
import AppClasses.Room;
import AppClasses.User;
import Interfaces.OnDataRecievedRoom;
import Interfaces.OnDataRecievedRooms;

public class JoinPubRoomFragment extends Fragment {
    private RecyclerView RoomsListRV;
    private ArrayList<Room> public_rooms;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_join_pub_room, container, false);
        RoomsListRV = view.findViewById(R.id.publi_rooms_RV);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
    }


    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        super.onStart();



        Room room = new Room();
        room.GetPublicRooms(getContext(), new OnDataRecievedRooms() {
            @Override
            public void callback(ArrayList<Room> rooms) {
                Log.d("Public rooms",rooms.toString());
                public_rooms = rooms;
                if(rooms.size()==0){
                    Toast.makeText(getContext(), "You already joined all rooms", Toast.LENGTH_SHORT).show();
                }
                AdapterOneRoom adapter = new AdapterOneRoom(getContext(), public_rooms, R.layout.one_room_item, new OnDataRecievedRoom() {
                    @Override
                    public void callback(Room room) {
                        if(room == null){
                            MyJoinedRoomFragment myJoinedRoomFragment =  new MyJoinedRoomFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, myJoinedRoomFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                       else{
                            Log.d("room_from_adapter",room.room_name);
                            // go to chat
                            ChatChatChatRoomFragment charRoomFragment =  new ChatChatChatRoomFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, charRoomFragment);
                            transaction.addToBackStack(null);

                            Bundle bundle = new Bundle();
                            bundle.putString("room_id",room.room_id);

                            charRoomFragment.setArguments(bundle);
                            transaction.commit();
                        }
                    }
                });
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
                RoomsListRV.setLayoutManager(gridLayoutManager);
                RoomsListRV.setAdapter(adapter);
                RoomsListRV.setClickable(true);
            }
        });



        /*room.GetMyCreatedRooms(getContext(), new OnDataRecievedRooms() {
            @Override
            public void callback(ArrayList<Room> rooms) {
                Log.d("Created rooms",rooms.toString());
            }
        });

        room.GetMyJoinedRooms(getContext(), new OnDataRecievedRooms() {
            @Override
            public void callback(ArrayList<Room> rooms) {
                Log.d("Joined rooms",rooms.toString());
            }
        });*/

    }


}