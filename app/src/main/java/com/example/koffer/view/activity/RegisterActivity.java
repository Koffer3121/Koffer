package com.example.koffer.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koffer.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;


public class RegisterActivity extends AppCompatActivity {

    TextView textView;

    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mRef;

    private EditText name, email, passwd1, phone;
    private ProgressDialog progressDialog;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupComponents();
        setupStyles();
    }


    private void setupStyles() {
        TextView checkbox = findViewById(R.id.pdf);
        String text = "He leído y acepto los Términos del servicio y la Política de privacidad de Koffer";
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan fcsBlue = new ForegroundColorSpan(Color.BLUE);
        ForegroundColorSpan fcsBlue2 = new ForegroundColorSpan(Color.BLUE);
        UnderlineSpan underlineSpan = new UnderlineSpan();
        UnderlineSpan underlineSpan2 = new UnderlineSpan();
        ss.setSpan(underlineSpan, 22, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(underlineSpan2, 48, 81, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(fcsBlue, 22, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(fcsBlue2, 48, 81, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkbox.setText(ss);

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/14rMza6evlSd9qfor8sJL0f4rn3jd_c2o/view?usp=sharing"));
                startActivity(intent);
            }
        });
    }

    private void setupComponents() {
        firebaseAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();

        name = findViewById(R.id.registerName);
        email = findViewById(R.id.email);
        passwd1 = findViewById(R.id.password);
        phone = findViewById(R.id.registerPhone);
        checkBox = findViewById(R.id.checkBox);

        Button btnRegister = findViewById(R.id.btnRegister);
        progressDialog = new ProgressDialog(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    registrarUsuario();
                } else {
                    Toasty.info(getApplicationContext(), "Debes de aceptar los terminos y condiciones", Toasty.LENGTH_LONG).show();
                }
            }
        });
    }

    public void registrarUsuario(){
        //.trim elimina espacios de delante y de detras del texto
        final String userName = name.getText().toString().trim();
        String correo = email.getText().toString().trim();
        String password = passwd1.getText().toString().trim();
        final String phoneNumber = phone.getText().toString().trim();

        if (TextUtils.isEmpty(correo) || TextUtils.isEmpty(password) || TextUtils.isEmpty(userName)){
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
                        mRef.child("users").child(firebaseUser.getUid()).child("name").setValue(userName);
                        mRef.child("users").child(firebaseUser.getUid()).child("email").setValue(firebaseUser.getEmail());
                        mRef.child("users").child(firebaseUser.getUid()).child("phone_num").setValue(phoneNumber);
                        mRef.child("users").child(firebaseUser.getUid()).child("isTransportist").setValue(false);
                    }

                    Toasty.info(RegisterActivity.this, "Registro completado, A continuacion inicie sesion.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);


                }else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toasty.info(RegisterActivity.this, "Este usuario ya esta registrado!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }else {
                        Toasty.error(RegisterActivity.this, "No se puede registrar el usuario.", Toast.LENGTH_LONG).show();
                    }
                }
                progressDialog.dismiss();
            }
        });
    }
}
