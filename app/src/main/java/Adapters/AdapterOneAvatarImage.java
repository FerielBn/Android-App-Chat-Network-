package Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.miniprojet.CreateRoomFragment;
import com.example.miniprojet.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import Interfaces.OnDataRecieveImage;

public class AdapterOneAvatarImage extends RecyclerView.Adapter<AdapterOneAvatarImage.ViewHolder> {

    ArrayList<String> images;
    Context context;
    LayoutInflater inflater;
    OnDataRecieveImage callback;

    public AdapterOneAvatarImage(Context ctx, ArrayList<String> images,OnDataRecieveImage callback){
        this.context=ctx;
        this.images=images;
        this.inflater = LayoutInflater.from(ctx);
        this.callback=callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.one_avatar_img,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
       // holder.image.setImageBitmap( getImageBitmap(images.get(position) ));
        Glide.with(this.context)
                .load(images.get(position))
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.callback(images.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
        }
    }
}
