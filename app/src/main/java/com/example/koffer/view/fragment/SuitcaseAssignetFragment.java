package com.example.koffer.view.fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.koffer.R;
import com.example.koffer.model.MapsPojo;
import com.example.koffer.model.Suitcase;
import com.example.koffer.view.SuitcaseViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuitcaseAssignetFragment extends Fragment {

    public static final String SUITCASE_REFERENCE = "suitcase";
    public DatabaseReference mReference;
    public FirebaseUser mUser;
    public FusedLocationProviderClient mLocation;

    private String cardOrderId;
    private String suitcaseUid;


    public SuitcaseAssignetFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suitcase_assignet, container, false);
        setupComponents(view);
        return view;
    }

    private void setupComponents(View view) {
        mReference = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseRecyclerOptions<Suitcase> options = new FirebaseRecyclerOptions.Builder<Suitcase>()
                .setIndexedQuery(
                        mReference.child("carrier-suitcase").child(mUser.getUid()).limitToFirst(100),
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

                cardOrderId = suitcaseKey;
                suitcaseUid = suitcase.getUid();

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
                .setMessage("¿Que desea hacer con este encargo?")
                .setPositiveButton("Entregado", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delivered();

                    }
                })
                .setNegativeButton("Geolocalizar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        geolocation();
                    }
                });
        builder.show();
    }

    public void delivered() {
        // uid del transportista
        String uid = FirebaseAuth.getInstance().getUid();
        mLocation = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocation.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            MapsPojo mp = new MapsPojo();
                            mp.setLatitude(location.getLatitude());
                            mp.setLongitude(location.getLongitude());
                            mp.setDelivered(true);
                            mReference.child("map-suitcase").child(suitcaseUid).child(cardOrderId).setValue(mp);
                        }
                    }
                });


        mReference.child("carrier-suitcase").child(uid).child(cardOrderId).setValue(null);
        mReference.child("user-suitcase-active").child(cardOrderId).setValue(null);
        Toasty.info(getActivity(), "Pedido entregado", Toast.LENGTH_LONG).show();
    }

    public void geolocation() {
        mLocation = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocation.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            MapsPojo mp = new MapsPojo();
                            mp.setLatitude(location.getLatitude());
                            mp.setLongitude(location.getLongitude());
                            mReference.child("map-suitcase").child(suitcaseUid).child(cardOrderId).setValue(mp);
                        }
                    }
                });
    }
}
