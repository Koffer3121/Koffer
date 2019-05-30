package com.example.koffer.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.koffer.R;
import com.example.koffer.model.UserInformation;
import com.example.koffer.view.activity.EditUserActivity;
import com.example.koffer.view.activity.HistoryUserActivity;
import com.example.koffer.view.activity.SelectLoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreUserFragment extends Fragment {

    private static final String USERS_REFERENCE = "users";

    Button btnSignOut;
    TextView name, editProfile, history;

    FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    FirebaseUser user;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public MoreUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more_user, container, false);

        editProfile = view.findViewById(R.id.editPerfil);
        history = view.findViewById(R.id.history);
        name = view.findViewById(R.id.moreUserName);
        btnSignOut = view.findViewById(R.id.btnSignOut);

        setupFirebaseComponents();

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditUserActivity.class);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryUserActivity.class);
                startActivity(intent);
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), SelectLoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

    private void setupFirebaseComponents() {
        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference(USERS_REFERENCE);
        user = FirebaseAuth.getInstance().getCurrentUser();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user != null) {
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
                name.setText(userName);
            } else {
                Log.e("Error", "algo ha salido mal");
            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

}
