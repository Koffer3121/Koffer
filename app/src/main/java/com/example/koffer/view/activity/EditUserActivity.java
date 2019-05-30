package com.example.koffer.view.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class EditUserActivity extends AppCompatActivity {

    private static final String USERS_REFERENCE = "users";


    EditText name, email, password, phone;

    FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    FirebaseUser user;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        setupComponents();
        setupFirebaseComponents();
    }


    private void setupComponents() {
        name = findViewById(R.id.editUserName);
        email = findViewById(R.id.editUserEmail);
        password = findViewById(R.id.editUserPassword);
        phone = findViewById(R.id.editUserPhone);
        Button save = findViewById(R.id.saveChanges);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
                Intent intent = new Intent(EditUserActivity.this, UserViewActivity.class);
                startActivity(intent);
                Toasty.info(getApplicationContext(), "Datos guardados con exito", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setupFirebaseComponents() {
        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference(USERS_REFERENCE);
        user = FirebaseAuth.getInstance().getCurrentUser();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user == null) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.putExtra("goToActivityUsers", true);
                    startActivity(intent);
                    finish();
                } else {
                    final String userUid = user.getUid();
                    mDataBase.child(userUid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            getUserInformation(dataSnapshot);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        };
    }


    private void getUserInformation(DataSnapshot dataSnapshot) {
        UserInformation userInf = dataSnapshot.getValue(UserInformation.class);
        if (userInf != null) {
            if (userInf.email != null && userInf.name != null && userInf.phone_num != null) {
                String userName = userInf.name;
                String userEmail = userInf.email;
                String userPhone = userInf.phone_num;
                name.setText(userName);
                email.setText(userEmail);
                phone.setText(userPhone);
            } else {
                Log.e("Error", "algo ha salido mal");
            }

        }
    }

    public void setUserName() {
        final String newName = name.getText().toString();
        if (TextUtils.isEmpty(newName))
            return;

        mDataBase.child(user.getUid()).child("name").setValue(newName);
    }


    public void setUserEmailAddr() {
        final String newEmail = email.getText().toString();
        if (TextUtils.isEmpty(newEmail))
            return;

        user.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mDataBase.child(user.getUid()).child("email").setValue(newEmail);
                }
            }
        });
    }

    public void setUserPassword() {
        String newPassword = password.getText().toString();
        if (TextUtils.isEmpty(newPassword))
            return;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                        Toast.makeText(getApplicationContext(), "password updated", Toast.LENGTH_SHORT).show();
                }
            });
        }
        password.getText().clear();
        password.setHint("******");
    }

    public void setUserPhone() {
        final String newPhone = phone.getText().toString();
        if (TextUtils.isEmpty(newPhone))
            return;

        mDataBase.child(user.getUid()).child("phone_num").setValue(newPhone);
    }

    private void saveChanges() {
        setUserName();
        setUserEmailAddr();
        setUserPassword();
        setUserPhone();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
