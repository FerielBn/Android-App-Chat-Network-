package com.example.miniprojet;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

public class MyRoomFragment extends Fragment implements LifecycleOwner {

    private Lifecycle lifecycle;
    private Button CreateRoomBtn;
    private Button JoiRoomBtn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_rooms, container, false);
        CreateRoomBtn = view.findViewById(R.id.CreateBtn);
        JoiRoomBtn = view.findViewById(R.id.JoinBtn);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycle = getLifecycle();
        lifecycle.addObserver(new MyObserver(getContext()));
    }

    private class MyObserver implements LifecycleEventObserver {
        private Context context;

        public MyObserver(Context context) {
            this.context = context;
        }
        @Override
        public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
            switch (event) {
                case ON_CREATE:
                    // Do something when the fragment is created
                    break;
                case ON_START:
                    // Do something when the fragment is started
                    break;
                case ON_RESUME:
                    // Do something when the fragment is resumed
                    break;
                case ON_PAUSE:
                    // Do something when the fragment is paused
                    break;
                case ON_STOP:
                    // Do something when the fragment is stopped
                    break;
                case ON_DESTROY:
                    // Do something when the fragment is destroyed
                    break;
            }
        }
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        super.onStart();
        CreateRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateRoomFragment newFragment = new CreateRoomFragment();

                Bundle bundle = new Bundle();
                bundle.putString("key", "AYA 9OLNA SALAM");

                newFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        JoiRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JoinRoomFragment newFragment = new JoinRoomFragment();

                Bundle bundle = new Bundle();
                bundle.putString("key", "AYA 9OLNA SALAM");

                newFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
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
