package com.example.chatme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chatme.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
  private   FirebaseAuth firebaseAuth;
  FirebaseDatabase database;
  ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // getSupportActionBar().hide();
        progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Creating acccount");
        progressDialog.setMessage("creatign your acount");
        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(binding.editTextTextEmailAddress.getText().toString(),
                        binding.editTextTextPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            UserModel user=new UserModel(binding.edittextName.toString(),binding.editTextTextEmailAddress.toString(),binding.editTextTextPassword.toString());
                        String id=task.getResult().getUser().getUid();
                        database.getReference().child("user").child(id).setValue(user);
                            Toast.makeText(MainActivity.this, "User created successful", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
    }
}