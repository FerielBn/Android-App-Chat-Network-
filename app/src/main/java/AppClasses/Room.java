package AppClasses;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Interfaces.OnDataRecievedRoom;
import Interfaces.OnDataRecievedRooms;

public class Room {
    public String room_name="";
    public String room_key="";
    public String category="";
    public String room_id="";
    public String room_image="";
    public User owner;
    public ArrayList<User> users;
    public boolean room_public = true;
    // messages list


    public Room() {
    }

    public Room(String room_name, String room_key, String category, String room_image, boolean room_public,Context context) {
        User user = new User();
        user.GetFromStorage(context);

        this.room_name = room_name;
        this.room_key = room_key;
        this.category = category;
        this.room_id = "";
        this.room_image = room_image;
        this.owner = user;
        this.users = new ArrayList<User>();
        this.room_public = room_public;
    }

    public void CreateRoom(Context context){
        Room room = this;
        User user = new User();
        user.GetFromStorage(context);
        ArrayList<User> users = new ArrayList<User>();
        users.add(user);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference BD_Room = database.getReference("rooms");
        String roomId = BD_Room.push().getKey();
        room.room_id=roomId;
        room.users = users;
        BD_Room.child(roomId).setValue(room);
        Toast.makeText(context, "Room Created Successfully", Toast.LENGTH_SHORT).show();
    }

    public void UpdateRoom(Room room){
        this.room_name = room_name;
        this.room_key = room_key;
        this.category = category;
        this.room_id = room.room_id;
        this.room_image = room_image;
        this.owner = room.owner;
        this.users = room.users;
        this.room_public = room.room_public;

    }

    public void JoinRoomPublic(Context context, String room_id, OnDataRecievedRoom callback){
        User user = new User();
        user.GetFromStorage(context);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference roomsBD_ref = database.getReference("rooms");
        Query checkID = roomsBD_ref.orderByChild("room_id").equalTo(room_id);

        checkID.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot snap_data : snapshot.getChildren()) {
                        Room bd_room = snap_data.getValue(Room.class);
                            Log.d("room connected","Welcome to " + bd_room.room_name);
                            boolean exist_in_room=false;
                            for (User user_in_room : bd_room.users) {
                                if (user.num_tel.equals(user_in_room.num_tel)) {
                                    exist_in_room = true;
                                    return;
                                }
                            }
                            if(!exist_in_room){
                                Map<String, Object> updatedValues = new HashMap<>();
                                ArrayList<User> users = bd_room.users;
                                users.add(user);
                                updatedValues.put("users",users);
                                roomsBD_ref.child(bd_room.room_id).updateChildren(updatedValues, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        if (error == null) {
                                            callback.callback(bd_room);
                                            Toast.makeText(context, "you have joined now", Toast.LENGTH_SHORT).show();
                                        } else {
                                            callback.callback(null);
                                            Toast.makeText(context, "you can't join now", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                    }
                }
                else{
                    Toast.makeText(context, "No Room With That ID", Toast.LENGTH_SHORT).show();
                    callback.callback(null);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("room login error","error error");
                callback.callback(null);
            }
        });

    }

    public void JoinRoomPriv(Context context, String room_name, String room_key, OnDataRecievedRoom callback){
        Room room = this;
        User user = new User();
        user.GetFromStorage(context);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference roomsBD_ref = database.getReference("rooms");
        Query checkName = roomsBD_ref.orderByChild("room_name").equalTo(room_name);

        checkName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot snap_data : snapshot.getChildren()) {
                        Room bd_room = snap_data.getValue(Room.class);
                        if(bd_room.room_key.equals(room_key)){
                            room.UpdateRoom(bd_room);
                            Log.d("room connected","Welcome to " + bd_room.room_name);
                            boolean exist_in_room=false;
                            for (User user_in_room : bd_room.users){
                                if(user.num_tel.equals(user_in_room.num_tel)){
                                    exist_in_room=true;
                                    return;
                                }
                            }
                            if(!exist_in_room){
                                Map<String, Object> updatedValues = new HashMap<>();
                                ArrayList<User> users = bd_room.users;
                                users.add(user);
                                updatedValues.put("users",users);
                                roomsBD_ref.child(bd_room.room_id).updateChildren(updatedValues, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        if (error == null) {
                                            Toast.makeText(context, "you have joined now", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "you can't join now", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                            callback.callback(bd_room);

                        }else{
                            Toast.makeText(context, "Wrong Key ", Toast.LENGTH_SHORT).show();
                            callback.callback(null);
                        }
                    }
                }
                else{
                    Toast.makeText(context, "No Room With That Name", Toast.LENGTH_SHORT).show();
                    callback.callback(null);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("room login error","error error");
                callback.callback(null);
            }
        });

    }

