package com.example.vetid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterPage extends AppCompatActivity {
    EditText emailUsername,userPassword,Username,phoneNumber;
    Button registerUser;
    TextView signInRedirect;

    FirebaseAuth mAuth;
    FirebaseFirestore fstore;

    String UID;

    //sends user to home page if user is signed in
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent redirect= new Intent(getApplicationContext(),HomePage.class);
            startActivity(redirect);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        //Reference the elements in the activity_register_page.xml
        emailUsername=findViewById(R.id.emailForm);
        userPassword=findViewById(R.id.passwordForm);
        Username=findViewById(R.id.usernameForm);
        phoneNumber=findViewById(R.id.phoneForm);

        registerUser= findViewById(R.id.registerButton);

        signInRedirect=findViewById((R.id.signIn));

        mAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        //button that is used to register a new user
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the user input in all of the field
                String email,password,username,phone;

                email=String.valueOf(emailUsername.getText());
                password=String.valueOf(userPassword.getText());
                username=String.valueOf(Username.getText());
                phone=String.valueOf(phoneNumber.getText());

                //makes sure field are not empty
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)|| TextUtils.isEmpty(phone) || TextUtils.isEmpty(username)){
                    Toast.makeText(getApplicationContext(),"Fill all of the details to register",Toast.LENGTH_SHORT).show();
                    return;
                }

                //registers the user using Firebase authentication
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    //gets the user id and put in the users collection with default information
                                    UID=mAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference=fstore.collection("users").document(UID);

                                    Map<String,Object> user= new HashMap<>();
                                    user.put("username",username);
                                    user.put("phone_number",phone);
                                    user.put("email",email);
                                    user.put("user_information","");
                                    user.put("sex","");
                                    user.put("age","");
                                    user.put("is_doctor",false);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    //redirects user to login page
                                    startActivity(new Intent(RegisterPage.this,LoginPage.class));

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterPage.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


            }

        });

        signInRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirect= new Intent(getApplicationContext(),LoginPage.class);
                startActivity(redirect);
            }
        });
    }
}