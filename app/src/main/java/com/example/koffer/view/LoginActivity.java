package com.example.koffer.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.Contacts;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.koffer.R;
import com.example.koffer.model.UserInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Provider";
    private static final String UID = "UID";
    private static final String IST = "isTransporist";

    private static final String USERS_REFERENCE = "users";

    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;

    private EditText email, passwd;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        passwd = findViewById(R.id.pasword);
        progressDialog = new ProgressDialog(this);

        Button btnLogin = findViewById(R.id.btnLogin);
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
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        String uid = user.getUid();

                        Log.d(UID, uid);

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference(USERS_REFERENCE);
                        myRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                UserInformation user = dataSnapshot.getValue(UserInformation.class);

                                boolean value = user.isTransportist;

                                Log.d("isCarrier", String.valueOf(value));

                                if (value) {
                                    Intent intent = new Intent(getApplicationContext(), TestingActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), BottomNavigationViewActivity.class);
                                    startActivity(intent);
                                    finish();
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.e("ERROR", "Error en hacer el listener");
                            }
                        });
                    }
                    String uid;
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

                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                        Toast.makeText(LoginActivity.this, "Este usuario no está registrado!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "No se pudo iniciar sesión.", Toast.LENGTH_LONG).show();
                    }
                }
                progressDialog.dismiss();
            }
        });

    }
}
