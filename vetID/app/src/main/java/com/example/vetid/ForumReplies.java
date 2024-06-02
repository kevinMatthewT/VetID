package com.example.vetid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ForumReplies extends AppCompatActivity {

    TextView forumUser,forumInformation, vetIdLogo ,reply,usersusername, returnForumPage;

    RecyclerView recyclerView;

    EditText replyfield;

    ArrayList<RepliesModel> replyList;
    RepliesAdapter repliesAdapter;
    //for connection to firebase
    FirebaseFirestore fstore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_replies);

        String forumUsername=getIntent().getStringExtra("forumName");
        String forumDescription=getIntent().getStringExtra("forumDescription");
        String forumUUID=getIntent().getStringExtra("forumUUID");

        forumUser=findViewById(R.id.displayUsername);
        forumInformation=findViewById(R.id.displayForum);

        forumUser.setText(forumUsername);
        forumInformation.setText(forumDescription);

        recyclerView=findViewById(R.id.allForumReplies);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        fstore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        replyList=new ArrayList<RepliesModel>();
        repliesAdapter=new RepliesAdapter(ForumReplies.this,replyList);
        recyclerView.setAdapter(repliesAdapter);

        reply=findViewById(R.id.submitReply);
        replyfield=findViewById(R.id.replyField);
        usersusername=findViewById(R.id.usersUsername);

        returnForumPage=findViewById(R.id.backToForum);
        vetIdLogo=findViewById(R.id.logo);
        vetIdLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ForumReplies.this,HomePage.class));

            }

        });

        returnForumPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForumReplies.this,ForumPage.class));
            }
        });

        DocumentReference documentUser=fstore.collection("users").document(auth.getCurrentUser().getUid());

        documentUser.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                usersusername.setText(value.getString("username"));
            }
        });

        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting all values of the fields that exist in the form
                String formDescription = String.valueOf(replyfield.getText());
                String forminf= String.valueOf(usersusername.getText());

                //error handing to make sure important details are not empty
                if (TextUtils.isEmpty(formDescription)) {
                    Toast.makeText(getApplicationContext(), "Fill all of the details to create schedule", Toast.LENGTH_SHORT).show();
                    return;
                }

                //set an appointment to a random UUID
                UUID uuid = UUID.randomUUID();


                //Referencing to the appointment collection in the doctor collection
                DocumentReference documentReply = fstore.collection("forum").document(forumUUID).collection("replies").document(uuid.toString());

                //putting all of the fields to place
                Map<String, Object> reply = new HashMap<>();
                reply.put("forumUsernameReply", forminf);
                reply.put("forumContentReply", formDescription);

                //placing all of the data in each hashmap in their respective collection
                documentReply.set(reply).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });



        eventChageListener(forumUUID);
    }

    private void eventChageListener(String forumUUID) {
        fstore.collection("forum").document(forumUUID).collection("replies")
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
                                replyList.add(dc.getDocument().toObject(RepliesModel.class));

                            }
                            repliesAdapter.notifyDataSetChanged();
                        }

                        return;
                    }


                });
    }
}