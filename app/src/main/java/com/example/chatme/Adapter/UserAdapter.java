package com.example.chatme.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatme.R;
import com.example.chatme.UserModel;
import com.example.chatme.chatscreen;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.ViewHolder> implements Filterable
{
ArrayList<UserModel> data;
   ArrayList<UserModel> backup;
Context context;

    public UserAdapter(ArrayList<UserModel> list, Context context) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
UserModel userModel= data.get((position));
        //Picasso.get().load(userModel.getProfile_pic()).placeholder(R.drawable.chat).into((Target) holder.imageView);
        holder.userName.setText(userModel.getUserName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, chatscreen.class);

                intent.putExtra("userId",userModel.getUserId());
                intent.putExtra("userName",userModel.getUserName());
                intent.putExtra("profilePic",userModel.getProfile_pic());
//              intent.putExtra("userId",userModel.getLastMessage());
                context.startActivity(intent);


            }
        });

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
TextView userName,lastMessage;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.userImg);
        userName=itemView.findViewById(R.id.userName);
        lastMessage=itemView.findViewById(R.id.lastmessage);
    }
}
}
