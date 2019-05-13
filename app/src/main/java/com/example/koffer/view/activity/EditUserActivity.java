package com.example.koffer.view.activity;

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

import es.dmoral.toasty.Toasty;

public class EditUserActivity extends AppCompatActivity {

    private static final String USERS_REFERENCE = "users";
    String userName;
    String userEmail;
    String userPhone;


    EditText name, email, password, phone;

    FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        setupFirebaseComponents();
        setupComponents();
    }



    private void setupComponents() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        name = findViewById(R.id.editUserName);
        email = findViewById(R.id.editUserEmail);
        password = findViewById(R.id.editUserPassword);
        phone = findViewById(R.id.editUserPhone);
        Button save = findViewById(R.id.saveChanges);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
    }

    private void setupFirebaseComponents() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    final String userUid = user.getUid();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference(USERS_REFERENCE);
                    myRef.child(userUid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            getUserInformation(dataSnapshot);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toasty.error(getApplicationContext(), "Algo a salido mal", Toasty.LENGTH_SHORT).show();
                }
            }
        };
    }


    private void getUserInformation(DataSnapshot dataSnapshot) {
        UserInformation userInf = dataSnapshot.getValue(UserInformation.class);
        if (userInf != null) {
            if (userInf.email != null && userInf.name != null && userInf.phone_num != null) {
                userName = userInf.name;
                userEmail = userInf.email;
                userPhone = userInf.phone_num;
                name.setText(userName);
                email.setText(userEmail);
                phone.setText(userPhone);
            } else {
                Log.e("Error", "algo ha salido mal");
            }

        }
    }


    public void setUserEmailAddr() {
        String newEmail = email.getText().toString();
        if (TextUtils.isEmpty(newEmail))
            return;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Toast.makeText(getApplicationContext(), "email updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setUserPassword() {
        String newPassword = password.getText().toString();
        if (TextUtils.isEmpty(newPassword))
            return;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Toast.makeText(getApplicationContext(), "password updated", Toast.LENGTH_SHORT).show();
            }
        });
        password.getText().clear();
        password.setHint("******");
    }

    private void saveChanges() {
        setUserEmailAddr();
        setUserPassword();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
