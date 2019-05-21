package com.example.koffer.view.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.koffer.R;
import com.example.koffer.model.Suitcase;
import com.example.koffer.view.SuitcaseViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeCarrierFragment extends Fragment {

    public static final String SUITCASE_REFERENCE = "suitcase";
    public DatabaseReference mReference;
    public FirebaseUser mUser;

    private String cardOrderId;

    public HomeCarrierFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_carrier, container, false);
        setupComponents(view);
        return view;
    }

    private void setupComponents(View view) {
        mReference = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseRecyclerOptions<Suitcase> options = new FirebaseRecyclerOptions.Builder<Suitcase>()
                .setIndexedQuery(
                        mReference.child(SUITCASE_REFERENCE).limitToFirst(100),
                        mReference.child(SUITCASE_REFERENCE), Suitcase.class)
                .setLifecycleOwner(this)
                .build();

        RecyclerView recyclerView = view.findViewById(R.id.suitcase_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new FirebaseRecyclerAdapter<Suitcase, SuitcaseViewHolder>(options) {
            @NonNull
            @Override
            public SuitcaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suitecase, parent, false);

                return new SuitcaseViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull SuitcaseViewHolder holder, int position, @NonNull Suitcase suitcase) {
                final String suitcaseKey = getRef(position).getKey();

                orderId(suitcaseKey);

                holder.userName.setText("Nombre: " + suitcase.getName());
                holder.userEmail.setText("Email: " + suitcase.getEmail());
                holder.suitcaseQuantity.setText("Cantidad maletas: " + suitcase.getQuantity());
                holder.suitcaseKG.setText("Kilos total: " + suitcase.getKg());
                holder.suitcaseAdress.setText("Direccion de recogida: " + suitcase.getPickUpAddress());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog();
                    }
                });
            }
        });
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Informacion")
                .setMessage("¿Desea aceptar este encargo?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Al hacer clic en aceptar que haga algo la aplicacion.
                        orderAssign();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    public void orderId(String suitcaseKey){
        cardOrderId = suitcaseKey;
    }

    public void orderAssign(){
        String uid = FirebaseAuth.getInstance().getUid();

        mReference.child("suitcase").child(cardOrderId).child("carrierAsigned").setValue(uid);
        Toasty.info(getActivity(), "Peticion aceptada", Toast.LENGTH_LONG).show();
    }

}
