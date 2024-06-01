package com.example.vetid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.myViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<ForumModel> allForums;

    public ForumAdapter(Context context, ArrayList<ForumModel> allForums, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.allForums = allForums;
        this.recyclerViewInterface=recyclerViewInterface;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.forum_card,parent,false);
        return new ForumAdapter.myViewHolder(v,recyclerViewInterface);
    }

    //used to set the information grabbed from the appointment models into the appointment card
    @Override
    public void onBindViewHolder(@NonNull ForumAdapter.myViewHolder holder, int position) {
        ForumModel forum=allForums.get(position);

        holder.name.setText(forum.forumUsername);
        holder.description.setText(forum.forumDescription);
        holder.uuid.setText(forum.forumUUID);
    }

    //returns all of the items in the appointment list
    @Override
    public int getItemCount() {
        return allForums.size();
    }

    //Used to reference the id of texts in the appointment cards
    public static class myViewHolder extends RecyclerView.ViewHolder{

        TextView name,description,uuid;

        public myViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            name=itemView.findViewById(R.id.forumUsername);
            description=itemView.findViewById(R.id.forumContent);
            uuid=itemView.findViewById(R.id.uuidForum);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface!=null){
                        int pos=getAdapterPosition();

                        if (pos!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }

}
