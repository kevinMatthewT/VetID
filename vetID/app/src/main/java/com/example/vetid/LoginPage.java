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

public class LoginPage extends AppCompatActivity {

    EditText emailUsername,userPassword;
    Button login,signup;
    TextView forgotPassword, signUpRedirect;
    FirebaseAuth mAuth;

    //sends user to home page if user is signed in
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent redirect= new Intent(getApplicationContext(),MainActivity.class);
            startActivity(redirect);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //References the id of the elements in the page
        emailUsername =findViewById(R.id.emailForm);
        userPassword= findViewById(R.id.passwordForm);
        login =findViewById(R.id.loginButton);
        forgotPassword =findViewById(R.id.forgotPassword);
        signUpRedirect =findViewById(R.id.signUp);
        mAuth=FirebaseAuth.getInstance();

        //Login button that reads in the field input that the user put in
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email=String.valueOf(emailUsername.getText());
                password=String.valueOf(userPassword.getText());

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Fill all of the details to login",Toast.LENGTH_SHORT).show();
                    return;
                }

                //Checks if the user is signed in properly and exists
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                                    Intent redirect= new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(redirect);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginPage.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        //redirects to the sign up page if user has no account
        signUpRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirect= new Intent(getApplicationContext(),RegisterPage.class);
                startActivity(redirect);
                finish();
            }
        });


    }
}