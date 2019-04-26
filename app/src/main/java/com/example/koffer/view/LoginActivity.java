package com.example.koffer.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.koffer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {
    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;

    private EditText email, passwd;
    private Button btnLogin;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //inizializamos el objeto firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        passwd = findViewById(R.id.pasword);
        progressDialog = new ProgressDialog(this);

        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login(){
        final String correo = email.getText().toString().trim();
        String password = passwd.getText().toString().trim();

        if (TextUtils.isEmpty(correo) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "Se deben rellenar todos los campos", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Iniciando sesion...");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(correo, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Bienvenido: " + email.getText(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, SelectLoginActivity.class);
                    startActivity(intent);
                }else {
                    if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                        Toast.makeText(LoginActivity.this, "Este usuario no esta registrado!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this, "No se pudo iniciar sesion.", Toast.LENGTH_LONG).show();
                    }
                }
                progressDialog.dismiss();
            }
        });

    }
}
