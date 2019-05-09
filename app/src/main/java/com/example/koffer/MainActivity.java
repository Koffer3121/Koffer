package com.example.koffer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.koffer.view.activity.LoginActivity;
import com.karan.churi.PermissionManager.PermissionManager;

public class MainActivity extends AppCompatActivity {

    PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

        permissionManager = new PermissionManager() {};
        permissionManager.checkAndRequestPermissions(this);

        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.checkResult(requestCode, permissions, grantResults);
    }

    //    String uid;
//    uid = getCurrent.guid()
//    view.findViewById();
//    this   =>   getActivity()
//    nclick(){
//    saco los dato
//    long =
//    lat  =
//    canti =
//    String suitcaseKey = getReference().push().getKey();
//    getReference().child("suitcase").child(suitcaseKey).setValue(suitcase);
//    Ref().child("user-suicase").child(uid).child(suitcaseKey).setValue(true);
//     }
    //uid = getCurrent.guid()

//        view.findViewById();

// this   =>   getActivity()

        /*
            nclick(){
                saco los dato
                long =
                lat  =
                canti =
                String suitcaseKey = getReference().push().getKey();
                getReference().child("suitcase").child(suitcaseKey).setValue(suitcase);
                Ref().child("user-suicase").child(uid).child(suitcaseKey).setValue(true);
            }
         */
}
