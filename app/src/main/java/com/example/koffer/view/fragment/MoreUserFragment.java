package com.example.koffer.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.koffer.R;
import com.example.koffer.view.activity.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreUserFragment extends Fragment {

    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    EditText etxtEmail, etxtPassword;
    Button btnSignOut;
    ImageView setTxtEmailEditable, setTxtPasswordEditable;

    View view;

    public MoreUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_more_user, container, false);

        etxtEmail = view.findViewById(R.id.etxtEmail);
        etxtPassword = view.findViewById(R.id.etxtPassword);
        setTxtEmailEditable = view.findViewById(R.id.setTxtEmailEditable);
        setTxtPasswordEditable = view.findViewById(R.id.setTxtPasswordEditable);
        btnSignOut = view.findViewById(R.id.btnSignOut);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra("goToActivityUsers", true);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    etxtEmail.setText(user.getEmail());
                }
            }
        };

        setTxtEmailEditable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean editingEmail;

                if (etxtEmail.isEnabled()) {
                    etxtEmail.setEnabled(false);
                    setTxtEmailEditable.setImageResource(R.drawable.ic_mode_edit_black_24dp);
                    editingEmail = false;
                } else {
                    etxtEmail.setEnabled(true);
                    setTxtEmailEditable.setImageResource(R.drawable.ic_check_black_24dp);
                    editingEmail = true;
                }

                if (!editingEmail) {
                    setUserEmailAddr(v);
                }

            }
        });

        setTxtPasswordEditable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean editingPassword;

                if (etxtPassword.isEnabled()) {
                    etxtPassword.setEnabled(false);
                    setTxtPasswordEditable.setImageResource(R.drawable.ic_mode_edit_black_24dp);
                    editingPassword = false;
                    etxtPassword.setHint("update password");
                } else {
                    etxtPassword.setEnabled(true);
                    setTxtPasswordEditable.setImageResource(R.drawable.ic_check_black_24dp);
                    editingPassword = true;
                    etxtPassword.setHint("");
                }

                if (!editingPassword) {
                    setUserPassword(v);
                }
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut(v);
            }
        });

        return view;
    }

    public void getUserProviderProfileInfo(View view) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            for (UserInfo profile: user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address.........
                String email = profile.getEmail();

                Toast.makeText(getActivity(), "id : " + providerId + "uid : " + uid + "email :" + email, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setUserEmailAddr(View view) {
        String newEmail = etxtEmail.getText().toString();
        if (TextUtils.isEmpty(newEmail))
            return;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Toast.makeText(getActivity(), "email updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setUserPassword(View view) {
        String newPassword = etxtPassword.getText().toString();
        if (TextUtils.isEmpty(newPassword))
            return;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Toast.makeText(getActivity(), "password updated", Toast.LENGTH_SHORT).show();
            }
        });
        etxtPassword.getText().clear();
        etxtPassword.setHint("******");
    }

    public void signOut(View view) {
        mAuth.signOut();
    }

}