    public void LeaveRoom(Context context, String room_id, OnDataRecievedRoom callback){
        User user = new User();
        user.GetFromStorage(context);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference roomsBD_ref = database.getReference("rooms");
        Query checkID = roomsBD_ref.orderByChild("room_id").equalTo(room_id);

        checkID.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot snap_data : snapshot.getChildren()) {
                        Room bd_room = snap_data.getValue(Room.class);
                        Log.d("room connected","Welcome to " + bd_room.room_name);
                        ArrayList<User> new_users = new ArrayList<User>();
                        for (User user_in_room : bd_room.users) {
                            if (!user.num_tel.equals(user_in_room.num_tel)) {
                                new_users.add(user_in_room);
                            }
                        }
                        Map<String, Object> updatedValues = new HashMap<>();
                        updatedValues.put("users",new_users);
                        roomsBD_ref.child(bd_room.room_id).updateChildren(updatedValues, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if (error == null) {
                                    callback.callback(bd_room);
                                    Toast.makeText(context, "you have joined now", Toast.LENGTH_SHORT).show();
                                } else {
                                    callback.callback(null);
                                    Toast.makeText(context, "you can't join now", Toast.LENGTH_SHORT).show();
                                }
                                }
                        });
                    }
                }
                else{
                    Toast.makeText(context, "No Room With That ID", Toast.LENGTH_SHORT).show();
                    callback.callback(null);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("room login error","error error");
                callback.callback(null);
            }
        });

    }

    public void GetPublicRooms(Context context, OnDataRecievedRooms callbacks ){
        ArrayList<Room> public_rooms = new ArrayList<Room>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference roomsBdRef = database.getReference("rooms");
        Query PublicRoomsQ = roomsBdRef.orderByChild("room_public").equalTo(true);

        User user = new User();
        user.GetFromStorage(context);


        PublicRoomsQ.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int count=0;
                    public_rooms.clear();
                    for (DataSnapshot snap_data : snapshot.getChildren()) {
                        Room bd_room = snap_data.getValue(Room.class);
                        count++;
                        ArrayList<User> users_in_room= bd_room.users;
                        boolean exist_in_room=false;
                        for (User user_in_room : users_in_room){
                            if(user_in_room.num_tel.equals(user.num_tel)){
                                exist_in_room = true;
                            }
                        }
                        if(exist_in_room==false){
                            public_rooms.add(bd_room);
                        }
                        if(snapshot.getChildrenCount() == count){
                            callbacks.callback(public_rooms);
                        }
                    }
                }
                else{
                    Toast.makeText(context, "No Public Room", Toast.LENGTH_SHORT).show();
                    callbacks.callback(public_rooms);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("room error","error error");
                callbacks.callback(public_rooms);
            }
        });
    }

    public void GetMyCreatedRooms(Context context, OnDataRecievedRooms callbacks ){
        ArrayList<Room> created_rooms = new ArrayList<Room>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference roomsBdRef = database.getReference("rooms");

        User user = new User();
        user.GetFromStorage(context);

        roomsBdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count=0;
                created_rooms.clear();
                for (DataSnapshot snap_data : snapshot.getChildren()) {
                    Room bd_room = snap_data.getValue(Room.class);
                    count++;
                    if(bd_room.owner.num_tel.equals(user.num_tel)){
                        created_rooms.add(bd_room);
                    }
                    if(snapshot.getChildrenCount() == count){
                        if(created_rooms==null){
                            callbacks.callback(new ArrayList<Room>());
                        }else{
                            callbacks.callback(created_rooms);
                        }

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("room error","error error");
                callbacks.callback(created_rooms);
            }
        });
    }

    public void GetMyJoinedRooms(Context context, OnDataRecievedRooms callbacks ){
        ArrayList<Room> my_joined_rooms = new ArrayList<Room>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference roomsBdRef = database.getReference("rooms");
        User user = new User();
        user.GetFromStorage(context);

        roomsBdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count=0;
                my_joined_rooms.clear();
                for (DataSnapshot snap_data : snapshot.getChildren()) {
                    Room bd_room = snap_data.getValue(Room.class);
                    count++;
                    ArrayList<User> users_in_room= bd_room.users;
                    for (User user_in_room : users_in_room){
                        if(user_in_room.num_tel.equals(user.num_tel) && !bd_room.owner.num_tel.equals(user.num_tel)){
                            my_joined_rooms.add(bd_room);
                        }
                    }
                    if(snapshot.getChildrenCount() == count){
                        callbacks.callback(my_joined_rooms);
                    }


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("room error","error error");
                callbacks.callback(my_joined_rooms);
            }
        });
    }

    public void GetRoomByID(String room_id, OnDataRecievedRoom callback){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference roomsBD_ref = database.getReference("rooms");
        Query checkID = roomsBD_ref.orderByChild("room_id").equalTo(room_id);

        checkID.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot snap_data : snapshot.getChildren()) {
                        Room bd_room = snap_data.getValue(Room.class);
                        Log.d("bd_room bd_room",bd_room.toString());
                        callback.callback(bd_room);
                    }
                }
                else{
                    callback.callback(null);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("room login error","error error");
                callback.callback(null);
            }
        });

    }


    @Override
    public String toString() {
        return "Room{" +
                "room_name='" + room_name + '\'' +
                ", room_key='" + room_key + '\'' +
                ", category='" + category + '\'' +
                ", room_id='" + room_id + '\'' +
                ", room_image='" + room_image + '\'' +
                ", owner=" + owner +
                ", users=" + users +
                ", room_public=" + room_public +
                '}';
    }
}
