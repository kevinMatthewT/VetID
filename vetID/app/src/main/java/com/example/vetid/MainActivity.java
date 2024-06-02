package com.example.vetid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.vetid.OrdersFragment;
import com.example.vetid.ProfileFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        FirebaseAuth fAuth;
        FirebaseFirestore fstore;

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        } else if (itemId == R.id.nav_order) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OrdersFragment()).commit();
        } else if (itemId == R.id.nav_chat) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatFragment()).commit();
        } else if (itemId == R.id.nav_profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
        } else if (itemId == R.id.nav_doctor) {
            // Update Firestore when "Apply Doctor" is selected
            DocumentReference documentReference = fstore.collection("users")
                    .document(fAuth.getCurrentUser().getUid());
            Map<String, Object> toggle = new HashMap<>();
            toggle.put("is_doctor", true);

            documentReference.update(toggle).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // Handle successful update
                    Toast.makeText(getApplicationContext(), "You are now a doctor!", Toast.LENGTH_SHORT).show();

                    // Optionally, perform a fragment transaction if needed
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Handle failure
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (itemId == R.id.nav_groom) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ApplyGroomerFragment()).commit();
        } else if (itemId == R.id.nav_info) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
        }else if (itemId == R.id.nav_logout) {
            logout();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void logout() {
        // Perform logout using FirebaseAuth
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        fAuth.signOut();

        // Navigate to the login screen
        startActivity(new Intent(getApplicationContext(), LoginPage.class));

        // Finish the current activity
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}