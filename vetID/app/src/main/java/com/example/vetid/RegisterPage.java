package com.example.vetid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterPage extends AppCompatActivity {
    EditText emailUsername,userPassword,Username,phoneNumber;
    Button registerUser;
    TextView signInRedirect;

    FirebaseAuth mAuth;

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent redirect= new Intent(getApplicationContext(),HomePage.class);
//            startActivity(redirect);
//            finish();
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        emailUsername=findViewById(R.id.emailForm);
        userPassword=findViewById(R.id.passwordForm);
        Username=findViewById(R.id.usernameForm);
        phoneNumber=findViewById(R.id.phoneForm);

        registerUser= findViewById(R.id.registerButton);

        signInRedirect=findViewById((R.id.signIn));

        mAuth=FirebaseAuth.getInstance();

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password,username,phone;

                email=String.valueOf(emailUsername.getText());
                password=String.valueOf(userPassword.getText());
                username=String.valueOf(Username.getText());
                phone=String.valueOf(phoneNumber.getText());

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)|| TextUtils.isEmpty(phone) || TextUtils.isEmpty(username)){
                    Toast.makeText(getApplicationContext(),"Fill all of the details to register",Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    // FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
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