package com.example.koffer.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.koffer.R;
import com.example.koffer.model.Carrier;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    private static final String USERS_REFERENCE = "users";

    private FirebaseAuth firebaseAuth;

    private EditText email, password;
    private CheckBox mCheckBoxRemember;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupComponents();
    }

    private void setupComponents() {
        firebaseAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mCheckBoxRemember = findViewById(R.id.checkBox);
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
        final String email = this.email.getText().toString().trim();
        String password = this.password.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(this, getString(R.string.empty_fields), Toast.LENGTH_LONG).show();
        } else {
            progressDialog.setMessage(getString(R.string.logging_in));
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            String userUid = user.getUid();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference(USERS_REFERENCE);
                            myRef.child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    getUserInformationAndDecideWhereTheUserShouldGo(dataSnapshot);
                                    progressDialog.dismiss();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.e("ERROR", "Error al hacer el addListenerForSingleValueEvent");
                                }
                            });
                        }
                    } else {
                        if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                            progressDialog.dismiss();
                            Toasty.error(getApplicationContext(), getString(R.string.user_not_registered), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                            startActivity(intent);
                        } else {
                            progressDialog.dismiss();
                            Toasty.error(getApplicationContext(), getString(R.string.logging_in_error), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    }

    private void getUserInformationAndDecideWhereTheUserShouldGo(@NonNull DataSnapshot dataSnapshot) {
        Carrier userInf = dataSnapshot.getValue(Carrier.class);
        if (userInf != null) {
            boolean value = userInf.isTransportist;
            if (value) {
                Toasty.success(getApplicationContext(),
                        getString(R.string.welcome) + " " + email.getText().toString().trim(),
                        Toast.LENGTH_SHORT, true).show();
                Intent intent = new Intent(getApplicationContext(), CarrierViewActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toasty.success(getApplicationContext(),
                        getString(R.string.welcome) + " " + email.getText().toString().trim(),
                        Toast.LENGTH_SHORT, true).show();
                Intent intent = new Intent(getApplicationContext(), UserViewActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
