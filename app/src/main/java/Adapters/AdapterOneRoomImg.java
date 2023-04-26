package Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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
import java.util.List;

public class AdapterOneRoomImg extends RecyclerView.Adapter<AdapterOneRoomImg.ViewHolder> {

    ArrayList<String> images;
    Context context;
    LayoutInflater inflater;
    CreateRoomFragment fragment;
    FragmentTransaction transaction;

    public AdapterOneRoomImg(Context ctx, ArrayList<String> images,CreateRoomFragment fragment,FragmentTransaction transaction){
        this.context=ctx;
        this.images=images;
        this.inflater = LayoutInflater.from(ctx);
        this.fragment = fragment;
        this.transaction=transaction;
    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("Image Fetch", "Error getting bitmap", e);
        }
        return bm;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.one_room_img,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       // holder.image.setImageBitmap( getImageBitmap(images.get(position) ));
        Glide.with(this.context)
                .load(images.get(position))
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("image", images.get(position));
                fragment.setArguments(bundle);
                transaction.commit();
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
