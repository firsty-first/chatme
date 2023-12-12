package com.example.chatme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.chatme.Adapter.ChatAdapter;
import com.example.chatme.databinding.ChatscreenUiActivityBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class chatscreen extends AppCompatActivity {
    ChatscreenUiActivityBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    String senderRoom, reciverRoom;
    String senderId;
    ArrayList<messagesModel> messagesModels;
    ChatAdapter chatAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        messagesModels = new ArrayList<>();
        chatAdapter = new ChatAdapter(messagesModels, getApplicationContext());
        binding.chatRv.setAdapter(chatAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setSmoothScrollbarEnabled(true);
        binding.chatRv.setLayoutManager(layoutManager);
        NotificationUtils.createNotificationChannel(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ChatscreenUiActivityBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        senderId = auth.getUid();
        String recieverId = getIntent().getStringExtra("userId");
        String recieverImg = getIntent().getStringExtra("profilePic");
        String recieverName = getIntent().getStringExtra("userName");
        binding.name.setText(recieverName);
        Picasso.get().load(recieverImg).placeholder(R.drawable.parrot).into(binding.userImage);
        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);

                onBackPressed();

            }
        });
//final ArrayList<messagesModel> messagesModels=new ArrayList<>();
// ChatAdapter chatAdapter=new ChatAdapter(messagesModels,getApplicationContext());
//binding.chatRv.setAdapter(chatAdapter);
//        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
//        binding.chatRv.setLayoutManager(layoutManager);


        senderRoom = senderId + recieverId;
        reciverRoom = recieverId + senderId;
//
        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeMsgIndatabase();
            }
        });

        database.getReference().child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messagesModels.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            messagesModel model = snapshot1.getValue(messagesModel.class);
                            messagesModels.add(model);
                            Log.d("hii", "got into if");
                            Log.d("hii", model.getMessages());
                        }
                        chatAdapter.notifyDataSetChanged();

                        if (!AppVisibilityTracker.getInstance().isAppInForeground()) {
                            Log.d("notification", "show notif");
                            showNotification();}

                            else
                            Log.d("notification","not show notif");




                        if (messagesModels.size() > 0) {
                            int lastItemPosition = messagesModels.size() - 1;
                            binding.chatRv.scrollToPosition(lastItemPosition);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("db", "Dberror");
                    }
                });
    }


    void storeMsgIndatabase() {

        String msg = binding.editTextText.getText().toString();
        if (msg.length() > 0) {
            final messagesModel model = new messagesModel(senderId, msg);

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
        }
    }

    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(chatscreen.this, NotificationUtils.CHANNEL_ID)
                .setSmallIcon(R.drawable.parrot)
                .setContentTitle("New message Arrived")
                .setContentText("Tap tp view it ")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
     Log.d("notif","no permission");
            return;
        }
        notificationManager.notify(1, builder.build());
    }

}