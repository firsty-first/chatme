package com.example.chatme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.chatme.Adapter.ChatAdapter;
import com.example.chatme.databinding.ChatscreenUiActivityBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

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
final ArrayList<messagesModel> messagesModels=new ArrayList<>();
final ChatAdapter chatAdapter=new ChatAdapter(messagesModels,this);
binding.chatRv.setAdapter(chatAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatRv.setLayoutManager(layoutManager);
final String senderRoom=senderId+recieverId;
final String reciverRoom=recieverId+senderRoom;
binding.sendBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
String msg=binding.editTextText.getText().toString();
if(msg.length()>0)
{
final messagesModel model=new messagesModel(senderId,msg);

model.setTimestamp(new Date().getTime());
binding.editTextText.setText("");
database.getReference().child("chats")
        .child(senderRoom)
        .push()
        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                database.getReference().child("chats")
                        .child(reciverRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });

            }
        });
    }}
});



    }
}