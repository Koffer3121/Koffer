package com.example.koffer.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.koffer.R;

public class RegisterActivity extends AppCompatActivity {
    public String email;
    public String passwd1;
    public String passwd2;
    public String tel;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email).toString();
        passwd1 = findViewById(R.id.pasword).toString();
        passwd2 = findViewById(R.id.pasword2).toString();
        tel = findViewById(R.id.tel).toString();
        button.findViewById(R.id.btnRegistro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
