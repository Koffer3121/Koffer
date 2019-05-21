package com.example.koffer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.koffer.view.activity.LoginActivity;
import com.example.koffer.view.activity.SelectLoginActivity;
import com.example.koffer.view.activity.SlideActivity;
import com.karan.churi.PermissionManager.PermissionManager;

public class MainActivity extends AppCompatActivity {

    PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionManager = new PermissionManager() {};
        permissionManager.checkAndRequestPermissions(this);

        Intent intent = new Intent(MainActivity.this, SlideActivity.class);
        startActivity(intent);


        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.checkResult(requestCode, permissions, grantResults);
    }

}
