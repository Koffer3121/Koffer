package com.example.koffer.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.koffer.MainActivity;
import com.example.koffer.R;

import com.example.koffer.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    EditText correo, passwd1, passwd2, tel;
    Button button;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        correo = findViewById(R.id.email);
        passwd1 = findViewById(R.id.pasword);
        passwd2 = findViewById(R.id.pasword2);
        tel = findViewById(R.id.tel);
        button=findViewById(R.id.registrarse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
                //falta comprobar que esta registrado correctamente y retornar al main
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void registrarUsuario(){
        // falta comprobar que ese usuario no este creado, que los campos esten llenos y que las contraseñas coincidan.
        String email = correo.getText().toString();
        String password = passwd1.getText().toString();
        String password2 = passwd2.getText().toString();
        String telephone = tel.getText().toString();


        String id = mDatabase.push().getKey();
        User user = new User(email, password, password2,telephone);
        mDatabase.child("users").child(id).setValue(user);
    }
}
