package com.example.koffer.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.koffer.R;
import com.example.koffer.model.SuitCase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ServiceFragment extends Fragment {

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

    private DatabaseReference mdatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_service, container, false);

        mdatabase = FirebaseDatabase.getInstance().getReference();

        name = view.findViewById(R.id.name);
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

        if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(phone) || !TextUtils.isEmpty(dniString) || !TextUtils.isEmpty(quantity) || !TextUtils.isEmpty(weight) || !TextUtils.isEmpty(pickUpAddress) || !TextUtils.isEmpty((deliveryAddress))){
            String key = mdatabase.push().getKey();
            SuitCase suitCase = new SuitCase(name, email, phone, dniString, quantity, weight, pickUpAddress, deliveryAddress);
            mdatabase.child("suitcase").child(key).setValue(suitCase);
            mdatabase.child("user-suitcase").child(uid).child(key).setValue(true);
            Toast.makeText(getActivity(), "Petición aceptada", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getActivity(), "Debes completar todos los campos", Toast.LENGTH_LONG).show();
        }
    }
}
