package com.example.vetid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    EditText emailUsername,userPassword;
    Button login;
    TextView forgotPassword, signUpRedirect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        emailUsername =findViewById(R.id.emailForm);
        userPassword= findViewById(R.id.emailForm);
        login =findViewById(R.id.loginButton);
        forgotPassword =findViewById(R.id.forgotPassword);
        signUpRedirect =findViewById(R.id.signUp);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=emailUsername.getText().toString();
                String password=userPassword.getText().toString();

                if(username.length()==0|| password.length()==0){
                    Toast.makeText(getApplicationContext(),"Fill all of the details to login",Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,RegisterPage.class));
            }
        });
    }
}