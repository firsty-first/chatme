package com.example.chatme;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.chatme.databinding.ActivitySettingsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class settingsActivity extends AppCompatActivity {
FirebaseDatabase database;
FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private static final String PREF_NAME = "MyAppPreferences";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_EMAIL = "user_email";
    private StorageReference storageReference;
    private ProgressDialog progressDialog;
ActivitySettingsBinding binding;
int SELECT_PICTURE=13102010;
    @Override
    protected void onStart() {
        super.onStart();
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUserDetails();
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        progressDialog = new ProgressDialog(this);
        binding.userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

    }

    private void setUserDetails() {
        auth = FirebaseAuth.getInstance();

        FirebaseUser currentuser = auth.getCurrentUser();

        binding.userName.setText(getUserName(getApplicationContext()));
        binding.emailId.setText(getUserEmail(getApplicationContext()));

        databaseReference = FirebaseDatabase.getInstance().getReference("user");

        // Get reference to the specific user's node using their UID
        DatabaseReference userReference = databaseReference.child(currentuser.getUid());

        //
        if(currentuser.getPhotoUrl()==null)
        {
            binding.userProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //startActivity(new Intent(this,));
                }
            });
        }
        else {
            binding.userProfile.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return false;
                }
            });
        }
        }

        // the Select Image Button is clicked
        void imageChooser() {


        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select a nice profile picture"), SELECT_PICTURE);
    }

        // this function is triggered when user
        // selects the image from the imageChooser
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    binding.userProfile.setImageURI(selectedImageUri);
                    binding.userProfile.setMaxWidth(6);
                    binding.userProfile.setMaxHeight(12);
                    // Get the file extension
                   // String fileExtension = getFileExtension(selectedImageUri);

                    // Generate a unique filename for the uploaded image
                    String fileName = auth.getCurrentUser().getUid();

                    // Set up the progress dialog
                    progressDialog.setMessage("Uploading...");
                    progressDialog.show();
                    StorageReference fileReference = storageReference.child(fileName);

                    // Upload the file to Firebase Storage
                    fileReference.putFile(selectedImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.dismiss();
                                    Log.d("user","important it is");
                                    storageReference.child(fileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Log.d("user","uri save in process");
                                            insertImageUrlintoUserData(uri.toString());
                                            progressDialog.dismiss();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("user","cant get link");
                                            Log.d("user",e.getMessage());
                                        }
                                    });

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(settingsActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
                }
            }
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    // Retrieve user's name
    public static String getUserName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, "");
    }

    // Retrieve user's email
    public static String getUserEmail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, "");
    }
    public void insertImageUrlintoUserData(String uri) {
        // Get current user ID from FirebaseAuth
        Log.d("user","uri save in process 2");
        String userId = auth.getCurrentUser().getUid();

        // Push the user object to the database
        Map<String, Object> userUpdates = new HashMap<>();

        userUpdates.put("profilepic", uri);

        // Update the specific fields within the user node
        database.getReference().child("user").child(userId).updateChildren(userUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("user","uri save success");
                Toast.makeText(settingsActivity.this, "success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("user","uri save failed");
            }
        });


    }
        }
