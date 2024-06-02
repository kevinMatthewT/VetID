package com.example.vetid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ForumPage extends AppCompatActivity implements RecyclerViewInterface{

    //empty page coming soon
    TextView vetIdLogo,makeForumRedirect;

    String UID;

    //for the display of all of the schedule/appointments created
    RecyclerView recyclerView;

    ArrayList<ForumModel> forumList;
    ForumAdapter forumAdapter;
    //for connection to firebase
    FirebaseFirestore fstore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_page);

        makeForumRedirect=findViewById(R.id.createForumRedirect);

        recyclerView=findViewById(R.id.allForums);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        fstore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        forumList=new ArrayList<ForumModel>();
        forumAdapter=new ForumAdapter(ForumPage.this,forumList,this);
        recyclerView.setAdapter(forumAdapter);



        vetIdLogo=findViewById(R.id.logo);
        vetIdLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ForumPage.this,MainActivity.class));

            }

        });

        makeForumRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForumPage.this, CreateForumPage.class));
                finish();
            }
        });


        eventChageListener();


    }

    private void eventChageListener() {
        fstore.collection("forum")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestore Error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                //gets the information based on the Appointment model
                                forumList.add(dc.getDocument().toObject(ForumModel.class));

                            }
                            forumAdapter.notifyDataSetChanged();
                        }

                        return;
                    }


                });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(ForumPage.this, ForumReplies.class);
        intent.putExtra("forumUUID",forumList.get(position).getForumUUID());
        intent.putExtra("forumName",forumList.get(position).getForumUsername());
        intent.putExtra("forumDescription",forumList.get(position).getForumDescription());

        startActivity(intent);
    }
}