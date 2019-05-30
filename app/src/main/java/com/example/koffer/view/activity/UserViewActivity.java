package com.example.koffer.view.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.koffer.R;
import com.example.koffer.view.fragment.HomeUserFragment;
import com.example.koffer.view.fragment.MapFragment;
import com.example.koffer.view.fragment.MoreUserFragment;
import com.example.koffer.view.fragment.ServiceFragment;

public class UserViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);
        setupComponents();
    }

    private void setupComponents() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_user);
        bottomNav.setOnNavigationItemSelectedListener(navListenerUser);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                new HomeUserFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListenerUser =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeUserFragment();
                            break;
                        case R.id.nav_service:
                            selectedFragment = new ServiceFragment();
                            break;
                        case R.id.nav_maps:
                            selectedFragment = new MapFragment();
                            break;
                        case R.id.nav_more:
                            selectedFragment = new MoreUserFragment();
                            break;

                    }
                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.fade_enter, R.anim.fade_exit)
                                .replace(R.id.fragment_container_user,
                                selectedFragment).commit();
                    }
                    return true;
                }
            };
}
