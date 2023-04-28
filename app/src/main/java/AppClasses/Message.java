package AppClasses;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import Interfaces.OnDataRecievedMessage;
import Interfaces.OnDataRecievedMessages;

public class Message {
    public String message_id="";
    public String room_id="";
    public String user_id="";
    public String user_image="";
    public String user_name="";
    public String content="";

    public Message() {
    }

    public void SendMessage(Context context, String room_id, String content, OnDataRecievedMessage callback){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference BD_messages = database.getReference("messages");
        Message message = this;

        User user = new User();
        user.GetFromStorage(context);

        message.message_id = BD_messages.push().getKey();
        message.room_id = room_id;
        message.user_id = user.num_tel;
        message.user_image = user.user_img;
        message.user_name = user.userName;
        message.content = content;

        BD_messages.child(message.message_id).setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.callback(message);
                } else {
                    callback.callback(null);
                }
            }
        });
    }

    public void GetMessagesByRoom(Context context, String room_id,  OnDataRecievedMessages callback){
        ArrayList<Message> messages = new ArrayList<Message>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference roomsBdRef = database.getReference("messages");

        roomsBdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count=0;
                messages.clear();
                for(DataSnapshot snap_data : snapshot.getChildren()){
                    Message message = snap_data.getValue(Message.class);
                    count++;
                    if(message.room_id.equals(room_id)){
                        messages.add(message);
                    }
                    if(snapshot.getChildrenCount() == count){
                        if(messages==null){
                            callback.callback(new ArrayList<Message>());
                        }else{
                            callback.callback(messages);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.callback(messages);
            }
        });

    }

    @Override
    public String toString() {
        return "Message{" +
                "message_id='" + message_id + '\'' +
                ", room_id='" + room_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_image='" + user_image + '\'' +
                ", user_name='" + user_name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
