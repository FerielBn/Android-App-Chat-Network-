package com.example.miniprojet;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adapters.AdapterOneRoomImg;

public class ChooseRoomImgFragment extends Fragment implements LifecycleOwner{
    private RecyclerView ImageList;
    private ArrayList<String> images;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_room_img, container, false);
        ImageList = view.findViewById(R.id.ImageList);
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

        images = new ArrayList<String>();
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361794/rooms/aujg4dlvuisaljz7qjwl.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361750/rooms/h7ts30derao2pcwwaj4d.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361750/rooms/afc9lrhtldgbzre63ojy.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361750/rooms/njckuetqksg7y7q7ln8m.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361750/rooms/jjcrh4lauadgswgatkmd.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361749/rooms/eiktjzpdx8yxkkdm5lua.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361749/rooms/bpp00qse8ig7f8qolyf3.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361748/rooms/a4r0ewhp4cey0ypbn7ng.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361748/rooms/wbgywq2lxngknaykj4g5.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361748/rooms/nixnqn3crk8di7abhvij.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361747/rooms/aqzsfaeafpcdari70rrv.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361746/rooms/qzxn54zlrkay9hhfgeqm.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361746/rooms/fggxmahcqqo03cxs5x0k.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361746/rooms/qnda0vrqopxof3g22lfk.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361746/rooms/e55fzp8lejkm4wqqsgs7.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361746/rooms/rpshtnadyfadwyrpum8a.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361746/rooms/fysid57f6p40nzpukmrc.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361745/rooms/njl5f2lwnae2gjup29hz.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361745/rooms/bzo2fo24aydmkmjwd6b5.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361744/rooms/aoeodpwgebgd4yf7v6fk.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361744/rooms/t34h4hdsn05bgp5pmjp2.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361744/rooms/nvpfdxzormhcdioyww4b.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361744/rooms/gd7bjt1xcftzmm7ssgk6.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361743/rooms/fc0kwbf27jqhevbvhkmw.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682361742/rooms/qysyskzvgyazylfeizel.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682512285/rooms/h63hq08t2aqrfnmj9nyv.png");
        images.add("https://res.cloudinary.com/hatem/image/upload/v1682512373/rooms/a3lypdqyhdnyupmee2le.png");



        // here a code to go back to CreateRoomFragment with selected Item
        CreateRoomFragment create_room_fragm =  new CreateRoomFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, create_room_fragm);
        transaction.addToBackStack(null);

        AdapterOneRoomImg adapter = new AdapterOneRoomImg(getContext(),images,create_room_fragm,transaction);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL,false);
        ImageList.setLayoutManager(gridLayoutManager);
        ImageList.setAdapter(adapter);
        ImageList.setClickable(true);

    }

}