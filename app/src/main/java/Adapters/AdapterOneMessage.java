package Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.miniprojet.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import AppClasses.Message;
import AppClasses.Room;
import AppClasses.User;
import Interfaces.OnDataRecievedRoom;

public class AdapterOneMessage extends RecyclerView.Adapter<AdapterOneMessage.ViewHolder> {

    ArrayList<Message> messages;
    Context context;
    LayoutInflater inflater;
    User user;


    public AdapterOneMessage(Context ctx, ArrayList<Message> messages){
        this.context=ctx;
        this.messages=messages;
        this.inflater = LayoutInflater.from(ctx);
        User user = new User();
        user.GetFromStorage(ctx);
        this.user= user;
    }

    @Override
    public int getItemViewType(int position) {
        boolean is_left = messages.get(position).user_id.equals(this.user.num_tel);
        if(is_left){
            return R.layout.one_message_right;
        }else{
            return R.layout.one_message_left;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(viewType,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Message message = messages.get(position);
        holder.UserComment.setText(message.content);
        holder.UserName.setText(message.user_name);
        Glide.with(this.context).load(message.user_image).into(holder.UserImage);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView UserImage;
        TextView UserName;
        TextView UserComment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            UserImage = itemView.findViewById(R.id.UserImage);
            UserName = itemView.findViewById(R.id.UserName);
            UserComment = itemView.findViewById(R.id.UserComment);
        }
    }
}
