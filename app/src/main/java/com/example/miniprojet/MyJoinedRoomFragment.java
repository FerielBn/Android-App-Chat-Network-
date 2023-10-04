package com.example.miniprojet;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Adapters.AdapterOneRoom;
import AppClasses.Room;
import Interfaces.OnDataRecievedRoom;
import Interfaces.OnDataRecievedRooms;

public class MyJoinedRoomFragment extends Fragment implements LifecycleOwner {

    private Lifecycle lifecycle;
    private RecyclerView JoinedRoomsRV;
    private ArrayList<Room> joined_rooms;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_joined_room, container, false);
        JoinedRoomsRV = view.findViewById(R.id.JoinedRoomsRV);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycle = getLifecycle();

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }


    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        super.onStart();

        Room room = new Room();

        room.GetMyJoinedRooms(getContext(), new OnDataRecievedRooms() {
            @Override
            public void callback(ArrayList<Room> rooms) {
                Log.d("Public rooms",rooms.toString());
                joined_rooms = rooms;
                try{
                AdapterOneRoom adapter = new AdapterOneRoom(getContext(), joined_rooms, R.layout.one_room_item_white, new OnDataRecievedRoom() {
                    @Override
                    public void callback(Room room) {
                        if(room == null){
                            MyJoinedRoomFragment fragment =  new MyJoinedRoomFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, fragment);
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
                JoinedRoomsRV.setLayoutManager(gridLayoutManager);
                JoinedRoomsRV.setAdapter(adapter);
                JoinedRoomsRV.setClickable(true);

                }catch ( Exception exception){
                    Log.d("error in contenxt","error in contenxt My Joined Room Fragment");
                }
            }
        });

    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        // Do something when the fragment is resumed
        super.onResume();
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        // Do something when the fragment is paused
        super.onPause();
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        // Do something when the fragment is stopped
        super.onStop();
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        // Do something when the fragment is destroyed
        super.onDestroy();
    }


}
