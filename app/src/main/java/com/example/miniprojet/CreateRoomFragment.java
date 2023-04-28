package com.example.miniprojet;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import AppClasses.Room;

public class CreateRoomFragment extends Fragment implements LifecycleOwner, AdapterView.OnItemSelectedListener{
    private Lifecycle lifecycle;
    private Spinner RoomAccess ;
    private Spinner RoomCateg ;
    private EditText RoomKey;
    private TextView RoomName;
    private Button AddImgBtn;
    private Button CancelBtn;
    private Button SubmitBtn;

    private String room_selected_img ="";
    private String room_selected_access ="";
    private String room_selected_categ ="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_room, container, false);
        RoomAccess = view.findViewById(R.id.accessibility_select);
        RoomCateg = view.findViewById(R.id.category_select);
        RoomKey = view.findViewById(R.id.room_key);
        RoomName = view.findViewById(R.id.room_name);
        AddImgBtn = view.findViewById(R.id.AddImgBtn);
        CancelBtn = view.findViewById(R.id.CancelPrivBtn);
        SubmitBtn = view.findViewById(R.id.JoinPrivRoomBtn);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycle = getLifecycle();
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String value = bundle.getString("image","");
            this.room_selected_img = value;
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.accessibility, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        RoomAccess.setAdapter(adapter);
        RoomAccess.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(), R.array.room_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        RoomCateg.setAdapter(adapter2);
        RoomCateg.setOnItemSelectedListener(this);

        AddImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseRoomImgFragment choose_img_fragment = new ChooseRoomImgFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, choose_img_fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyRoomFragment my_room_fragm =  new MyRoomFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, my_room_fragm);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String room_name = String.valueOf(RoomName.getText());
                String room_key = String.valueOf(RoomKey.getText());
                if(TextUtils.isEmpty(room_name)){
                    Toast.makeText(getContext(), "Enter room name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(room_selected_access.equals("Select Access")){
                    Toast.makeText(getContext(), "Select Room Access Type", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(room_selected_categ.equals("Select Category")){
                    Toast.makeText(getContext(), "Select Room Category", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(room_selected_img)){
                    Toast.makeText(getContext(), "Select Room Image", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(room_selected_access.equals("Private") && TextUtils.isEmpty(room_key)){
                    Toast.makeText(getContext(), "Select Room Private Key", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("create_room",room_name);
                Log.d("create_room",room_selected_access);
                Log.d("create_room",room_selected_categ);
                Log.d("create_room",room_key);
                Log.d("create_room",room_selected_img);
                boolean room_public = room_selected_access.equals("Public");

                Room room = new Room(room_name,room_key,room_selected_categ,room_selected_img,room_public,getContext());
                room.CreateRoom(getContext());

                MyRoomFragment myRoomFragment = new MyRoomFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, myRoomFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        if(text.equals("Private") || text.equals("Public") || text.equals("Select Access")){
            this.room_selected_access =text;
            if(text.equals("Private")){
                RoomKey.setVisibility(View.VISIBLE);
            }else if(text.equals("Public") || text.equals("Select Access")){
                RoomKey.setVisibility(View.GONE);
            }
        }else{
            this.room_selected_categ =text;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}