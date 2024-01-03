package com.example.chatme;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.chatme.Adapter.UserAdapter;
import com.example.chatme.databinding.FragmentChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class chatFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION =2 ;
    private String mParam1;
    private String mParam2;
    FragmentChatBinding binding;
    UserAdapter adapter;
    ArrayList<UserModel> arrayList=new ArrayList<>();
    FirebaseDatabase database;
    FirebaseAuth auth;
    SharedPreferences userDetails;
    private static final String PREF_NAME = "MyAppPreferences";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_EMAIL = "user_email";
    private LocationManager locationManager1;
    SharedPreferences sharedPreferences;

    public chatFragment() {
        // Required empty public constructor
    }


    public static chatFragment newInstance(String param1, String param2) {
        chatFragment fragment = new chatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        locationManager1 = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        startLocationUpdates();
      binding= FragmentChatBinding.inflate(inflater, container, false);
        SharedPreferences sharedPreferences =getContext(). getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        if(GPSUtils.isGPSEnabled(getContext()))
            binding.gpsTurn.setVisibility(View.GONE);
        binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b)
            GPSUtils.openGPSSettings(getContext());
        if(GPSUtils.isGPSEnabled(getContext()))
            binding.gpsTurn.setVisibility(View.GONE);



    }
});
binding.dismissBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        binding.gpsTurn.setVisibility(View.GONE);
    }
});
        Log.d("locfromShared",sharedPreferences.getString("latitude","0").toString()+","+sharedPreferences.getString("longitude"," ").toString());
        binding.optionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), settingsActivity.class));
            }
        });
        database.getReference().child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               arrayList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    UserModel userModel=dataSnapshot.getValue(UserModel.class);
                    userModel.setUserId(dataSnapshot.getKey());
                    Log.d("sort",userModel.getUserName());
                    Log.d("user",auth.getCurrentUser().getEmail());
                     if( auth.getCurrentUser().getUid().equals(userModel.getUserId())) {
                        Log.d("user","entered into if");
                        userModel.setUserName(userModel.getUserName() + "(Me)");
                        saveUserName(getContext(),userModel.getUserName().substring(0,userModel.getUserName().length()-4));
                        saveUserEmail(getContext(),userModel.getMail());

             }

                    arrayList.add(userModel);
                    adapter=new UserAdapter(arrayList,getContext(),Double.parseDouble(sharedPreferences.getString("latitude","0")),Double.parseDouble(sharedPreferences.getString("longitude","0")));
                    binding.chatRv.setAdapter(adapter);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
                    binding.chatRv.setLayoutManager(layoutManager);
                    Log.d("search debug",userModel.getUserName());
                   //
                }
                //Collections.sort(arrayList, Comparator.comparingLong(UserModel::getLastmessageTime).reversed());
                // Sort the ArrayList in descending order based on the time property using lambda expression
binding.chatRv.smoothScrollToPosition(0);
                binding.dashboardname.setText("Hi,"+" "+getUserName(getContext()));
                adapter.notifyDataSetChanged();
                if (arrayList.size() > 0) {
                    int lastItemPosition = arrayList.size() - 1;
                    //binding.chatRv.scrollToPosition(lastItemPosition);

                    binding.chatRv.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
                        @Override
                        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                            // Handle touch events

                            return false; // Return true if the touch event is consumed
                        }
                    });

                }
                searchUserInChatFrag();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), filterActivity.class));
            }
        });

      return  binding.getRoot();
    }
    void searchUserInChatFrag()
    {


        binding.searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

    }

    //saving in user node
    public void createUserNode(double latitude, double longitude) {
        // Get current user ID from FirebaseAuth
        String userId = auth.getCurrentUser().getUid();

        UserModel user = new UserModel();

        // Push the user object to the database
        Map<String, Object> userUpdates = new HashMap<>();

        userUpdates.put("latitude",latitude);
        userUpdates.put("longitude",longitude);



        // Update the specific fields within the user node
        database.getReference().child("user").child(userId).updateChildren(userUpdates);




    }
    // Save user's name
    public static void saveUserName(Context context, String name) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

            // Check if the name is not already saved before saving
            if (!sharedPreferences.contains(KEY_NAME)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_NAME, name);
                editor.apply();
            }
        } catch (Exception e) {
            Log.e("error", e.getMessage().toString());
        }
    }
    // Save user's email
    public static void saveUserEmail(Context context, String email) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

            // Check if the email is not already saved before saving
            if (!sharedPreferences.contains(KEY_EMAIL)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_EMAIL, email);
                editor.apply();
            }
        } catch (Exception e) {
            Log.d("error", e.getMessage().toString());
        }
    }

    public static String getUserName(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_NAME, "myname");
        } catch (Exception e) {
            Log.e("error", e.getMessage().toString());
        }
        return "myname";
    }

    private void startLocationUpdates() {
        // Check if location services are enabled

        if (locationManager1 != null) {
            // Define a listener that responds to location updates
            Log.d("location","null nhi hai");
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    // Called when a new location is found
                    Log.d("location","listener ke andar");
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    //String myloc=Double.toString(latitude)+","+Double.toString(longitude);
                    createUserNode(latitude,longitude);
                    Log.d("location", "Latitude: " + latitude + ", ongitude: " + longitude);
                    // Get SharedPreferences instance

// Get SharedPreferences editor
                    SharedPreferences.Editor editor = sharedPreferences.edit();

// Put the string value
                    editor.putString("latitude", Double.toString(latitude));
                    editor.putString("longitude", Double.toString(longitude));

// Apply changes
                    editor.apply();

                    // You can use the location data as needed
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                    Log.d("location",Integer.toString(status));
                }

                @Override
                public void onProviderEnabled(String provider) {

                    Log.d("location","provider enabled");
                    binding.gpsTurn.setVisibility(View.GONE);
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Log.d("location","provider disabled");
                    binding.gpsTurn.setVisibility(View.VISIBLE);
                }
            };

            // Register the listener with the Location Manager to receive location updates
            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.


                return;
            }
            locationManager1.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100, locationListener);
        }
        else
            Log.d("location","null hai");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start location updates
                startLocationUpdates();
            } else {

                // Permission denied, handle accordingly
                Log.d("location", "Location permission denied");
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        // Check GPS status when the fragment comes back into the foreground
        if (binding.switch1.isChecked()) {
            // The switch is ON, check if GPS is enabled
            if (!GPSUtils.isGPSEnabled(requireContext())) {
                // GPS is not enabled, handle it (e.g., show a message to the user)
                binding.switch1.setChecked(false); // Turn off the switch since GPS is not enabled
            }
            else
                binding.switch1.setChecked(true);
        }
    }

}