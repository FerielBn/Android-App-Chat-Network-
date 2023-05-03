package com.example.miniprojet;

import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapters.AdapterOneMessage;
import Adapters.AdapterOneRoom;
import AppClasses.Room;
import AppClasses.User;
import AppClasses.Message;
import Interfaces.OnDataRecievedMessage;
import Interfaces.OnDataRecievedMessages;
import Interfaces.OnDataRecievedRoom;
import Interfaces.OnDataRecievedRooms;

public class ChatChatChatRoomFragment extends Fragment {
    private Lifecycle lifecycle;
    private ImageView RoomImage;
    private TextView RoomName;
    private EditText InputMessage;
    private Button SendButton;
    private RecyclerView MesssagesRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_chat_chat_room, container, false);
        RoomImage = view.findViewById(R.id.RoomImage);
        RoomName = view.findViewById(R.id.RoomName);
        InputMessage = view.findViewById(R.id.EditTextMessage);
        SendButton = view.findViewById(R.id.ButtonSend);
        MesssagesRV = view.findViewById(R.id.MesssagesRV);
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

        Bundle bundle = getArguments();
        if (bundle != null) {
            String room_id = bundle.getString("room_id",null);
            if(room_id != null){
                Room room = new Room();
                room.GetRoomByID(room_id, new OnDataRecievedRoom() {
                    @Override
                    public void callback(Room room) {
                        if(room==null){
                            HomeFragment homeFragment = new HomeFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, homeFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();

                        }else{
                            Glide.with(getContext()).load(room.room_image).into(RoomImage);
                            RoomName.setText(room.room_name);
                            User user = new User();
                            user.GetFromStorage(getContext());

                            Message message = new Message();
                            message.GetMessagesByRoom(getContext(), room_id, new OnDataRecievedMessages() {
                                @Override
                                public void callback(ArrayList<Message> messages) {
                                    if(messages !=null ){
                                        try {
                                            AdapterOneMessage adapter = new AdapterOneMessage(getContext(), messages);
                                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
                                            MesssagesRV.setLayoutManager(gridLayoutManager);
                                            MesssagesRV.setAdapter(adapter);
                                            MesssagesRV.setClickable(true);
                                        }catch ( Exception exception){
                                            Log.d("error in contenxt","error in contenxt chat room");
                                        }

                                    }
                                }
                            });





                            SendButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String content = InputMessage.getText().toString();
                                    Message message = new Message();
                                    message.SendMessage(getContext(), room_id, content, new OnDataRecievedMessage() {
                                        @Override
                                        public void callback(Message message) {
                                            if(message==null){
                                                Log.d("message","message didn't sent");
                                            }else{
                                                Log.d("message","message sent");
                                                InputMessage.setText("");
                                            }
                                        }
                                    });
                                }
                            });



                        }
                    }
                });
            }

        }

    }
}