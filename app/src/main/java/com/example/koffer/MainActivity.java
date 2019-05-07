package com.example.koffer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.koffer.view.BottomNavigationViewActivity;
import com.example.koffer.view.LoginActivity;
import com.example.koffer.view.SelectLoginActivity;
import com.example.koffer.view.SlideActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, SelectLoginActivity.class);
        startActivity(intent);
        finish();
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
}
