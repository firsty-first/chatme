package com.example.chatme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chatme.databinding.ActivityFilter2Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class filterActivity extends AppCompatActivity {
    ActivityFilter2Binding binding;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    String distanceSlider,spinnerItem;
    String hobbey="";
    Boolean a = false, b = false, c = false, d = false, e = false, f = false, g = false, h = false;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFilter2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Drawable cardBgDrawablefinal = ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext_bg_filled);
        int colortextfinal = ContextCompat.getColor(getApplicationContext(), R.color.white);
        Drawable cardBgDrawableinit = ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_bg);
        int colortextinit = ContextCompat.getColor(getApplicationContext(), R.color.themeblue);

        binding.buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(filterActivity.this, "Updated ", Toast.LENGTH_SHORT).show();
                //here i would update the data in database which would automatically update the view

                createUserNode();
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                onBackPressed();
            }
        });
        binding.c12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b == false) {
                    binding.c12.setBackground(cardBgDrawablefinal);
                    binding.textView12.setTextColor(colortextfinal);
                    hobbey=hobbey+" "+binding.textView12.getText();
                    b = !b;
                } else {
                    binding.c12.setBackground(cardBgDrawableinit);
                    binding.textView12.setTextColor(colortextinit);
                    hobbey=hobbey.replace("Business","");
                    b = !b;
                }
            }
        });
        binding.c32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c == false) {
                    binding.c32.setBackground(cardBgDrawablefinal);
                    binding.textView32.setTextColor(colortextfinal);
                    hobbey=hobbey+" "+binding.textView32.getText();
                    c = !c;
                } else {
                    binding.c32.setBackground(cardBgDrawableinit);
                    binding.textView32.setTextColor(colortextinit);
                    hobbey=hobbey.replace("Business","");
                    c = !c;
                }
            }
        });
        binding.c31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (d == false) {
                    binding.c31.setBackground(cardBgDrawablefinal);
                    binding.textView31.setTextColor(colortextfinal);
                    d = !d;
                } else {
                    binding.c31.setBackground(cardBgDrawableinit);
                    binding.textView31.setTextColor(colortextinit);
                    d = !d;
                }
            }
        });
        binding.c23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (e == false) {
                    binding.c23.setBackground(cardBgDrawablefinal);
                    binding.textView23.setTextColor(colortextfinal);
                    e = !e;
                } else {
                    binding.c23.setBackground(cardBgDrawableinit);
                    binding.textView23.setTextColor(colortextinit);
                    e = !e;
                }
            }
        });
        binding.c22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (f == false) {
                    binding.c22.setBackground(cardBgDrawablefinal);
                    binding.textView22.setTextColor(colortextfinal);
                    hobbey=hobbey+" "+binding.textView22.getText();
                    f = !f;
                } else {
                    binding.c22.setBackground(cardBgDrawableinit);
                    binding.textView22.setTextColor(colortextinit);
                    f = !f;
                }
            }
        });
        binding.c21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (g == false) {
                    binding.c21.setBackground(cardBgDrawablefinal);
                    binding.textView21.setTextColor(colortextfinal);
                    g = !g;
                } else {
                    binding.c21.setBackground(cardBgDrawableinit);
                    binding.textView21.setTextColor(colortextinit);
                    g = !g;
                }
            }
        });

        binding.c13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (h == false) {
                    binding.c13.setBackground(cardBgDrawablefinal);
                    binding.textView113.setTextColor(colortextfinal);
                    h = !h;
                } else {
                    binding.c13.setBackground(cardBgDrawableinit);
                    binding.textView113.setTextColor(colortextinit);
                    h = !h;
                }
            }
        });
        binding.c11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (a == false) {
                    binding.c11.setBackground(cardBgDrawablefinal);
                    binding.textView11.setTextColor(colortextfinal);
                    a = !a;
                } else {
                    binding.c11.setBackground(cardBgDrawableinit);
                    binding.textView11.setTextColor(colortextinit);
                    a = !a;
                }
            }
        });
        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        SeekBar slider = findViewById(R.id.seekbardistance);

// Custom drawable for the thumb
        // Drawable customThumb = ContextCompat.getDrawable(getApplicationContext(), R.drawable.seekbarhead);
        // slider.setThumb(customThumb);

// SeekBar change listener (to handle thumb updates if needed)
        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // You can update the custom thumb here to reflect the current value
                // This can be done by creating a new Drawable with the updated shape/line
                // and then setting it as the thumb of the SeekBar
                distanceSlider=String.valueOf(progress);

                binding.textViewProgress.setText(String.valueOf(progress) + "Km");

                // Calculate the position for the TextView based on SeekBar thumb's position
                float thumbX = seekBar.getX() + ((float) progress / seekBar.getMax()) * seekBar.getWidth();
                float thumbY = seekBar.getY();


                // Updating thhe position of the TextView
                // binding.textViewProgress.setX(thumbX - binding.textViewProgress.getWidth() / 2); // Center the text view under the thumb
                // binding.textViewProgress.setY(thumbY - binding.textViewProgress.getHeight() - 10); // Adjust Y position as needed

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Handle when the user stops interacting with the slider
               // distanceSlider=seekBar.toString();
            }
        });


        Spinner spinner = findViewById(R.id.spinner);


        String[] items = {"Available | Hey Let Us Connect", "Away | Stay descrete and watch", "Busy | Do not disturb", "SOS | Emergency! "};

// Create an adapter to display the items
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Set the adapter to the spinner
        spinner.setAdapter(adapter);

// Implement a listener to handle item selection
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = items[position];
                spinnerItem=selectedItem;
                // Handle the selected item
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case when nothing is selected
            }
        });
    }

    public void createUserNode() {
        // Get current user ID from FirebaseAuth
        String userId = firebaseAuth.getCurrentUser().getUid();

        String hobbey="coffee";
        String userName=firebaseAuth.getCurrentUser().getDisplayName();
        UserModel user = new UserModel();
        user.setAbout(binding.aboutEditText.getText().toString());
        user.setAvailability(spinnerItem);
        user.setHobbey(hobbey);
        user.setDistance(distanceSlider);
        // Push the user object to the database
        Map<String, Object> userUpdates = new HashMap<>();

        userUpdates.put("hobbey", hobbey);
        userUpdates.put("distance", distanceSlider);
        userUpdates.put("availability", spinnerItem);
        userUpdates.put("about", binding.aboutEditText.getText().toString());

        // Update the specific fields within the user node
        database.getReference().child("user").child(userId).updateChildren(userUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(filterActivity.this, "success", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.spinnermenu, menu);
        return true;
    }
}