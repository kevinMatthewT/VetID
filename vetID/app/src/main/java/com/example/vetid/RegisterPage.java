package com.example.vetid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterPage extends AppCompatActivity {
    EditText emailUsername,userPassword;
    Button registerUser;
    TextView signInRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        emailUsername=findViewById(R.id.emailFormRegister);
        userPassword=findViewById(R.id.passwordFormRegister);

        registerUser= findViewById(R.id.registerButton);

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=emailUsername.getText().toString();
                String password=userPassword.getText().toString();

                if(username.length()==0|| password.length()==0){
                    Toast.makeText(getApplicationContext(),"Fill all of the details to login",Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterPage.this,HomePage.class));
                }
            }
        });

        signInRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterPage.this,LoginPage.class));
            }
        });
    }
}