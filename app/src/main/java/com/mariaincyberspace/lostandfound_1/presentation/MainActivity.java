package com.mariaincyberspace.lostandfound_1.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mariaincyberspace.lostandfound_1.R;
import com.mariaincyberspace.lostandfound_1.data.repository.AuthenticationRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.databinding.ActivityMainBinding;
import com.mariaincyberspace.lostandfound_1.domain.model.Item;
import com.mariaincyberspace.lostandfound_1.domain.use_case.SignOut;
import com.mariaincyberspace.lostandfound_1.presentation.auth.LoginViewModel;
import com.mariaincyberspace.lostandfound_1.utils.Literals;

import java.util.HashSet;

public class MainActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private AuthenticationRepositoryImpl repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_add, R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        repository = new AuthenticationRepositoryImpl(getApplication());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void onClickSignOut(MenuItem item) {
        new SignOut(repository).signOut();
        Intent i = new Intent(MainActivity.this, LogoActivity.class);
        finish();
        startActivity(i);
    }

    // todo: remove this test click and the button
    public void onClickTestTest(MenuItem item) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child(Literals.ITEM_KEY).child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    System.out.println(ds.toString());

                    for (DataSnapshot s : ds.getChildren()) {
                        System.out.println(s.toString() + ", " + s.getValue().getClass() + ", "
                                + s.getKey().getClass());

                        // Output
                        /*
                        I/System.out: DataSnapshot { key = latitude, value = 23.37641222503183 }
                        I/System.out: DataSnapshot { key = longitude, value = -9.471363499760628 }
                        I/System.out: DataSnapshot { key = name, value = gloves 2 }
                        I/System.out: DataSnapshot { key = photoUri, value = https://firebasestorage.googleapis.com/v0/b/lostandfound-d0834.appspot.com/o/1647490380039aaa.jpg?alt=media&token=eb9a3775-8e00-4b91-824b-eb3ddbca9cf5 }

                        * */
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

//    public void onClickAddItem(MenuItem item) {
//
//    }

}