package com.mariaincyberspace.lostandfound_1.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.FirebaseDatabase;
import com.mariaincyberspace.lostandfound_1.R;
import com.mariaincyberspace.lostandfound_1.data.repository.AuthenticationRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.data.repository.ItemRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.databinding.ActivityMainBinding;
import com.mariaincyberspace.lostandfound_1.domain.model.Item;
import com.mariaincyberspace.lostandfound_1.domain.repository.OnCallBack;
import com.mariaincyberspace.lostandfound_1.domain.use_case.SignOut;
import com.mariaincyberspace.lostandfound_1.services.CoordinatesToPlaceService;
import com.mariaincyberspace.lostandfound_1.utils.Literals;

import java.util.List;


public class MainActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private AuthenticationRepositoryImpl authRepository;

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
                R.id.nav_all_items, R.id.nav_add)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        authRepository = new AuthenticationRepositoryImpl(getApplication());
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
        new SignOut(authRepository).signOut();
        Intent i = new Intent(MainActivity.this, LogoActivity.class);
        finish();
        startActivity(i);
    }



    // todo: remove this test click and the button
    public void onClickTestTest(MenuItem item) {
        ItemRepositoryImpl itemRepository = new ItemRepositoryImpl(getApplication(),
                FirebaseDatabase.getInstance().getReference().child(Literals.Nodes.ITEM_KEY));

        itemRepository.getAllItems(items -> {
            Log.d("\nMy ActLog: all items: ", "");
            for (Item i: items) {
                Log.d("Item: ", "'" + i.getName());
            }
        });

        itemRepository.getCurrentUsersItems(authRepository.getCurrentUserId(), items -> {
            Log.d("\nMy ActLog: current: ", "");
            for (Item i: items) {
                String address = CoordinatesToPlaceService.getPlace(i.getLatitude(), i.getLongitude());
                Log.d("Item: ", "'" + i.getName() + "', " + address);
            }
        });

    }

}
