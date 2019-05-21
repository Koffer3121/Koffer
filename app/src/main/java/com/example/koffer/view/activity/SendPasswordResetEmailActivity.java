package com.example.koffer.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.koffer.R;
import com.google.firebase.auth.FirebaseAuth;

public class SendPasswordResetEmailActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private EditText emailToRecoverPassword;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_password_reset_email);

        firebaseAuth = FirebaseAuth.getInstance();

        emailToRecoverPassword = findViewById(R.id.emailToRecoverPassword);
        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPasswordResetEmail();
                if (sendPasswordResetEmail()) {
                    Intent intent = new Intent(SendPasswordResetEmailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean sendPasswordResetEmail() {
        String mEmail = emailToRecoverPassword.getText().toString();
        if (TextUtils.isEmpty(mEmail)) {
            Toast.makeText(this, "please, enter en email to recover your password", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            firebaseAuth.sendPasswordResetEmail(mEmail);
            Toast.makeText(this, "email was sent to your email to reset password", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

}
