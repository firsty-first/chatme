package com.example.chatme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatme.Adapter.UserAdapter;
import com.example.chatme.databinding.FragmentChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class chatFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
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
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

      binding= FragmentChatBinding.inflate(inflater, container, false);
        SharedPreferences sharedPreferences =getContext(). getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

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
                    Log.d("user",auth.getCurrentUser().getUid());
                    Log.d("user",auth.getCurrentUser().getEmail());
                     if( auth.getCurrentUser().getUid().equals(userModel.getUserId())) {
                        Log.d("user","entered into if");
                        userModel.setUserName(userModel.getUserName() + "(Me)");
                        saveUserName(getContext(),userModel.getUserName().substring(0,userModel.getUserName().length()-4));
                        saveUserEmail(getContext(),userModel.getMail());
             }
              binding.dashboardname.setText("Howdy ,"+getUserName(getContext()));
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
                adapter.notifyDataSetChanged();
                if (arrayList.size() > 0) {
                    int lastItemPosition = arrayList.size() - 1;
                    binding.chatRv.scrollToPosition(lastItemPosition);

                    binding.chatRv.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
                        @Override
                        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                            // Handle touch events

                            return false; // Return true if the touch event is consumed
                        }
                    });

                }
                searc();

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
    void searc()
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
    public void createUserNode(String userName, String mail, String password, String lastMessage,
                               String hobbey, String location, String availability, String about) {
        // Get current user ID from FirebaseAuth
        String userId = auth.getCurrentUser().getUid();

        // Create a new User object
        UserModel user = new UserModel();


        // Push the user object to the database
        database.getReference().child("users").child(userId).setValue(user);
    }
    // Save user's name
    public static void saveUserName(Context context, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, name);
        editor.apply();
    }
    // Save user's email
    public static void saveUserEmail(Context context, String email) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }
    public static String getUserName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, "");
    }
}