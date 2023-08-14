package com.example.chatme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.chatme.databinding.ChatscreenUiActivityBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class chatscreen extends AppCompatActivity {
    ChatscreenUiActivityBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ChatscreenUiActivityBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        final String senderId=auth.getUid();
        String recieverId=getIntent().getStringExtra("userId");
        String recieverImg=getIntent().getStringExtra("profilePic");
        String recieverName=getIntent().getStringExtra("userName");
binding.name.setText(recieverName);
        Picasso.get().load(recieverImg).placeholder(R.drawable.parrot).into(binding.userImage);
        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}