package com.example.koffer.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.koffer.R;

public class SelectLoginActivity extends AppCompatActivity {

    Button registro;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login);

//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

//        if (sharedPreferences.getBoolean("IS_LOGIN",true)){
//            sharedPreferences.edit().putBoolean("IS_LOGIN", false).apply();
//        } else {
//            startActivity(new Intent(this, MainMenuActivity.class));
//            finish();
//        }

        setupComponents();
    }

    private void setupComponents() {
        registro = findViewById(R.id.register);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectLoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(SelectLoginActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
