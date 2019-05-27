package com.example.koffer.view.fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.koffer.R;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;

    private DatabaseReference mReference;
    private FirebaseUser mUser;
    private ArrayList<Marker> tmpRealTimeMarkers = new ArrayList<>();

    private ArrayList<Marker> realTimeMarkers = new ArrayList<>();

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mReference = FirebaseDatabase.getInstance().getReference().child("user-suitcase");
//        countDownTimer();

        mMapView = mView.findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

    }

//    private void countDownTimer() {
//
//        new CountDownTimer(10000, 1000) {
//
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//            }
//
//            @Override
//            public void onFinish() {
//                onMapReady(mGoogleMap);
//            }
//        }.start();
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            MapsInitializer.initialize(Objects.requireNonNull(getContext()));
        }


        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String suitcaseId = dataSnapshot.getKey();
                Log.e("ID", suitcaseId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        mReference.child("suitcase").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for (Marker marker: realTimeMarkers) {
//                    marker.remove();
//                }
//
//                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
//
//                    MapsPojo mp = snapshot.getValue(MapsPojo.class);
//                    double latitud = 0;
//                    if (mp != null) {
//                        latitud = mp.getLatitud();
//                    }
//                    double longitud = 0;
//                    if (mp != null) {
//                        longitud = mp.getLongitud();
//                    }
//                    MarkerOptions markerOptions = new MarkerOptions();
//                    markerOptions.position(new LatLng(latitud,longitud));
//                    tmpRealTimeMarkers.add(mGoogleMap.addMarker(markerOptions));
//
//                }
//
//                realTimeMarkers.clear();
//                realTimeMarkers.addAll(tmpRealTimeMarkers);
////                countDownTimer();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
}
