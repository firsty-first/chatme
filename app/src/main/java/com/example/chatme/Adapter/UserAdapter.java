package com.example.chatme.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatme.MapUtils;
import com.example.chatme.R;
import com.example.chatme.UserModel;
import com.example.chatme.ZoomableImageView;
import com.example.chatme.chatscreen;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.ViewHolder> implements Filterable
{
ArrayList<UserModel> data;
   ArrayList<UserModel> backup;
   double latitude,longitude;
Context context;

    public UserAdapter(ArrayList<UserModel> list, Context context,double latitude,double longitude) {
        this.data = list;
        this.context = context;

        backup=new ArrayList<>(data);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.rv_item_card_profile,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
UserModel userModel= data.get((position));
        Picasso.get().load(userModel.getProfilepic()).placeholder(R.drawable.defaultuserprofile).into( holder.imageView1);
        holder.userName.setText(userModel.getUserName());

        holder.hobbey.setText(userModel.getHobbey());
        holder.availability.setText(userModel.getAvailability());

        holder.about.setText("Status: "+userModel.getAbout());
        holder.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialogue(userModel.getUserId(),userModel.getUserName(),userModel.getProfilepic(),userModel.getLatitude(),userModel.getLongitude());
            }
        });

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

// Retrieve values using keys
        String latitudeString = sharedPreferences.getString("latitude", "0");
        String longitudeString = sharedPreferences.getString("longitude", "0");

// Convert strings to double
      latitude = Double.parseDouble(latitudeString);
        longitude = Double.parseDouble(longitudeString);

// Now you have the latitude and longitude values



        double distance=distance(latitude,longitude,userModel.getLatitude(),userModel.getLongitude());
      holder.progressBar.setProgress((int)(distance));
     // holder.progressBar.setMax(holder.progressBar.getMax()*10);
      //Log.d("progressbar",Integer.toString(holder.progressBar.getMax()*10));
     // Log.d("progressbar",Integer.toString(distance*10));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, chatscreen.class);

                intent.putExtra("userId",userModel.getUserId());
                intent.putExtra("userName",userModel.getUserName());
                intent.putExtra("profilePic",userModel.getProfilepic());
//              intent.putExtra("userId",userModel.getLastMessage());
                context.startActivity(intent);
            }
        });
holder.distanceTv.setText((int) distance+"km away");
    }
    public static double distance(double lat1,
                                  double lon1, double lat2,
                                  double lon2)
    {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;
        Log.d("distance",Double.toString(c*r));
        // calculate the result
        return Math.ceil(c * r);
    }

    private static double calculateHaversineDistance(double lat1, double lon1,double lat2,double lon2) {
        final int R = 6371; // Radius of the Earth in kilometers

        // Convert latitude and longitude from degrees to radians

        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Calculate the differences
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        // Haversine formula
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
System.out.println("distance between it"+ R*c);
        // Calculate distance
        Log.d("distance with ceil",Double.toString(Math.ceil(R * c)));
        return Math.ceil(R * c);
    }
    void showDialogPreviewImage(String profilepic,Context context,String name) // will be called on pressing on the image in previous dialog

    {
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.imagepreview);
        TextView username=dialog.findViewById(R.id.previewUsername);
        ImageView backBtn=dialog.findViewById(R.id.previewBackBtn);
        ZoomableImageView profilePreview = dialog.findViewById(R.id.zoomableImageView);

        Picasso.get().load(profilepic)
                .placeholder(R.drawable.defaultuserprofile)
                .into(profilePreview, new Callback() {
                    @Override
                    public void onSuccess() {
                        // Image loaded successfully
                    }

                    @Override
                    public void onError(Exception e) {
                        // Log or handle the error
                        e.printStackTrace();
                    }
                });


        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
           // window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        }

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.stay);

        username.setText(name);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Animation animation = AnimationUtils.loadAnimation(context, R.anim.stay);
                dialog.findViewById(R.id.imagePreviewLinearLayout).startAnimation(animation);

                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.imagePreviewLinearLayout).startAnimation(animation);

dialog.show();

    }
    void showdialogue(String userId, String name,String profilepic,double lati, double longi)
    {
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialoguepreview);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
        dialog.findViewById(R.id.messageBtnDialog).startAnimation(animation);
        dialog.findViewById(R.id.calllBtnDialog).startAnimation(animation);
        dialog.findViewById(R.id.vidCall).startAnimation(animation);
        dialog.findViewById(R.id.locationBtnDialog).startAnimation(animation);
        ImageView messagebuttonDialog=dialog.findViewById(R.id.messageBtnDialog);
       TextView nameDialog=dialog.findViewById(R.id.nameDialog);
       ImageView profile=dialog.findViewById(R.id.profilephotoDialog);

       nameDialog.setText(name);
        Picasso.get().load(profilepic).placeholder(R.drawable.defaultuserprofile).resize(500,540).into(profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialogPreviewImage(profilepic,context,name);
            }
        });
        messagebuttonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, chatscreen.class);

                intent.putExtra("userId",userId);
                intent.putExtra("userName",name);
                intent.putExtra("profilePic",profilepic);
//              intent.putExtra("userId",userModel.getLastMessage());
                context.startActivity(intent);
            }
        });
        ImageView callbuttonDialog=dialog.findViewById(R.id.calllBtnDialog);
        ImageView locationbuttonDialog=dialog.findViewById(R.id.locationBtnDialog);
        locationbuttonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapUtils.openGoogleMaps(context,Double.toString(lati),Double.toString(longi),"taking you there");
            }
        });
        callbuttonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "This feature will come soon", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        @Override
        // background thread
        protected FilterResults performFiltering(CharSequence keyword)
        {
            ArrayList<UserModel> filtereddata=new ArrayList<>();

            if(keyword.toString().isEmpty())
                filtereddata.addAll(backup);
            else
            {
                for(UserModel obj : backup)
                {
                    Log.d("search",obj.getUserName());
                    if(obj.getUserName().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                        filtereddata.add(obj);
                }
            }

            FilterResults results=new FilterResults();
            results.values=filtereddata;
            return results;
        }

        @Override  // main UI thread
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            data.clear();
            data.addAll((ArrayList<UserModel>)results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder
{
CardView imageView;
ImageView imageView1;
ProgressBar progressBar;
TextView userName,lastMessage,hobbey,availability,about,distanceTv;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.userImg);
        imageView1=itemView.findViewById(R.id.photo);
        userName=itemView.findViewById(R.id.userName);
        about=itemView.findViewById(R.id.about);
        hobbey=itemView.findViewById(R.id.hobbey);
        availability=itemView.findViewById(R.id.availability);
        progressBar=itemView.findViewById(R.id.progressBarprofile);
        distanceTv=itemView.findViewById(R.id.distanceTv);
    }
}
}
