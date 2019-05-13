package com.example.koffer.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.koffer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class EditUserActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

//        mAuth = FirebaseAuth.getInstance();
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user == null) {
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                    intent.putExtra("goToActivityUsers", true);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    etxtEmail.setText(user.getEmail());
//                }
//            }
//        };
//
//        setTxtEmailEditable.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean editingEmail;
//
//                if (etxtEmail.isEnabled()) {
//                    etxtEmail.setEnabled(false);
//                    setTxtEmailEditable.setImageResource(R.drawable.ic_mode_edit_black_24dp);
//                    editingEmail = false;
//                } else {
//                    etxtEmail.setEnabled(true);
//                    setTxtEmailEditable.setImageResource(R.drawable.ic_check_black_24dp);
//                    etxtEmail.requestFocus();
//                    InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imgr.showSoftInput(etxtEmail, InputMethodManager.SHOW_IMPLICIT);
//                    editingEmail = true;
//                }
//
//                if (!editingEmail) {
//                    setUserEmailAddr(v);
//                }
//
//            }
//        });
//
//        setTxtPasswordEditable.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean editingPassword;
//
//                if (etxtPassword.isEnabled()) {
//                    etxtPassword.setEnabled(false);
//                    setTxtPasswordEditable.setImageResource(R.drawable.ic_mode_edit_black_24dp);
//                    editingPassword = false;
//                    etxtPassword.setHint("update password");
//                } else {
//                    etxtPassword.setEnabled(true);
//                    setTxtPasswordEditable.setImageResource(R.drawable.ic_check_black_24dp);
//                    etxtPassword.requestFocus();
//                    InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imgr.showSoftInput(etxtPassword, InputMethodManager.SHOW_IMPLICIT);
//                    editingPassword = true;
//                    etxtPassword.setHint("");
//                }
//
//                if (!editingPassword) {
//                    setUserPassword(v);
//                }
//            }
//        });
//
//        btnSignOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signOut(v);
//            }
//        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    public void getUserProviderProfileInfo(View view) {
//        FirebaseUser user = mAuth.getCurrentUser();
//        if (user != null) {
//            for (UserInfo profile: user.getProviderData()) {
//                // Id of the provider (ex: google.com)
//                String providerId = profile.getProviderId();
//
//                // UID specific to the provider
//                String uid = profile.getUid();
//
//                // Name, email address.........
//                String email = profile.getEmail();
//
//                Toast.makeText(getActivity(), "id : " + providerId + "uid : " + uid + "email :" + email, Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    public void setUserEmailAddr(View view) {
//        String newEmail = etxtEmail.getText().toString();
//        if (TextUtils.isEmpty(newEmail))
//            return;
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        user.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful())
//                    Toast.makeText(getActivity(), "email updated", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    public void setUserPassword(View view) {
//        String newPassword = etxtPassword.getText().toString();
//        if (TextUtils.isEmpty(newPassword))
//            return;
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful())
//                    Toast.makeText(getActivity(), "password updated", Toast.LENGTH_SHORT).show();
//            }
//        });
//        etxtPassword.getText().clear();
//        etxtPassword.setHint("******");
//    }
//
//    public void signOut(View view) {
//        mAuth.signOut();
//    }
}
