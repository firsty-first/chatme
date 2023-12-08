package com.example.chatme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chatme.databinding.ActivityFilter2Binding;

public class filterActivity extends AppCompatActivity {
    ActivityFilter2Binding binding;
    Boolean a = false, b = false, c = false, d = false, e = false, f = false, g = false, h = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFilter2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Drawable cardBgDrawablefinal = ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext_bg_filled);
        int colortextfinal = ContextCompat.getColor(getApplicationContext(), R.color.white);
        Drawable cardBgDrawableinit = ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_bg);
        int colortextinit = ContextCompat.getColor(getApplicationContext(), R.color.themeblue);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(filterActivity.this, "Updated ", Toast.LENGTH_SHORT).show();
                //here i would update the data in database which would automatically update the view

                onBackPressed();
            }
        });
        binding.c12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b == false) {
                    binding.c12.setBackground(cardBgDrawablefinal);
                    binding.textView12.setTextColor(colortextfinal);
                    b = !b;
                } else {
                    binding.c12.setBackground(cardBgDrawableinit);
                    binding.textView12.setTextColor(colortextinit);
                    b = !b;
                }
            }
        });
        binding.c32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c == false) {
                    binding.c32.setBackground(cardBgDrawablefinal);
                    binding.textView2.setTextColor(colortextfinal);
                    c = !c;
                } else {
                    binding.c32.setBackground(cardBgDrawableinit);
                    binding.textView2.setTextColor(colortextinit);
                    c = !c;
                }
            }
        });
        binding.c31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (d == false) {
                    binding.c31.setBackground(cardBgDrawablefinal);
                    binding.textView.setTextColor(colortextfinal);
                    d = !d;
                } else {
                    binding.c31.setBackground(cardBgDrawableinit);
                    binding.textView.setTextColor(colortextinit);
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
        SeekBar slider = findViewById(R.id.slider);

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
                // Handle the selected item
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case when nothing is selected
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.spinnermenu, menu);
        return true;
    }

}