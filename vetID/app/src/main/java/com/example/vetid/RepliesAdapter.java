package com.example.vetid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RepliesAdapter extends RecyclerView.Adapter<RepliesAdapter.myViewHolder> {
    Context context;
    ArrayList<RepliesModel> allReplies;

    public RepliesAdapter(Context context, ArrayList<RepliesModel> allReplies) {
        this.context = context;
        this.allReplies = allReplies;
    }

    @NonNull
    @Override
    public RepliesAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.replies_card,parent,false);
        return new RepliesAdapter.myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RepliesAdapter.myViewHolder holder, int position) {
        RepliesModel reply=allReplies.get(position);

        holder.name.setText(reply.forumUsernameReply);
        holder.description.setText(reply.forumContentReply);
    }

    @Override
    public int getItemCount() {
        return allReplies.size();
    }


    public static class myViewHolder extends RecyclerView.ViewHolder{

        TextView name,description;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.forumUsernameReply);
            description=itemView.findViewById(R.id.forumContentReply);
        }
    }

}
