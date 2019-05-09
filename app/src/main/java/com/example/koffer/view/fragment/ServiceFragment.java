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
import com.example.koffer.model.Suitcase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ServiceFragment extends Fragment {

    View view;
    EditText nombre;
    EditText correo;
    EditText telefono;
    EditText dni;
    EditText cantidad;
    EditText peso;
    EditText direccionRecogida;
    EditText direccionEntrega;
    Button btnService;

    private DatabaseReference mdatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_service, container, false);

        mdatabase = FirebaseDatabase.getInstance().getReference();

        nombre = view.findViewById(R.id.registerName);
        correo = view.findViewById(R.id.email);
        telefono = view.findViewById(R.id.phone);
        dni = view.findViewById(R.id.dni);
        cantidad = view.findViewById(R.id.quantity);
        peso = view.findViewById(R.id.kg);
        direccionRecogida = view.findViewById(R.id.pickUpAddress);
        direccionEntrega = view.findViewById(R.id.deliveryAddress);

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
        String name = nombre.getText().toString();
        String email = correo.getText().toString();
        String phone = telefono.getText().toString();
        String dniString = dni.getText().toString();
        String quanity = cantidad.getText().toString();
        String kg = peso.getText().toString();
        String pickUpAddress = direccionRecogida.getText().toString();
        String deliveryAddress = direccionEntrega.getText().toString();

        if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(phone) || !TextUtils.isEmpty(dniString) || !TextUtils.isEmpty(quanity) || !TextUtils.isEmpty(kg) || !TextUtils.isEmpty(pickUpAddress) || !TextUtils.isEmpty((deliveryAddress))){
            String key = mdatabase.push().getKey();
            Suitcase suitcase = new Suitcase(name, email, phone, dniString, quanity, kg, pickUpAddress, deliveryAddress);
            mdatabase.child("suitcase").child(key).setValue(suitcase);
            mdatabase.child("user-suitcase").child(uid).child(key).setValue(true);
            Toast.makeText(getActivity(), "Petición aceptada", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getActivity(), "Debes completar todos los campos", Toast.LENGTH_LONG).show();
        }
    }
}
