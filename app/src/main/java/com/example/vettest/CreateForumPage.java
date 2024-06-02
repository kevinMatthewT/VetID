package com.example.vettest;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.vettest.databinding.ActivityCreateForumPageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateForumPage extends AppCompatActivity {

    EditText description;
    TextView returnButton,username,vetIdLogo;

    Button submitButton;

    String UID;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_forum_page);

        username = findViewById(R.id.usernameForum);
        description = findViewById(R.id.descriptionForumField);
        returnButton = findViewById(R.id.forumPageRedirect);
        submitButton = findViewById(R.id.submitForumButton);
        vetIdLogo = findViewById(R.id.logo);

        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        UID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("users").document(UID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                username.setText(value.getString("username"));
            }
        });

        vetIdLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForumPage.class));
                finish();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting all values of the fields that exist in the form
                String formTitle = String.valueOf(username.getText());
                String formDescription = String.valueOf(description.getText());

                //error handing to make sure important details are not empty
                if (TextUtils.isEmpty(formDescription)) {
                    Toast.makeText(getApplicationContext(), "Fill all of the details to create schedule", Toast.LENGTH_SHORT).show();
                    return;
                }

                //set an appointment to a random UUID
                UUID uuid = UUID.randomUUID();

                //Referencing to the appointment collection in the doctor collection
                DocumentReference documentForum = fstore.collection("forum").document(uuid.toString());

                //putting all of the fields to place
                Map<String, Object> forum = new HashMap<>();
                forum.put("forumUsername", formTitle);
                forum.put("forumDescription", formDescription);
                forum.put("forumUUID", uuid.toString());

                //placing all of the data in each hashmap in their respective collection
                documentForum.set(forum).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(), ForumPage.class));
                        finish();
                    }
                });


            }
        });
    }
}