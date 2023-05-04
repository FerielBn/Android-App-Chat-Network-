package Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.miniprojet.ChatChatChatRoomFragment;
import com.example.miniprojet.CreateRoomFragment;
import com.example.miniprojet.LoginActivity;
import com.example.miniprojet.MainActivity;
import com.example.miniprojet.R;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import AppClasses.Room;
import AppClasses.User;
import Interfaces.OnDataRecieve;
import Interfaces.OnDataRecievedRoom;

public class AdapterOneRoom extends RecyclerView.Adapter<AdapterOneRoom.ViewHolder> {

    ArrayList<Room> rooms;
    Context context;
    LayoutInflater inflater;
    int ViewItem;
    OnDataRecievedRoom callback;


    public AdapterOneRoom(Context ctx, ArrayList<Room> rooms, int ViewItem,OnDataRecievedRoom callback){
        this.context=ctx;
        this.rooms=rooms;
        this.inflater = LayoutInflater.from(ctx);
        this.ViewItem=ViewItem;
        this.callback=callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(this.ViewItem,parent,false);
        boolean isWhite = this.ViewItem == R.layout.one_room_item_white;
        return new ViewHolder(view,isWhite);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
       // holder.image.setImageBitmap( getImageBitmap(images.get(position) ));
        Room room = rooms.get(position);
        String room_type= room.room_public ? "Public" : "Private";
        String roomCategAcc = room_type + " - " + room.category;
        String roomNumbers = "Users in room : " + room.users.size();

        Glide.with(this.context).load(room.room_image).into(holder.image);
        holder.RoomName.setText(room.room_name);
        holder.RoomCategAcc.setText(roomCategAcc);
        holder.RoomUsersNumbers.setText(roomNumbers);

        if(holder.isWhite){

            User user = new User();
            user.GetFromStorage(context);

            boolean isOwner= room.owner.num_tel.equals(user.num_tel);

            if(isOwner){
                holder.EditRoom.setVisibility(View.VISIBLE);
                holder.DeleteRoom.setVisibility(View.VISIBLE);
            }else{
                holder.LeaveRoom.setVisibility(View.VISIBLE);
            }

            holder.DeleteRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete Room" + room.room_name);
                    builder.setMessage("Are you sure you want to delete ?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Toast.makeText(context, "I will delete " + room.room_name, Toast.LENGTH_SHORT).show();
                            room.DeleteRoom(context, room.room_id, new OnDataRecieve() {
                                @Override
                                public void callback() {
                                    Toast.makeText(context, "Room deleted", Toast.LENGTH_SHORT).show();
                                    callback.callback(null);
                                }
                            });

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
            });

            holder.EditRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "I will Edit " + room.room_name, Toast.LENGTH_SHORT).show();
                    room.owner = null;
                    callback.callback(room);
                }
            });

            holder.LeaveRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "I will Leave " + room.room_name, Toast.LENGTH_SHORT).show();
                    room.LeaveRoom(context, room.room_id, new OnDataRecievedRoom() {
                        @Override
                        public void callback(Room room) {
                            callback.callback(null);
                        }
                    });
                }
            });
            holder.LinearLayoutImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.callback(room);
                }
            });

            holder.LinearLayoutTexts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.callback(room);
                }
            });


        }else{
            holder.PurpleRoomCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    room.JoinRoomPublic(context, room.room_id, new OnDataRecievedRoom() {
                        @Override
                        public void callback(Room room) {
                           callback.callback(room);
                        }
                    });
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView RoomName;
        TextView RoomCategAcc;
        TextView RoomUsersNumbers;

        Button EditRoom;
        Button DeleteRoom;
        Button LeaveRoom;

        LinearLayout PurpleRoomCard;
        LinearLayout LinearLayoutImage;
        LinearLayout LinearLayoutTexts;

        boolean isWhite=false;


        public ViewHolder(@NonNull View itemView,boolean isWhite) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            RoomName = itemView.findViewById(R.id.RoomNameTV);
            RoomCategAcc = itemView.findViewById(R.id.categ_accessTV);
            RoomUsersNumbers = itemView.findViewById(R.id.users_numberTV);
            this.isWhite = isWhite;
            if(isWhite){
                EditRoom=itemView.findViewById(R.id.EditBtn);
                DeleteRoom=itemView.findViewById(R.id.DeleteBtn);
                LeaveRoom=itemView.findViewById(R.id.LeaveBtn);

                EditRoom.setVisibility(View.GONE);
                DeleteRoom.setVisibility(View.GONE);
                LeaveRoom.setVisibility(View.GONE);

                LinearLayoutImage =itemView.findViewById(R.id.LinearLayoutImage);
                LinearLayoutTexts =itemView.findViewById(R.id.LinearLayoutTexts);
            }else{
                PurpleRoomCard = itemView.findViewById(R.id.roomcard);
            }

        }
    }
}
