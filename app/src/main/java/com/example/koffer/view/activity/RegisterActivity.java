package com.example.koffer.view.activity;

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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {

    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mRef;

    private EditText name, email, passwd1;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //inizializamos el objeto firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();

        name = findViewById(R.id.registerName);
        email = findViewById(R.id.email);
        passwd1 = findViewById(R.id.password);
        Button btnRegister = findViewById(R.id.btnRegister);
        progressDialog = new ProgressDialog(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }

    public void registrarUsuario(){
        //.trim elimina espacios de delante y de detras del texto
        String correo = email.getText().toString().trim();
        String password = passwd1.getText().toString().trim();

        if (TextUtils.isEmpty(correo) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "Se deben rellenar todos los campos", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                    if (firebaseUser != null) {
                        mRef.child("users").child(firebaseUser.getUid()).child("email").setValue(firebaseUser.getEmail());
                        mRef.child("users").child(firebaseUser.getUid()).child("isTransportist").setValue(false);
                    }

                    Toast.makeText(RegisterActivity.this, "Registro completado, A continuacion inicie sesion.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);


                }else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(RegisterActivity.this, "Este usuario ya esta registrado!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(RegisterActivity.this, "No se puede registrar el usuario.", Toast.LENGTH_LONG).show();
                    }
                }
                progressDialog.dismiss();
            }
        });
    }
}
