package com.example.koffer.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.koffer.R;
import com.example.koffer.model.Suitcase;
import com.example.koffer.model.UserSuitcase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServiceFragment extends Fragment {

    private static final String USERS_REFERENCE = "user-suitcase";

    View view;
    EditText name;
    EditText email;
    EditText phone;
    EditText dni;
    EditText quantity;
    EditText weight;
    EditText pickUpAddress;
    EditText deliveryAddress;
    Button btnService;


    private DatabaseReference mDatabase;
    FirebaseUser user;
    private FirebaseAuth.AuthStateListener mAuthListener;

    boolean statusOrder = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_service, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        name = view.findViewById(R.id.registerName);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        dni = view.findViewById(R.id.dni);
        quantity = view.findViewById(R.id.quantity);
        weight = view.findViewById(R.id.kg);
        pickUpAddress = view.findViewById(R.id.pickUpAddress);
        deliveryAddress = view.findViewById(R.id.deliveryAddress);

        btnService = view.findViewById(R.id.service);

        btnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setupFirebaseComponents();
                newService();
            }
        });
        return view;
    }

    public void newService(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        String name = this.name.getText().toString();
        String email = this.email.getText().toString();
        String phone = this.phone.getText().toString();
        String dniString = dni.getText().toString();
        String quantity = this.quantity.getText().toString();
        String weight = this.weight.getText().toString();
        String pickUpAddress = this.pickUpAddress.getText().toString();
        String deliveryAddress = this.deliveryAddress.getText().toString();
        Boolean carrierAsigned = false;

        if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(phone) || !TextUtils.isEmpty(dniString) || !TextUtils.isEmpty(quantity) || !TextUtils.isEmpty(weight) || !TextUtils.isEmpty(pickUpAddress) || !TextUtils.isEmpty((deliveryAddress))){
            String key = mDatabase.push().getKey();
            Suitcase suitCase = new Suitcase(name, email, phone, dniString, quantity, weight, pickUpAddress, deliveryAddress,carrierAsigned);
            mDatabase.child("suitcase").child(key).setValue(suitCase);
            mDatabase.child("user-suitcase").child(uid).child(key).setValue(true);
            mDatabase.child("user-suitcase").child(uid).child(key).child("activeService").setValue(true);
            mDatabase.child("user-suitcase").child(uid).child(key).child("isCaught").setValue(false);
            Toast.makeText(getActivity(), "Petición aceptada", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getActivity(), "Debes completar todos los campos", Toast.LENGTH_LONG).show();
        }
    }

//    public boolean checkNewService() {
//
//        ValueEventListener statusServiceListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                statusOrder = (boolean) dataSnapshot.child("user-suitcase").child(user.getUid()).child("activated").getValue();
//                if (statusOrder) {
//                    Toast.makeText(getActivity(), "ya tiene un pedido activo", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d(TAG, "onCancelled: error al obtener valor");
//            }
//        };
//
//
//        return true;
//    }

//    private void setupFirebaseComponents() {
//        mDatabase = FirebaseDatabase.getInstance().getReference(USERS_REFERENCE);
//        user = FirebaseAuth.getInstance().getCurrentUser();
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if (user != null) {
//                    final String userUid = user.getUid();
//                    Log.e("USER UID", userUid);
//                    mDatabase.child(userUid).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            getUserInformation(dataSnapshot);
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                } else {
//                    Log.e("Error", "Error al obtener los datos");
//                }
//            }
//        };
//    }
//
//    private void getUserInformation(DataSnapshot dataSnapshot) {
//        UserSuitcase userInf = dataSnapshot.getValue(UserSuitcase.class);
//        if (userInf != null) {
//            String isActivated = userInf.activated;
//            name.setText(isActivated);
//            Log.d("Activated", isActivated);
//        }
//    }


}
